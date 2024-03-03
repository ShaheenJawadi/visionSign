package dtos;


import entities.SousCategorie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SousCategorieDto {
    public SousCategorieDto() {
    }

    public SousCategorie getSousCategorie(ResultSet rs) throws SQLException {
        SousCategorie c = new SousCategorie();

        c.setId(rs.getInt("id"));
        c.setNom(rs.getString("nom"));
        c.setDescription(rs.getString("description"));
        c.setDateCreation(rs.getDate("date_creation"));
        c.setStatus(rs.getString("status"));
        c.setcategorieId(rs.getInt("categorie_id"));

        return c;
    }

    public List<SousCategorie> list(ResultSet rs) throws SQLException {


        List<SousCategorie> sousCategorieList = new ArrayList<>();


        while (rs.next()) {


            sousCategorieList.add(getSousCategorie(rs));
        }

        return sousCategorieList;


    }

}
