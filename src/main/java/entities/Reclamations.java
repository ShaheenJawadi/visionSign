package entities;

import java.sql.Date;
import java.time.LocalDate;

public class Reclamations {

    private int id_reclamation, id_user;
    private String type, description, status;
    private Date date;
    private boolean repondre;

    public Reclamations() {
        this.repondre = false; // Valeur par défaut
    }

    public Reclamations(int id_reclamation, String type, String description, String status, Date date,int id_user) {
        this.id_reclamation = id_reclamation;
        this.type = type;
        this.description = description;
        this.status = status;
        this.date = date;
        this.repondre = false; // Valeur par défaut
        this.id_user=id_user;
    }

    public Reclamations(String type, String description, String status, Date date, int id_user) {
        this.type = type;
        this.description = description;
        this.status = status;
        this.date = date;
        this.repondre = false; // Valeur par défaut
        this.id_user=id_user;
    }

    public int getId_user() {
        return id_user;
    }

    public String getStatus() {
        return status;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public  Date getDate() {
        return date;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRepondre() {
        return repondre;
    }

    public void setRepondre(boolean repondre) {
        this.repondre = repondre;
    }
    public LocalDate getLocalDate() {
        if (getDate() instanceof java.sql.Date) {
            java.sql.Date sqlDate = getDate();
            return sqlDate.toLocalDate();
        } else {
            return getDate().toLocalDate();
        }
    }

    @Override
    public String toString() {
        return "Reclamations{" +
                "id_reclamation=" + id_reclamation +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", id_user=" + id_user +
                ", repondre=" + repondre + '\'' +
                '}';
    }
}