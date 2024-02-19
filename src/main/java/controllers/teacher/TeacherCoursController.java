package controllers.teacher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherCoursController{

    private @FXML VBox vbRoot;
    private  StackPane spSubScene;



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


}
