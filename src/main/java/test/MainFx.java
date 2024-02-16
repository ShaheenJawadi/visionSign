package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // lancement de l'interface graphique
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));
        //getClass().getResource() pour acceder au fichier ressource
        //stocker dans une variable de type AnchorPane
        // AnchorPane root= loader.load(); erreur car specifique
        // generique howa Parent
        Parent root= loader.load();
        // ajouter dans scene
        Scene scene =new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public  static  void main(String[] args){
        launch(args);

    }
}
