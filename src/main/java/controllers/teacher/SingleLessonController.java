package controllers.teacher;

import entities.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.scene.layout.VBox;
import validation.ValidateCours;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleLessonController implements Initializable {


    @FXML
    private HTMLEditor Content;

    @FXML
    private TextField Duration;

    @FXML
    private TextField Order;

    @FXML
    private TextField Title;

    @FXML
    private TextField Video;

    @FXML
    private Button leconImage;

    @FXML
    private VBox singleLessonBox;

    @FXML
    private Label validateContent;

    @FXML
    private Label validateDuration;

    @FXML
    private Label validateLink;

    @FXML
    private Label validateOrder;

    @FXML
    private Label validateTitle;


    public VBox getSingleLessonBox() {
        return singleLessonBox;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initValidation();
    }

    public  void initValidation(){


        validateContent.setVisible(false);

        validateDuration.setVisible(false);

        validateLink.setVisible(false);

        validateOrder.setVisible(false);

        validateTitle.setVisible(false);
    }

    public String getLessonData() {

        return Title.getText()+"sdqdq";
    }



    public boolean vaidateLessonFields(){





      boolean  isValid =   ValidateCours.isNotEmpty(Video ,validateLink , "ee")
                        && ValidateCours.isNotEmpty(Title ,validateTitle , "ee")
                        && ValidateCours.isNotEmptyNumber(Order ,validateOrder , "ee")
              && ValidateCours.isNotEmptyNumber(Duration ,validateDuration , "ee")
              && ValidateCours.isNotHTML(Content ,validateContent , "ee") ;

      return isValid ;
    }

    public Lesson getLesson(int coursId){
        if(vaidateLessonFields()){


            Lesson lesson = new Lesson();
            lesson.setCoursId(coursId);
            lesson.setTitre(Title.getText());
            lesson.setContent(Content.getHtmlText());
            lesson.setDuree(Integer.valueOf(Duration.getText()));
            lesson.setClassement(Integer.valueOf(Order.getText()));
            lesson.setImage("qdsdq");
            lesson.setVideo(Video.getText());
            System.out.println(lesson);
            return  lesson ;


        }
        else
            return null;

    }
}
