package controllers.Categorie;

import entities.SousCategorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.SousCategorie.SousCategorieService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterSousCategorieController {
    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField statusTF;

    private final SousCategorieService sousCategorieService = new SousCategorieService();

    @FXML
    void ajouter(ActionEvent event) {
        try {
            sousCategorieService.addSousCategorie(new SousCategorie( nomTF.getText(),descriptionTF.getText(), statusTF.getText(), 1));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("SousCategorie ajoutée avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void naviguer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/categorie/AfficherSousCategorie.fxml"));
            Parent root = loader.load();
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
