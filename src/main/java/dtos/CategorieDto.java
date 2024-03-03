package dtos;

import entities.Categorie;
import entities.SousCategorie;
import services.Categorie.CategorieService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDto {

    public CategorieDto() {
    }

    public Categorie getCategorie(ResultSet rs) throws SQLException {
        Categorie c = new Categorie();

        c.setId(rs.getInt("id"));
        c.setNom(rs.getString("nom"));
        c.setDescription(rs.getString("description"));
        c.setLast_updated(rs.getDate("last_updated"));
        c.setImage(rs.getString("image"));

        return c;
    }

    public List<Categorie> list(ResultSet rs) throws SQLException {


        List<Categorie> categories = new ArrayList<>();


        while (rs.next()) {


            categories.add(getCategorie(rs));
        }

        return categories;


    }


    public Categorie getCategorieWithSousCategories(ResultSet rs) throws SQLException {
        Categorie c = getCategorie(rs);

        CategorieService catSer = new CategorieService();

        List<SousCategorie> sousCategorieList = catSer.getSousCategorieListByCategoryId(c.getId());

        c.setSousCategorieListe(sousCategorieList);

        return c;
    }
}
