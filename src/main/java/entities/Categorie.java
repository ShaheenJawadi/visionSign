package entities;

import java.sql.Date;
import java.util.List;

public class Categorie {
    private int id;
    private String nom;
    private String description;
    private Date last_updated;
    private String image;
    private List<SousCategorie> sousCategorieListe;

    public Categorie() {
    }

    public Categorie(String nom, String description, Date last_updated, String image, List<SousCategorie> sousCategorieListe) {
        this.nom = nom;
        this.description = description;
        this.last_updated = last_updated;
        this.image = image;
        this.sousCategorieListe = sousCategorieListe;
    }


    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
