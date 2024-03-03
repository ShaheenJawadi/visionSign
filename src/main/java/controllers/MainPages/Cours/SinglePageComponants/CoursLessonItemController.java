package controllers.MainPages.Cours.SinglePageComponants;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CoursLessonItemController {

    @FXML
    private Text duration;

    @FXML
    private Text title;


    @FXML
    private VBox lessonItem ;


    public VBox getRooot(){
        return lessonItem;
    }

    public void renderItem(String title  ,String duration ){

        this.duration.setText(duration);
        this.title.setText(title);

    }


}
