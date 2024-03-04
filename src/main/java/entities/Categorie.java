package entities;

import java.util.Date;
import java.util.List;

public class Categorie {
    private int id;
    private String nom;
    private String description;
    private Date last_updated;
    private String image;
    private int nbSousCategorie;
    private List<SousCategorie> sousCategorieListe;

    public Categorie() {
    }

    public Categorie(String nom, String description, Date last_updated, String image, List<SousCategorie> sousCategorieListe, int nbSousCategorie ) {
        this.nom = nom;
        this.description = description;
        this.last_updated = last_updated;
        this.image = image;
        this.sousCategorieListe = sousCategorieListe;
        this.nbSousCategorie=nbSousCategorie;
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

    public int getNbSousCategorie() {
        return nbSousCategorie;
    }

    public void setNbSousCategorie(int nbSousCategorie) {
        this.nbSousCategorie = nbSousCategorie;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", last_updated=" + last_updated +
                ", image='" + image + '\'' +
                ", nbSousCategorie=" + nbSousCategorie +
                ", sousCategorieListe=" + sousCategorieListe +
                '}';
    }

    public  String nbsousCategories(){
        return String.valueOf(this.getSousCategorieListe().size()) ;
    }
}
