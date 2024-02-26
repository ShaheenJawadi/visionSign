package entities;

import java.sql.Date;
import java.time.LocalDate;

public class Avis {
    private int id_avis, note, id_user, coursid;
    private String message;
    private String UserName;
    private String coursNom; // Ajout du champ pour le nom du cours

    private Date date;


    public Avis() {
        this.date = Date.valueOf(LocalDate.now());
    }

    public Avis(int id_avis, int note, String message, int id_user, int coursid, String coursNom) {
        this.id_avis = id_avis;
        this.note = note;
        this.message = message;
        this.date = Date.valueOf(LocalDate.now());
        this.id_user = id_user;
        this.coursid = coursid;
        this.coursNom = coursNom; // Affectation du nom du cours

    }

    public Avis(int note, String message, int id_user, int coursid) {
        this.note = note;
        this.message = message;
        this.date = Date.valueOf(LocalDate.now());
        this.id_user = id_user;
        this.coursid = coursid;
        //this.coursNom = coursNom; // Affectation du nom du cours
    }

    // Les autres méthodes restent inchangées...

    public String getCoursNom() {
        return coursNom;
    }

    public void setCoursNom(String coursNom) {
        this.coursNom = coursNom;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "id_avis=" + id_avis +
                ", note=" + note +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", id-user='" + id_user + '\'' +
                ", id-cours='" + coursid + '\'' +
                ", nom-cours='" + coursNom + '\'' +
                '}';
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getCoursid() {
        return coursid;
    }

    public int getId_avis() {
        return id_avis;
    }

    public int getNote() {
        return note;
    }

    public int getId_user() {
        return id_user;
    }


    public void setCoursid(int coursid) {
        this.coursid = coursid;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public void setId_avis(int id_avis) {
        this.id_avis = id_avis;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}