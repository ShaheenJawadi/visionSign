package controllers.MainPages.Cours;

import State.MainNavigations;
import controllers.MainPages.Cours.SinglePageComponants.CoursLessonItemController;
import controllers.MainPages.Cours.SinglePageComponants.CoursSingleLessonItem;
import controllers.studentForum.DisplayImgTest;
import entities.Cours;
import entities.Lesson;
import entities.UserCours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CoursLessonsPageController implements Initializable {


    @FXML

    private VBox CoursLessonsVboxHolder ;

    @FXML
    private VBox webViewHolder ;


    @FXML
    private  VBox listLessonsHolder ;


    @FXML
    private Button nextLessonBtnId ;


    @FXML
    private ImageView lessonThumbnail ;

    private Cours cours ;
    private  int currentStage ;




    public void setCours(Cours cours){

        cours.fetchUserCoursActivity();
        this.cours = cours ;
        System.out.println(cours);
        System.out.println(cours.getUserCoursActivity());

        currentStage =cours.getUserCoursActivity().getStage();
    }



    public VBox getVBoxRoot() {
        return CoursLessonsVboxHolder;
    }








    public void  renderCoursLessons(){




        renderLessonList();
        manipulateCurrentLesson();
        nextBtnState();



    }

    public void  renderLessonList(){
        listLessonsHolder.getChildren().clear();
        try {
            // Load customItem.fxml for each item

            for(int i = 0; i < cours.getLessons().size(); i++) {
                Lesson l = cours.getLessons().get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Lesson/LessonItem.fxml"));
                loader.load();

                CoursSingleLessonItem controller = loader.getController();
                controller.renderItem(l , cours.getUserCoursActivity() , i);

                listLessonsHolder.getChildren().add(controller.getRoot());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void renderCurrentLesson(Lesson l){
        WebView webView = new WebView() ;
        webView.getEngine().loadContent(l.getContent());


        if(!l.getImage().isEmpty()){
            DisplayImgTest imageLoader = new DisplayImgTest(lessonThumbnail, l.getImage());
            imageLoader.loadImage();
        }

        webViewHolder.getChildren().add(webView);

    }

    public  void  manipulateCurrentLesson(){

        int currentStage = cours.getUserCoursActivity().getStage() ;

        if(cours.getLessons().size()==0){

            return ;

        }

        if(cours.getLessons().size()>=currentStage){
            renderCurrentLesson(cours.getLessons().get(currentStage));
        }


    }


    @FXML
    void nextLessonBtn(ActionEvent event) {

        openQuizz();
        if(cours.getLessons().size()>currentStage){



            cours.getUserCoursActivity().setStage( cours.getUserCoursActivity().incrementStage())  ;

            renderCoursLessons();
            renderLessonList();
        }
        nextBtnState();

    }



    void  nextBtnState(){
        if(cours.getUserCoursActivity().isCompleted()){
            nextLessonBtnId.setVisible(false);
        }
        else {
            if (cours.getLessons().size() > currentStage) {
                nextLessonBtnId.setText("Suivant");
            } else if (cours.getLessons().size() == currentStage) {
                nextLessonBtnId.setText("Passer le Quizz");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       // renderCoursLessons();
    }



    public  void openQuizz(){


        MainNavigations.getInstance().openQuizzPage(cours.getId());

    }




}
