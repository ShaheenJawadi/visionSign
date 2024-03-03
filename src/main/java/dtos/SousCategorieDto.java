package dtos;

import entities.Categorie;
import entities.SousCategorie;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SousCategorieDto {
    public SousCategorieDto() {
    }

    public SousCategorie single(ResultSet rs) throws SQLException {
        SousCategorie c = new SousCategorie();

        c.setId(rs.getInt("id"));
        c.setNom(rs.getString("nom"));
        c.setDescription(rs.getString("description"));
        c.setDescription(rs.getString("status"));

        // Retrieve dateCreation using java.sql.Date
        Date dateCreation = rs.getDate("dateCreation");
        c.setDateCreation(dateCreation);

        // Instantiate and set the parent category
        Categorie parentCategory = new Categorie();
        parentCategory.setId(rs.getInt("id"));
        parentCategory.setNom(rs.getString("nom"));
        parentCategory.setDescription(rs.getString("description"));

        c.setCategorieParente(parentCategory);

        return c;
    }

    public List<SousCategorie> list(ResultSet rs) throws SQLException {


        List<SousCategorie> SousCategorie = new ArrayList<>();


        while (rs.next()) {


            SousCategorie.add(single(rs));
        }

        return SousCategorie;


    }

}
