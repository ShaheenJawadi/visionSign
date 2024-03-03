package controllers.MainPages.Cours.SinglePageComponants;

import entities.Cours;
import entities.Lesson;
import entities.UserCours;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CoursSingleLessonItem {

    @FXML
    private HBox lesssonItemHbox ;


    @FXML
    private Text title ;





    public  HBox getRoot(){
        return  this.lesssonItemHbox ;
    }


    public void  renderItem(Lesson l , UserCours userCours){


        title.setText(l.getTitre());





    }
}
