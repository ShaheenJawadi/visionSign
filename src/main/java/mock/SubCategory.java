package mock;

public class SubCategory {
    int id ;
    int categorieId ;
    String nom ;

    public SubCategory(int id, int categorieId, String nom) {
        this.id = id;
        this.categorieId = categorieId;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return  nom ;
    }
}
