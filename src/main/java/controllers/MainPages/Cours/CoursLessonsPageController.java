package controllers.MainPages.Cours;

import controllers.MainPages.Cours.SinglePageComponants.CoursLessonItemController;
import controllers.MainPages.Cours.SinglePageComponants.CoursSingleLessonItem;
import entities.Cours;
import entities.Lesson;
import entities.UserCours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

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



    private Cours cours ;




    public void setCours (Cours cours){

        this.cours = cours ;
    }



    public VBox getVBoxRoot() {
        return CoursLessonsVboxHolder;
    }








    public void  renderCoursLessons(){




     renderLessonList();
        manipulateCurrentLesson();



    }

    public void  renderLessonList(){
        listLessonsHolder.getChildren().clear();
        try {
            // Load customItem.fxml for each item

            for (Lesson l : cours.getLessons()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Lesson/LessonItem.fxml"));
                loader.load();

                CoursSingleLessonItem controller = loader.getController();
                controller.renderItem(l , cours.getUserCoursActivity());

                listLessonsHolder.getChildren().add(controller.getRoot());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void renderCurrentLesson(Lesson l){
        WebView webView = new WebView() ;
        webView.getEngine().loadContent(l.getContent());



        webViewHolder.getChildren().add(webView);

    }

    public  void  manipulateCurrentLesson (){
        int currentStage = cours.getUserCoursActivity().getStage() ;
        if(cours.getLessons().size()>=currentStage){
            renderCurrentLesson(cours.getLessons().get(currentStage));
        }

    }


    @FXML
    void nextLessonBtn(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       // renderCoursLessons();
    }





}
