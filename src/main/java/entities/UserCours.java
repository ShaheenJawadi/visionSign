package entities;

import services.cours.UserCoursServices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserCours {

//todo DATABASE
    private int id ;
    private int userId ;
    private int coursId ;
    private boolean isCorrectQuizz ;
    private int stage ;
    private boolean isCompleted ;
    private Date enrollmentDate ;
    private Date completedDate ;


    private Cours userCours ;





    public UserCours() {
    }


    public UserCours(int userId, int coursId) {
        this.userId = userId;
        this.coursId = coursId;


    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }




    public boolean isCorrectQuizz() {
        return isCorrectQuizz;
    }

    public void setCorrectQuizz(boolean correctQuizz) {
        isCorrectQuizz = correctQuizz;
    }


    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Cours getUserCours() {
        return userCours;
    }

    public void setUserCours(Cours userCours) {
        this.userCours = userCours;
    }




    public int incrementStage(){
        UserCoursServices userCoursServices = new UserCoursServices();
        this.stage = this.stage+1 ;
        try {
            userCoursServices.incrementStage(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.stage ;

    }
    public  void setCompletedCours(){


        UserCoursServices userCoursServices = new UserCoursServices();

        try {
            this.isCompleted = true ;
            userCoursServices.setCompleted(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
