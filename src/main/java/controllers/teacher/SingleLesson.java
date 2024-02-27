package controllers.teacher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.layout.VBox;
public class SingleLesson {

    @FXML
    private HTMLEditor leconContent;

    @FXML
    private TextField leconDuration;

    @FXML
    private Button leconImage;

    @FXML
    private TextField leconOrdre;

    @FXML
    private TextField leconTitle;

    @FXML
    private TextField leconVideo;


    @FXML
    private VBox singleLessonBox;


    public VBox getSingleLessonBox() {
        return singleLessonBox;
    }




}
