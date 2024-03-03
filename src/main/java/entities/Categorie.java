package entities;

import java.util.List;

public class Categorie {
    private int id;
    private String nom;
    private String description;
    private List<SousCategorie> sousCategorieListe;

    public Categorie() {
    }

    public Categorie(String nom, String description, List<SousCategorie> sousCategorieListe) {
        this.nom = nom;
        this.description = description;
        this.sousCategorieListe = sousCategorieListe;
    }

    public Categorie(int id, String nom, String description, List<SousCategorie> sousCategorieListe) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.sousCategorieListe = sousCategorieListe;
    }

    public Categorie(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SousCategorie> getSousCategorieListe() {
        return sousCategorieListe;
    }

    public void setSousCategorieListe(List<SousCategorie> sousCategorieListe) {
        this.sousCategorieListe = sousCategorieListe;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", sousCategorieListe=" + sousCategorieListe +
                '}';
    }
}
