package entities;


import java.util.Date;

public class Commentaires {
    private int id;
    private String commentaire;
    private Date date;
    private int userId;
    private String userName;
    private int id_pub;

    public Commentaires() {
    }

    public Commentaires(int id, String commentaire, Date date, int id_pub, int userId) {
        this.id = id;
        this.commentaire = commentaire;
        this.date = date;
        this.id_pub = id_pub;
        this.userId = userId;
    }

    public Commentaires(String commentaire, Date date, int id_pub, int userId) {
        this.commentaire = commentaire;
        this.date = date;
        this.id_pub = id_pub;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId_pub() {
        return id_pub;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    @Override
    public String toString() {
        return "Commentaires:{" +
                "id=" + id +
                ", commentaire='" + commentaire + '\'' +
                ", date=" + date +
                ", userId=" + userId +
                ", userName=" + userName +
                '}';
    }
}
