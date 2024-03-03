package dtos;

import entities.Cours;
import entities.Lesson;
import services.cours.CoursService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursDto {
    public CoursDto() {
    }

    public Cours  onlyCours(ResultSet rs) throws SQLException {
        Cours c = new Cours();

        c.setId(rs.getInt("id"));
        c.setEnseignantId(rs.getInt("enseignantId"));
        c.setSubCategoryId(rs.getInt("subCategoryId"));
        c.setNiveauId(rs.getInt("niveauId"));
        c.setNom(rs.getString("nom"));
        c.setDescription(rs.getString("description"));
        c.setTags(rs.getString("tags"));
        c.setImage(rs.getString("image"));
        c.setValidated(rs.getBoolean("isValidated"));

        return c ;
    }

    public List<Cours> list(ResultSet rs) throws  SQLException{


        List<Cours> cours =new ArrayList<>();


        while (rs.next()){


            cours.add(single(rs));
        }

        return cours ;


    }





    public Cours single(ResultSet rs) throws SQLException {
        Cours c = onlyCours(rs);

        CoursService coursService = new CoursService() ;

       List<Lesson> lessosn =  coursService.getLessons(c.getId());

       c.setLessons((ArrayList<Lesson>) lessosn);





        return c ;
    }


}
