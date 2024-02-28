package controllers.teacher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TeacherMainPanelController {


    private @FXML StackPane spSubScene;
    private  @FXML Button dashboardBtnId ;


    public void IntitalState(){

      dashboardBtnId.fire();
    }

    public StackPane getSpSubScene() {
        return spSubScene;
    }

    public void openDashboard(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/Dashboard.fxml"));

            loader.load();

            TeacherDashboardController dashboardController = loader.getController();
            spSubScene.getChildren().clear();
            spSubScene.getChildren().add(dashboardController.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }

    }

    public void openCoursPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/cours/CoursPage.fxml"));

            loader.load();

            TeacherCoursController coursPage = loader.getController();

            coursPage.setShowMessage(true);
            coursPage.setStackPane(spSubScene);
            spSubScene.getChildren().clear();
            spSubScene.getChildren().add(coursPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


}
