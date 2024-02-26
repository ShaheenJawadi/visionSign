package entities;

import java.util.Date;
import java.util.List;

public class Publications {
    private int id;
    private String titre;
    private String contenu;
    private Date date_creation;
    private int userId;
    private String userName;

    private List<Commentaires> pubCommentaires;

    public Publications() {
    }

    public Publications(int id, String titre, String contenu, Date date_creation, int userId) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.userId = userId;
    }

    public Publications(String titre, String contenu, Date date_creation, int userId) {
        this.titre = titre;
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.userId = userId;
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Commentaires> getPubCommentaires() {
        return pubCommentaires;
    }

    public void setPubCommentaires(List<Commentaires> pubCommentaires) {
        this.pubCommentaires = pubCommentaires;
    }

    @Override
    public String toString() {
        String result = "Publications:\n{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", date_creation=" + date_creation +
                ", userId=" + userId +", userName: "+userName;

        if (pubCommentaires != null && !pubCommentaires.isEmpty()) {
            result += ",\ncommentaires=[\n";
            for (Commentaires commentaire : pubCommentaires) {
                result += commentaire.toString() + ",\n";
            }
            result = result.substring(0, result.length() - 2);
            result += "\n]";
        } else {
            result += ", commentaires=[]";
        }

        result += "\n}";
        return result;
    }

}
