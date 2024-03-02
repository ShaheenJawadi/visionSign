package entities;

import mock.Category;

import java.util.ArrayList;

public class Cours {
    private  int id ,enseignantId, subCategoryId , niveauId  ;
    private  String nom , description,tags  ,image ;
    private boolean isValidated ;

    private ArrayList<Lesson> lessons ;
    // add ensgId
    public  Cours(){

    }

    public Cours(int id,int enseignantId ,  int subCategoryId, int niveauId, String nom, String description, String tags, String image, boolean isValidated) {
        this.id = id;
        this.enseignantId = enseignantId;
        this.subCategoryId = subCategoryId;
        this.niveauId = niveauId;
        this.nom = nom;
        this.description = description;
        this.tags = tags;
        this.image = image;
        this.isValidated = isValidated;
    }

    public Cours(int enseignantId , int subCategoryId, int niveauId, String nom, String description, String tags, String image, boolean isValidated) {
        this.enseignantId = enseignantId;
        this.subCategoryId = subCategoryId;
        this.niveauId = niveauId;
        this.nom = nom;
        this.description = description;
        this.tags = tags;
        this.image = image;
        this.isValidated = isValidated;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(int niveauId) {
        this.niveauId = niveauId;
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

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public int getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(int enseignantId) {
        this.enseignantId = enseignantId;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", enseignantId=" + enseignantId +
                ", subCategoryId=" + subCategoryId +
                ", niveauId=" + niveauId +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", image='" + image + '\'' +
                ", isValidated=" + isValidated +
                '}';
    }

    public  Category getCategory(){
        return new Category(1 , "cat mock");
    }


    public String getLevel (){
        return "level";
        //TODO FETCHlEVEL
    }
    public  String lessonsDuration(){

        //TODO fetch lessons + calc duration
        return "10 heurs ";

    }

    public  String getEnseignant (){
        //TODO fetch user unsg
        return "ensg";
    }

    public  String nbLessons(){



        //TODO fetch nbLessons
        return String.valueOf(this.lessons.size()) ;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }
}
