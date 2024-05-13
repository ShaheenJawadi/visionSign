package entities;

import java.util.List;

public class Quiz {
    private int id;
    private String nom;
    private String duree;
    private int coursId;
    private int userId;
    private List<Questions> quizQuestions;
    public Quiz(){}

    public Quiz(int id, String nom, String duree) {
        this.id = id;
        this.nom = nom;
        this.duree = duree;
    }

    public Quiz(int id, String nom, String duree, int coursId, int userId) {
        this.id = id;
        this.nom = nom;
        this.duree = duree;
        this.coursId = coursId;
        this.userId = userId;
    }

    public Quiz(String nom, String duree, int coursId, int userId) {
        this.nom = nom;
        this.duree = duree;
        this.coursId = coursId;
        this.userId = userId;

    }
    public void setQuizQuestions(List<Questions> questions) {
        this.quizQuestions = questions;
    }

    public List<Questions> getQuizQuestions(){
        return this.quizQuestions;
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

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", duree='" + duree + '\'' +
                ", coursId=" + coursId +
                ", userId=" + userId +'\n'+
                getQuizQuestions().toString()+
                '}'+'\n'+"---------------------"+'\n';
    }
}
