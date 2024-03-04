package entities;


import java.sql.Date;
import java.time.LocalDate;

public class SousCategorie {
    private int id;
    private String nom;
    private String description;
    private Date dateCreation;
    private String status;
    private int categorieId; // Reference to the parent category

    public SousCategorie() {
    }

    public SousCategorie(int id, String nom, String description, Date dateCreation, String status, int categorieId) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateCreation = Date.valueOf(LocalDate.now());
        this.status = status;
        this.categorieId = categorieId;
    }

    public SousCategorie(String nom, String description, Date dateCreation, String status, int categorieId) {
        this.nom = nom;
        this.description = description;
        this.dateCreation = Date.valueOf(LocalDate.now());
        this.status = status;
        this.categorieId = categorieId;
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

    public int getcategorieId() {
        return categorieId;
    }

    public void setcategorieId(int categorieId) {
        this.categorieId = categorieId;
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
                ", categorieId=" + categorieId +
                '}';
    }
}
