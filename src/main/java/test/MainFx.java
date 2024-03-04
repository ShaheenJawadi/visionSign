package test;

import controllers.MainController;
import controllers.teacher.TeacherMainPanelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx  extends Application  {
    @Override
    public void start(Stage primaryStage) throws Exception {


       // OpenTeacherPanel(primaryStage);
        OpenMainPagel(primaryStage);
        //OpenLogin(primaryStage);


    }


    public void OpenTeacherPanel(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/TeacherPanel.fxml"));

        Parent root= loader.load();

        Scene scene =new Scene(root);
        TeacherMainPanelController mainViewController = loader.getController();

        mainViewController.IntitalState();
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }


    public void OpenMainPagel(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/HomePage/Index.fxml"));

        Parent root= loader.load();

        Scene scene =new Scene(root);
        MainController mainViewController = loader.getController();

       // mainViewController.IntitalState();
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(600);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
    public void OpenLogin(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));

        Parent root=loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public  static  void main(String[] args){
        launch(args);

    }
}
