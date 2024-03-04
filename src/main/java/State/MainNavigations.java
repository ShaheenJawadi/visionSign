package State;

import controllers.MainPages.Cours.CoursLessonsPageController;
import controllers.MainPages.Cours.Filter.FilterController;
import controllers.MainPages.Cours.SingleCoursController;
import controllers.MainPages.MainPageController;
import controllers.Student.QuizEleveController;
import controllers.studentForum.*;
import entities.Cours;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainNavigations {
    private static MainNavigations instance;


    @FXML
    private VBox mainPageHolder;


    private MainNavigations() {

    }

    public static MainNavigations getInstance() {
        if (instance == null) {
            instance = new MainNavigations();
        }
        return instance;
    }



    public  void setPaheHoloder(VBox vBox){
        mainPageHolder =vBox;

    }


    public void openCoursFilterPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Filter/index.fxml"));

            loader.load();

            FilterController coursFilterPage = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(coursFilterPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void openSingleCoursPage(Cours cours){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Main/index.fxml"));

            loader.load();

            SingleCoursController coursFilterPage = loader.getController();
             coursFilterPage.renderContent(cours);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(coursFilterPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void openMainHomePage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/HomePage/Componants.fxml"));

            loader.load();

            MainPageController page = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }



    public void openCoursLessonPage(Cours cours){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Lesson/index.fxml"));

            loader.load();

            CoursLessonsPageController coursLessonPage = loader.getController();

            coursLessonPage.setCours(cours);
            coursLessonPage.renderCoursLessons();




            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(coursLessonPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void openQuizzPage(int coursId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Student/QuizEleve.fxml"));

            loader.load();

            QuizEleveController page = loader.getController();
            page.setCoursId(coursId);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }




    public void openForumPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));

            loader.load();

            ForumGetAllPublicationsController page = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }




    public void openChatbotForumPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ChatBot.fxml"));

            loader.load();

            ChatBotController page = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }



    public void openAddPublicationForumPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/AddPublication.fxml"));

            loader.load();

            AddPublicationController page = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }



    public void openModifyPublicationForumPage(int pubId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ModifyPublication.fxml"));

            loader.load();

            ModifyPublicationController page = loader.getController();
            page.setPubId(pubId);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void openPublicationDetailsForumPage(int pubId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/PublicationDetails.fxml"));

            loader.load();

            PublicationDetailsController page = loader.getController();
            page.setPubId(pubId);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }





}