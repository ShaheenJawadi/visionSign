package State;

import entities.Cours;
import entities.Lesson;
import entities.UserCours;
import services.cours.UserCoursServices;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserOPState {
    private static UserOPState instance;


    private ArrayList<UserCours> userEnrollmentsCours ;


    private UserOPState() {
        System.out.println("cons");
        this.fetchUserEnrollement();
    }


    public static UserOPState getInstance() {
        System.out.println("getins");
        if (instance == null) {
            instance = new UserOPState();
        }
        return instance;
    }


    public ArrayList<UserCours> getUserEnrollmentsCours() {
        return userEnrollmentsCours;
    }

    public void setUserEnrollmentsCours(ArrayList<UserCours> userEnrollmentsCours) {
        this.userEnrollmentsCours = userEnrollmentsCours;
    }

    public  void fetchUserEnrollement()   {
        UserCoursServices userCoursServices = new UserCoursServices() ;
        //TODO userId
        try {
            this.userEnrollmentsCours = (ArrayList< UserCours>)  userCoursServices.getByUser(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }





}
