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
    public Coureurs(String civilite, String nom, String prenom, String categorie, int temps) {
        setGenre(Genre.valueOf(civilite.trim().toUpperCase()));
        setNom(nom);
        setPrenom(prenom);
        setCategorie(Categorie.valueOf(categorie.trim().toUpperCase()));
        setTemps(LocalTime.ofSecondOfDay(temps));
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
        return String.format("%s, %s %s, Categorie: %s, Temps: %s",
                getGenre(), getNom(), getPrenom(), getCategorie(), getTemps()
        );
    }
}