package State;

import entities.Cours;
import entities.Lesson;
import entities.UserCours;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import services.cours.UserCoursServices;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserOPState {
    private static UserOPState instance;


    private ArrayList<UserCours> userEnrollmentsCours ;
    @FXML
    private Text nbEnrolledCours;

    private UserOPState() {
        System.out.println("cons");
        this.fetchUserEnrollement();
    }


    public static UserOPState getInstance() {
        System.out.println("getins");
        if (instance == null) {
            System.out.println("fnull");
            instance = new UserOPState();
        }
        return instance;
    }


    public ArrayList<UserCours> getUserEnrollmentsCours() {
        return userEnrollmentsCours;
    }



    public  void fetchUserEnrollement()   {

        UserCoursServices userCoursServices = new UserCoursServices() ;
        //TODO userId
        try {
            if(UserSessionManager.getInstance().isUserLoggedIn())
            this.userEnrollmentsCours = (ArrayList< UserCours>)  userCoursServices.getByUser(UserSessionManager.getInstance().getCurrentUser().getId());
            setEnrollementCoursNumber();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public  void setNbEnrolementTextView(Text text){
        this.nbEnrolledCours = text;
        setEnrollementCoursNumber();

    }
    public  void setEnrollementCoursNumber(){

        if(nbEnrolledCours != null && userEnrollmentsCours!= null)
            nbEnrolledCours.setText(String.valueOf(userEnrollmentsCours.size()));

    }

}
