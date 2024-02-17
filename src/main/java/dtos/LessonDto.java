package dtos;


import entities.Lesson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDto {
    public LessonDto() {
    }

    public Lesson single(ResultSet rs) throws SQLException {
        Lesson l =new Lesson();
//id	coursId	titre	content	duree	image	video	classement
        l.setId(rs.getInt("id"));
        l.setCoursId(rs.getInt("coursId"));
        l.setTitre(rs.getString("titre"));
        l.setContent(rs.getString("content"));
        l.setDuree(rs.getInt("duree"));
        l.setImage(rs.getString("image"));
        l.setVideo(rs.getString("video"));
        l.setClassement(rs.getInt("classement"));

        return l ;
    }

    public List<Lesson> list(ResultSet rs) throws  SQLException{


        List<Lesson> lessons =new ArrayList<>();


        while (rs.next()){


            lessons.add(single(rs));
        }

        return lessons ;


    }

}
