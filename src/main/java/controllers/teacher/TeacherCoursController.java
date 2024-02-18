package controllers.teacher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TeacherCoursController {

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/cours/Create/NewCours.fxml"));

            loader.load();

            TeacherCoursController addCourrs = loader.getController();
            addCourrs.setStackPane(spSubScene);
            spSubScene.getChildren().clear();
            spSubScene.getChildren().add(addCourrs.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }





    }

}
