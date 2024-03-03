package entities;

public class Lesson {
    private int id , coursId ;
    private String titre , content ;
    private int duree ;
    private String image , video ;
    private int classement ;

    public Lesson() {
    }


    public Lesson(int id, int coursId, String titre, String content, int duree, String image, String video, int classement) {
        this.id = id;
        this.coursId = coursId;
        this.titre = titre;
        this.content = content;
        this.duree = duree;
        this.image = image;
        this.video = video;
        this.classement = classement;
    }

    public Lesson(int coursId, String titre, String content, int duree, String image, String video, int classement) {
        this.coursId = coursId;
        this.titre = titre;
        this.content = content;
        this.duree = duree;
        this.image = image;
        this.video = video;
        this.classement = classement;
    }


    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getClassement() {
        return classement;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", coursId=" + coursId +
                ", titre='" + titre + '\'' +
                ", content='" + content + '\'' +
                ", duree=" + duree +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                ", classement=" + classement +
                '}';
    }



    public  String getTextDuration (){
        return String.valueOf(this.getDuree())+" Min";
    }
}
