package entities;

public class Level {
    private int id;
    private UserLevel niveau;

    public Level(){}
    public Level(int id, UserLevel niveau) {
        this.id = id;
        this.niveau = niveau;
    }
    public Level(UserLevel niveau) {
        this.niveau = niveau;
    }

    public int getId() {
        return id;
    }

    public UserLevel getNiveau() {
        return niveau;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNiveau(UserLevel niveau) {
        this.niveau = niveau;
    }


    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", niveau=" + niveau +
                '}';
    }
}

