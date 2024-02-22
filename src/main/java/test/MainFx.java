
package test;

import controllers.teacher.TeacherMainPanelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

   public class MainFx  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/TeacherPanel.fxml"));

        Parent root= loader.load();

        Scene scene =new Scene(root);
        TeacherMainPanelController mainViewController = loader.getController();

        mainViewController.IntitalState();
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();


    }


      public  static  void main(String[] args){
        launch(args);

    }
}
