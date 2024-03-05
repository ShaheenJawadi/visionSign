package controllers.teacher;

import State.TeacherNavigations;
import controllers.studentForum.DisplayImgTest;
import entities.Cours;
import entities.Lesson;
import entities.Ressource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import services.cours.CoursService;
import services.lesson.LessonService;
import services.ressource.RessourceService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CoursItemController   {

    @FXML
    private Text niveau;

    @FXML
    private Text duree;

    @FXML
    private Text nbLecons;

    @FXML
    private Text ressource;

    @FXML
    private Text sousCategorie;

    @FXML
    private Text title;


    @FXML
    private MenuItem ajouterQuizBtn;



    private  Cours cours ;


    public void putData(Cours cours) throws SQLException {
            this.cours  =cours ;

        title.setText(cours.getNom());
        sousCategorie.setText("sous Category");
       niveau.setText(String.valueOf(cours.getNiveauId()));



        RessourceService ressourceService = new RessourceService();

        Ressource res =ressourceService.getByCours(cours.getId()) ;
        if(res==null ){
            res = new Ressource();
        }

       ressource.setText(res.getType());

        LessonService lessonService = new LessonService();
        List<Lesson> lessons = lessonService.getByCours(cours.getId());


        nbLecons.setText(String.valueOf(lessons.size()));

        int lesson_duree =0 ;

        for(Lesson lesson :lessons){
            lesson_duree+=lesson.getDuree();
        }


        duree.setText(String.valueOf(lesson_duree));

    }


    @FXML
    void deleteCours(ActionEvent event) throws SQLException {
        System.out.println(cours.getId());
        CoursService coursService = new CoursService() ;
        coursService.delete(cours.getId());


    }

    @FXML
    void editCours(ActionEvent event) {

        System.out.println(cours.getId());


    }


    @FXML
    void ajouterQuiz(ActionEvent event) {

        TeacherNavigations.getInstance().openQuizzPage(cours.getId());


    }

}
