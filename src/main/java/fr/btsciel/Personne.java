package fr.btsciel;

import java.io.Serializable;

public class Personne implements Serializable {
    private  String nom;
    private  String prenom;
    private  Genre genre;

    public String getNom() {
        return nom;
    }

    public  void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public  void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Genre getGenre() {
        return genre;
    }

    public  void setGenre(Genre genre) {
        this.genre = genre;
    }
}