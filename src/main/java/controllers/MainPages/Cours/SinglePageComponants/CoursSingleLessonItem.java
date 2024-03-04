package controllers.MainPages.Cours.SinglePageComponants;

import entities.Cours;
import entities.Lesson;
import entities.UserCours;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CoursSingleLessonItem {

    @FXML
    private HBox lesssonItemHbox ;


    @FXML
    private Text title ;


    @FXML
    private Circle indexCircle;



    public  HBox getRoot(){
        return  this.lesssonItemHbox ;
    }


    public void  renderItem(Lesson l , int stage, int showIndex ){

        if(showIndex>stage){
            indexCircle.setFill(Color.web("#f46a6a"));
        }
        else if(showIndex== stage){
            indexCircle.setFill(Color.web("#34C38F"));
        }
        else {
            indexCircle.setFill(Color.web("#00aeef"));
        }


        title.setText(l.getTitre());





    }
}
