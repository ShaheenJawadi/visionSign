package controllers.Categorie;

import entities.Categorie;
import entities.SousCategorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.Categorie.CategorieService;
import services.SousCategorie.SousCategorieService;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterCategorieController {
    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField image;

    private final CategorieService categorieService = new CategorieService();

    @FXML
    void ajouter(ActionEvent event) {
        try {
            categorieService.addCategorie(new Categorie( nomTF.getText(),descriptionTF.getText(), image.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Categorie ajoutée avec succès");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/categorie/AfficherCategorie.fxml"));
            Parent root = loader.load();
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
