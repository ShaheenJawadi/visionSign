package dtos;

import entities.Categorie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDto {
    public CategorieDto() {
    }

    public Categorie single(ResultSet rs) throws SQLException {
        Categorie c = new Categorie();
        c.setId(rs.getInt("id"));
        c.setNom(rs.getString("nom"));
        c.setDescription(rs.getString("description"));
        return c;
    }

    public List<Categorie> list(ResultSet rs) throws SQLException {


        List<Categorie> Categorie = new ArrayList<>();


        while (rs.next()) {


            Categorie.add(single(rs));
        }

        return Categorie;


    }
}
