package fr.btsciel;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestionDesCoureurs {

    private List<Coureurs> coureurs = new ArrayList<>();

    public void lireFichier(String chemin) {
        try {
            List<String> lignes = Files.readAllLines(Paths.get(chemin));
            for (String ligne : lignes) {
                ajouterListe(ligne);
            }
            System.out.println("Chargement de " + coureurs.size() + " coureurs depuis le fichier " + chemin);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier " + chemin + ". Assurez-vous qu'il existe et est accessible. Détail: " + e.getMessage());
        }
    }


    public void ajouterListe(String ligne) {
        if (ligne != null) {
            String[] monTableau = ligne.split(",");
            if (monTableau.length == 5) {
                try {
                    Coureurs coureur = new Coureurs(
                            Genre.valueOf(monTableau[0].trim().toUpperCase()),
                            monTableau[1].trim(),
                            monTableau[2].trim(),
                            Categorie.valueOf(monTableau[3].trim().toUpperCase()),
                            LocalTime.ofSecondOfDay(Integer.parseInt(monTableau[4].trim()))
                    );
                    coureurs.add(coureur);
                } catch (IllegalArgumentException e) {
                    System.err.println("Erreur de format de donnée sur la ligne : " + ligne + ". Détail: " + e.getMessage());
                }
            } else {
                System.err.println("Ligne ignorée : format invalide (doit avoir 5 champs) : " + ligne);
            }
        }
    }

    public void sauvegarderdansFichier(String chemin) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(chemin));
        for (Coureurs c : coureurs) {
            bw.write(c.getGenre() + "," + c.getNom() + ","
                    + c.getPrenom() + "," + c.getCategorie()
                    + "," + c.getTemps().toSecondOfDay());
            bw.newLine();
        }

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("course.bin"))) {
            for (Coureurs c : coureurs) {
                dos.writeUTF(c.getGenre().name());
                dos.writeUTF(c.getNom());
                dos.writeUTF(c.getPrenom());
                dos.writeUTF(c.getCategorie().name());
                dos.writeLong(c.getTemps().toSecondOfDay());

                bw.close();
            }
        }

    }

    public List<Coureurs> getCoureurs() {
        return coureurs;
    }

    public void ajouterCoureur(Coureurs c) {
        coureurs.add(c);
    }

    public void supprimerCoureur(int index) {
        if (index >= 0 && index < coureurs.size()) {
            coureurs.remove(index);
        }
    }
    public void reinitialiserListe(String cheminFichier) {
        coureurs.clear();
        lireFichier(cheminFichier);
    }
    public void trierParNomCroissant() {
        coureurs.sort(Comparator.comparing(Coureurs::getNom));
    }

    public void trierParNomDecroissant() {
        coureurs.sort(Comparator.comparing(Coureurs::getNom).reversed());
    }

    public void trierParPrenomCroissant() {
        coureurs.sort(Comparator.comparing(Coureurs::getPrenom));
    }

    public void trierParPrenomDecroissant() {
        coureurs.sort(Comparator.comparing(Coureurs::getPrenom).reversed());
    }

    public void trierParTempsCroissant() {
        coureurs.sort(Comparator.comparing(Coureurs::getTemps));
    }

    public void trierParTempsDecroissant() {
        coureurs.sort(Comparator.comparing(Coureurs::getTemps).reversed());
    }

    public void modifierCoureur(int index, Coureurs nouveauCoureur) {
        if (index >= 0 && index < coureurs.size()) {
            coureurs.set(index, nouveauCoureur);
        } else {
            System.err.println("Index de coureur invalide pour la modification.");
        }
    }

    public long calculerDifferenceTemps(int indexCoureur1, int indexCoureur2) {
        if (indexCoureur1 < 0 || indexCoureur1 >= coureurs.size() ||
                indexCoureur2 < 0 || indexCoureur2 >= coureurs.size()) {
            return Long.MIN_VALUE;
        }
        Coureurs c1 = coureurs.get(indexCoureur1);
        Coureurs c2 = coureurs.get(indexCoureur2);
        Duration difference = Duration.between(c1.getTemps(), c2.getTemps());
        return difference.getSeconds();
    }
}