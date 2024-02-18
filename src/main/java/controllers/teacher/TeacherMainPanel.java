package controllers.teacher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TeacherMainPanel {


    private @FXML StackPane spSubScene;
    private  @FXML Button dashboardBtnId ;


    public void IntitalState(){

      dashboardBtnId.fire();
    }



    public void openDashboard(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/Dashboard.fxml"));

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

    public void AddCours(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/cours/CoursPage.fxml"));

            loader.load();

            TeacherCoursController addCourrs = loader.getController();
            spSubScene.getChildren().clear();
            spSubScene.getChildren().add(addCourrs.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


}
