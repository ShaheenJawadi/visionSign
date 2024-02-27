package controllers.teacher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherCoursController implements Initializable{

    private @FXML VBox vbRoot;
    private  StackPane spSubScene;
    private  @FXML VBox gridHolder ;



    VBox getVBoxRoot()
    {
        return vbRoot;
    }

    public void setStackPane(StackPane stackPane) {
        this.spSubScene = stackPane;
    }

    @FXML
    public  void openAddCoursPageBtn()   {
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/cours/create/NewCours.fxml"));
            loader.load();
            ManageCoursController addCoursPage = loader.getController();
            addCoursPage.setStackPane(spSubScene);
            spSubScene.getChildren().clear();
            spSubScene.getChildren().add(addCoursPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/cours/list/CoursGridPane.fxml"));
             loader.load();
            CoursGridPane controller = loader.getController();
            controller.setStackPane(spSubScene);

            gridHolder.getChildren().add(controller.getCoursGridHolder());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
