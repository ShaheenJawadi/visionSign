package entities;

public class Reactions {
    private int user_id,pub_id,jaime,dislike;

    public Reactions(int user_id, int pub_id, int jaime, int dislike) {
        this.user_id = user_id;
        this.pub_id = pub_id;
        this.jaime = jaime;
        this.dislike = dislike;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPub_id() {
        return pub_id;
    }

    public void setPub_id(int pub_id) {
        this.pub_id = pub_id;
    }

    public int getJaime() {
        return jaime;
    }

    public void setJaime(int jaime) {
        this.jaime = jaime;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    @Override
    public String toString() {
        return "Reactions{" +
                "user_id=" + user_id +
                ", pub_id=" + pub_id +
                ", jaime=" + jaime +
                ", dislike=" + dislike +
                '}';
    }
}
