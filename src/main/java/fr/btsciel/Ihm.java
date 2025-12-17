package fr.btsciel;

import clavier.In;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;

public class Ihm {
    private GestionDesCoureurs gestion = new GestionDesCoureurs();

    private void start() {
        gestion.lireFichier("course.txt");
        int choix;
        do {
            afficherMenu();
            choix = In.readInteger();
            traiterChoix(choix);
        } while (choix != 0);
    }

    private void afficherMenu() {
        System.out.println("\n--- MENU COUREURS ---");
        System.out.println("1 - Afficher par nom croissant");
        System.out.println("2 - Afficher par nom decroissant");
        System.out.println("3 - Afficher par prenom croissant");
        System.out.println("4 - Afficher par prenom décroissant");
        System.out.println("5 - Afficher par classement (temps croissant)");
        System.out.println("6 - Afficher par classement (temps decroissant)");
        System.out.println("7 - Ajouter un coureur");
        System.out.println("8 - Supprimer un coureur");
        System.out.println("9 - Sauvegarder dans le fichier");
        System.out.println("10 - Modifier un coureur");
        System.out.println("11 - Reinitialiser la liste (ordre du fichier)");
        System.out.println("12 - Comparer des coureurs");
        System.out.println("0 - Quitter");
        System.out.print("Votre choix : ");
    }

    private void traiterChoix(int choix) {
        switch (choix) {
            case 1:
                gestion.trierParNomCroissant();
                afficherListe();
                break;

            case 2:
                gestion.trierParNomDecroissant();
                afficherListe();
                break;

            case 3:
                gestion.trierParPrenomCroissant();
                afficherListe();
                break;

            case 4:
                gestion.trierParPrenomDecroissant();
                afficherListe();
                break;

            case 5:
                gestion.trierParTempsCroissant();
                afficherListe();
                break;

            case 6:
                gestion.trierParTempsDecroissant();
                afficherListe();
                break;

            case 7:
                ajouterCoureurDepuisSaisie();
                afficherListe();
                break;

            case 8:
                supprimerCoureurDepuisSaisie();
                afficherListe();
                break;

            case 9:
                sauvegarder();
                afficherListe();
                break;
            case 10:
                modifierCoureurDepuisSaisie();
                afficherListe();
                break;
            case 11:
                gestion.reinitialiserListe("course.txt");
                afficherListe();
                System.out.println("La liste des coureurs a été réinitialisée à l'ordre du fichier.");
                break;
            case 12 :
                CalculerEtAfficherDifference();
                break;
            case 0:
                System.out.println("Au revoir !");
                break;

            default:
                System.out.println("Choix invalide.");
                break;
        }
    }
    private void afficherListe() {
        if (gestion.getCoureurs().isEmpty()) {
            System.out.println("\n--- La liste des coureurs est actuellement vide. ---");
            return;
        }
        System.out.println("\n--- LISTE DES COUREURS ---");
        int i = 0;
        for (Coureurs c : gestion.getCoureurs()) {
            System.out.println((i++) + " - " + c);
        }
        System.out.println("--------------------------");
    }

    private Genre saisirGenre() {
        Genre genre = null;
        while (genre == null) {
            System.out.println("Choix Civilite :");
            System.out.println("1 - Femme (F)");
            System.out.println("2 - Homme (M)");
            System.out.print("Votre choix (1 ou 2) : ");
            int choix = In.readInteger();

            switch (choix) {
                case 1:
                    genre = Genre.F;
                    break;
                case 2:
                    genre = Genre.M;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez saisir 1 ou 2.");
            }
        }
        return genre;
    }

    private Categorie saisirCategorie() {
        Categorie categorie = null;
        Categorie[] toutesCategories = Categorie.values();
        int minChoix = 1;
        int maxChoix = toutesCategories.length;

        while (categorie == null) {
            System.out.println("Choix Categorie :");
            for (int i = 0; i < maxChoix; i++) {
                System.out.println((i + 1) + " - " + toutesCategories[i]);
            }
            System.out.print("Votre choix (" + minChoix + " à " + maxChoix + ") : ");
            int choix = In.readInteger();

            if (choix >= minChoix && choix <= maxChoix) {
                // L'index dans le tableau est (choix - 1)
                categorie = toutesCategories[choix - 1];
            } else {
                System.out.println("Choix invalide. Veuillez saisir un nombre entre " + minChoix + " et " + maxChoix + ".");
            }
        }
        return categorie;
    }

    private void ajouterCoureurDepuisSaisie() {
        System.out.println("\n--- Ajout d'un nouveau coureur ---");

        Genre genre = saisirGenre();

        System.out.print("Nom : ");
        String nom = In.readString();

        System.out.print("Prénom : ");
        String prenom = In.readString();

        Categorie categorie = saisirCategorie();

        System.out.print("Temps (en sec) : ");
        int temps = In.readInteger();

        Coureurs c = new Coureurs(genre, nom, prenom, categorie, LocalTime.ofSecondOfDay(temps));
        gestion.ajouterCoureur(c);
        System.out.println("\nCoureur ajoute.");
    }

    private void supprimerCoureurDepuisSaisie() {
        afficherListe();
        if (gestion.getCoureurs().isEmpty()) return;

        System.out.print("Index du coureur à supprimer : ");
        int index = In.readInteger();
        System.out.println("DEBUG : Tentative de suppression de l'index " + index + ".");
        gestion.supprimerCoureur(index);
        System.out.println("Coureur supprime (si l'index etait valide).");
    }

    private void modifierCoureurDepuisSaisie() {
        if (gestion.getCoureurs().isEmpty()) {
            System.out.println("\nLa liste des coureurs est vide. Aucune modification possible.");
            return;
        }

        afficherListe();
        System.out.print("Index du coureur a modifier : ");
        int index = In.readInteger();

        if (index < 0 || index >= gestion.getCoureurs().size()) {
            System.out.println("Index invalide. Retour au menu.");
            return;
        }
        System.out.println("\n--- Modification du coureur " + index + " (" + gestion.getCoureurs().get(index).getNom() + ") ---");

        Genre nouveauGenre = saisirGenre();

        System.out.print("Nouveau Nom : ");
        String nouveauNom = In.readString();

        System.out.print("Nouveau Prenom : ");
        String nouveauPrenom = In.readString();

        Categorie nouvelleCategorie = saisirCategorie();

        System.out.print("Nouveau Temps (en sec) : ");
        int nouveauTemps = In.readInteger();

        Coureurs cModifie = new Coureurs(
                nouveauGenre,
                nouveauNom,
                nouveauPrenom,
                nouvelleCategorie,
                LocalTime.ofSecondOfDay(nouveauTemps)
        );

        gestion.modifierCoureur(index, cModifie);
        System.out.println("\nCoureur modifié avec succes.");
    }

    private void sauvegarder() {
        try {
            gestion.sauvegarderdansFichier("course.txt");
            System.out.println("Sauvegarde effectuee.");
        } catch (IOException e) {
            System.out.println("Erreur de sauvegarde : " + e.getMessage());
        }
    }
    private String formaterEcartTemps(long totalSecondes) {
        String signe = "";
        if (totalSecondes < 0) {
            signe = "-";
            totalSecondes = -totalSecondes;
        }
        long heures = totalSecondes / 3600;
        long minutes = (totalSecondes % 3600) / 60;
        long secondes = totalSecondes % 60;
        return String.format("%s%02d:%02d:%02d", signe, heures, minutes, secondes);
    }
    private void CalculerEtAfficherDifference(){
        if (gestion.getCoureurs().size() <2 ){
            System.out.println("Il faut au moins 2 coureurs pour calculer une difference");
            return;
        }
        afficherListe();
        System.out.println("Index du 1er Coureur :");
        int index1 = In.readInteger();
        System.out.println("Index du 2eme Coureur :");
        int index2 = In.readInteger();

        if (index1 < 0  || index2 >= gestion.getCoureurs().size() || index2 < 0 || index2 >= gestion.getCoureurs().size()) {
            System.out.println("Index invalide. Retour au menu.");
        }
        long Differenceseconde = gestion.calculerDifferenceTemps(index1, index2);
        Coureurs c1 =  gestion.getCoureurs().get(index1);
        Coureurs c2 =  gestion.getCoureurs().get(index2);

        String ecartFormatee = formaterEcartTemps(Differenceseconde);

        System.out.println("\n Resultat de la comparaison");
        System.out.println("Coureur 1 (Reference): " + c1.getNom() + " " + c1.getPrenom() + " (" + c1.getTemps() + ")");
        System.out.println("Coureur 2 (Compare):   " + c2.getNom() + " " + c2.getPrenom() + " (" + c2.getTemps() + ")");
        System.out.println("Ecart (Coureur 2 - Coureur 1): " + ecartFormatee);

        if (Differenceseconde > 0){
            System.out.println(c2.getPrenom() + " a termine APRES " + c1.getPrenom() + ".");
        } else if (Differenceseconde < 0) {
            System.out.println(c1.getPrenom() + "a termine AVANT " + c2.getPrenom() + ".");
        } else
            System.out.println("les deux coureurs on finit en meme temps");
    }

    public static void main(String[] args) {
        new Ihm().start();
    }
}