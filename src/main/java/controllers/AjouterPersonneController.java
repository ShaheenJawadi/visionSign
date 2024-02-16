package controllers;

import entities.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.PersonneService;

import java.sql.SQLException;


public class AjouterPersonneController  {

    @FXML
    private TextField ageId;

    @FXML
    private Button btn;

    @FXML
    private TextField nomId;

    @FXML
    private TextField prenomId;

    @FXML
    void ajouter(ActionEvent event)  {
        String  nom = nomId.getText();
        String  prenom = prenomId.getText();
        String  age = ageId.getText();
        System.out.println(nomId.getText());
        PersonneService ps =new PersonneService();
        try {
            ps.ajouter(new Personne(Integer.parseInt(age) , nom , prenom));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("success !");

            alert.showAndWait();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error !");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }



    }

}
