package entities;

public class Ressource {

    private  int id , coursId ;
    private  String type , lien  ;

    public  Ressource() {

    }

    public Ressource(int id, int coursId, String type, String lien) {
        this.id = id;
        this.coursId = coursId;
        this.type = type;
        this.lien = lien;
    }

    public Ressource(int coursId, String type, String lien) {
        this.coursId = coursId;
        this.type = type;
        this.lien = lien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    @Override
    public String toString() {
        return "Ressource{" +
                "id=" + id +
                ", coursId=" + coursId +
                ", type='" + type + '\'' +
                ", lien='" + lien + '\'' +
                '}';
    }
}
