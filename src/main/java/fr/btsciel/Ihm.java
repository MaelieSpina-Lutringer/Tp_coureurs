package fr.btsciel;

import clavier.In;

import java.io.IOException;
import java.util.ArrayList;

public class Ihm {
    private GestionDesCoureurs gestion = new GestionDesCoureurs();
    private static final ArrayList<Coureurs> coureurs = new ArrayList<Coureurs>();

    public void start() {
        gestion.lireFichier("course.txt");
        int choix;
        do {
            afficherMenu();
            choix = In.readInteger();
            traiterChoix(choix);
        } while (choix != 0);
    }

    private void afficherMenu() {
        System.out.println(" MENU COUREURS ");
        System.out.println("1 - Afficher par nom croissant");
        System.out.println("2 - Afficher par nom décroissant");
        System.out.println("3 - Afficher par prenom croissant");
        System.out.println("4 - Afficher par prenom décroissant");
        System.out.println("5 - Afficher par classement (temps croissant)");
        System.out.println("6 - Afficher par classement (temps decroissant)");
        System.out.println("7 - Ajouter un coureur");
        System.out.println("8 - Supprimer un coureur");
        System.out.println("9 - Sauvegarder dans le fichier");
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
                break;

            case 8:
                supprimerCoureurDepuisSaisie();
                break;

            case 9:
                modifierCoureurDepuisSaisie();
                break;

            case 10 :
                sauvergarder();
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
        int i = 0;
        for (Coureurs c : gestion.getCoureurs()) {
            System.out.println((i++) + " - " + c);
        }
    }

    private void ajouterCoureurDepuisSaisie() {
        System.out.print("Civilité (M/F) : ");
        String civ = In.readString();

        System.out.print("Nom : ");
        String nom = In.readString();

        System.out.print("Prénom : ");
        String prenom = In.readString();

        System.out.print("Categorie (ELITE_1, M1, M2, M7... : ");
        String cat = In.readString();

        System.out.print("Temps (en secondes) : ");
        int temps = In.readInteger();

        Coureurs c = new Coureurs(civ, nom, prenom, cat, temps);
        gestion.ajouterCoureur(c);
        System.out.println("Coureur ajoute.");
    }

    private void supprimerCoureurDepuisSaisie() {
        afficherListe();
        System.out.print("Index du coureur à supprimer : ");
        int index = In.readInteger();
        gestion.supprimerCoureur(index);
        System.out.println("Coureur supprime (si l'index etait valide).");
    }

    private void modifierCoureurDepuisSaisie() {
        if (gestion.getCoureurs().isEmpty()) {
            System.out.println("La liste des coureurs est vide. Aucune modification possible.");
            return;
        }
        afficherListe();
        System.out.print("Index du coureur à modifier : ");
        int index = In.readInteger();

        if (index < 0 || index >= gestion.getCoureurs().size()) {
            System.out.println("Index invalide. Retour au menu.");
            return;
        }
        System.out.println("\n--- Saisie des nouvelles informations ---");
        System.out.print("Nouvelle Civilité (M/F) : ");
        String civ = In.readString();

        System.out.print("Nouveau Nom : ");
        String nom = In.readString();

        System.out.print("Nouveau Prénom : ");
        String prenom = In.readString();

        System.out.print("Nouvelle Catégorie : ");
        String cat = In.readString();

        System.out.print("Nouveau Temps (en sec) : ");
        int temps = In.readInteger();

        try {
            Coureurs cModifie = new Coureurs(civ, nom, prenom, cat, temps);
            gestion.modifierCoureur(index, cModifie);
            System.out.println("Coureur modifié avec succès.");
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur de saisie : Genre ou Catégorie invalide. La modification a été annulée. " + e.getMessage());
        }
    }
    private void sauvergarder() {
        try {
            gestion.sauvegarderdansFichier("course.txt");
            System.out.println("Sauvergarde réussie");

        } catch (IOException e) {
            System.err.println("Erreur de sauvegarde" + e.getMessage());
        }
    }


    public static void main(String[] args) {
        new Ihm().start();
    }
}
