package entities;


import java.sql.Date;
import java.time.LocalDate;

public class SousCategorie {
    private int id;
    private String nom;
    private String description;
    private Date dateCreation;
    private String status;
    private Categorie categorieParente; // Reference to the parent category

    public SousCategorie() {
    }

    public SousCategorie(int id, String nom, String description, Date dateCreation, String status, Categorie categorieParente) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateCreation = Date.valueOf(LocalDate.now());
        this.status = status;
        this.categorieParente = categorieParente;
    }

    public SousCategorie(String nom, String description, Date dateCreation, String status, Categorie categorieParente) {
        this.nom = nom;
        this.description = description;
        this.dateCreation = Date.valueOf(LocalDate.now());
        this.status = status;
        this.categorieParente = categorieParente;
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

    public Categorie getCategorieParente() {
        return categorieParente;
    }

    public void setCategorieParente(Categorie categorieParente) {
        this.categorieParente = categorieParente;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SousCategorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", dateCreation=" + dateCreation +
                ", status='" + status + '\'' +
                ", categorieParente=" + categorieParente +
                '}';
    }
}
