package State;

import controllers.EnseignantQuiz.DisplayQuizController;
import controllers.EnseignantQuiz.Newquestcontroller;
import controllers.EnseignantQuiz.Newquizzcontroller;
import controllers.MainPages.Cours.CoursLessonsPageController;
import controllers.MainPages.Cours.Filter.FilterController;
import controllers.MainPages.Cours.SingleCoursController;
import controllers.MainPages.MainPageController;
import controllers.Student.QuizEleveController;
import controllers.teacher.ManageCoursController;
import controllers.teacher.TeacherCoursController;
import controllers.teacher.TeacherDashboardController;
import entities.Cours;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TeacherNavigations {
    private static TeacherNavigations instance;


    @FXML
    private VBox mainPageHolder;


    private TeacherNavigations() {

    }

    public static TeacherNavigations getInstance() {
        if (instance == null) {
            instance = new TeacherNavigations();
        }
        return instance;
    }



    public  void setPaheHoloder(VBox vBox){
        mainPageHolder =vBox;

    }


    public void openCoursListPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/cours/CoursPage.fxml"));

            loader.load();

            TeacherCoursController coursPage = loader.getController();

            coursPage.setShowMessage(true);

            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(coursPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void openAddCoursPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/cours/create/NewCours.fxml"));
            loader.load();
            ManageCoursController addCoursPage = loader.getController();

            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(addCoursPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void openDashboardPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/Dashboard.fxml"));

            loader.load();

            TeacherDashboardController dashboardController = loader.getController();
            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(dashboardController.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }
    public void openQuizzPage(int coursId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/quiz/newquizz.fxml"));

            loader.load();

            Newquizzcontroller page = loader.getController();
            page.setCoursId(coursId);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }





    public void openQuestionFromQuizz(int coursId , int quizzId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/quiz/NewQuestion.fxml"));
            Parent root = loader.load();
            Newquestcontroller newquestcontroller = loader.getController();
            newquestcontroller.setQuizId(quizzId);
            newquestcontroller.setCoursId(coursId);





            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(newquestcontroller.getVboxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }




    public void openDisplayQuestionFromQuizz(int coursId , int quizzId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/quiz/DisplayQuiz.fxml"));
            Parent root = loader.load();

            DisplayQuizController displayQuizController = loader.getController();
            displayQuizController.setDisplayQuizId(quizzId);
            displayQuizController.setCoursId(coursId);





            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(displayQuizController.getRootId());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }





}