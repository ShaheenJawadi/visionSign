package dtos;


import entities.UserCours;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCoursDto {
    public UserCoursDto() {
    }

    public UserCours single(ResultSet rs) throws SQLException {
        UserCours l =new UserCours();

        l.setId(rs.getInt("id"));
        l.setCoursId(rs.getInt("coursId"));
        l.setUserId(rs.getInt("userId"));
        l.setCorrectQuizz(rs.getBoolean("isCorrectQuizz"));
        l.setState(rs.getInt("state"));


        return l ;
    }

    public List<UserCours> list(ResultSet rs) throws  SQLException{


        List<UserCours> lessons =new ArrayList<>();


        while (rs.next()){


            lessons.add(single(rs));
        }

        return lessons ;


    }

}
