package fr.btsciel;

import fr.btsciel.Categorie;
import fr.btsciel.Genre;
import fr.btsciel.Personne;

import java.time.LocalTime;

public class Coureurs extends Personne {
    private LocalTime temps;
    private Categorie categorie;
    public Coureurs(Genre genre, String nom , String prenom , Categorie categorie, LocalTime temps ) {
        setGenre(genre);
        setNom(nom);
        setPrenom(prenom);
        setTemps(temps);
        setCategorie(categorie);
    }
    public LocalTime getTemps() {
        return temps;
    }

    public  void setTemps(LocalTime temps) {
        this.temps = temps;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public  void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    @Override
    public String toString() {
        // Ajout du formatage du temps pour un affichage en HH:MM:SS
        long secondes = temps.toSecondOfDay();
        long h = secondes / 3600;
        long m = (secondes % 3600) / 60;
        long s = secondes % 60;
        String tempsFormatte = String.format("%02d:%02d:%02d", h, m, s);

        return String.format("%s, %s %s, Catégorie: %s, Temps: %s (%d secondes)",
                getGenre(), getNom(), getPrenom(), getCategorie(), tempsFormatte, secondes
        );
    }
}