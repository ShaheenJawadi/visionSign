package dtos;

import entities.Ressource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RessourceDto {
    public RessourceDto() {
    }

    public Ressource single(ResultSet rs) throws SQLException {
        Ressource r =new Ressource();

        r.setId(rs.getInt("id"));
        r.setCoursId(rs.getInt("coursId"));
        r.setLien(rs.getString("lien"));
        r.setType(rs.getString("type"));

        return r ;
    }

    public List<Ressource> list(ResultSet rs) throws  SQLException{


        List<Ressource> ressources =new ArrayList<>();


        while (rs.next()){


            ressources.add(single(rs));
        }

        return ressources ;


    }


}
