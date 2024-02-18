package controllers.Reclamations;

import entities.Reclamations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.Reclamations.ReclamationsServices;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterRecController {

    @FXML
    private DatePicker dateFx;

    @FXML
    private TextArea desFx;

    @FXML
    private TextField statFx;

    @FXML
    private TextField typeFx;

    @FXML
    private TextField userFx;
    private ReclamationsServices reclamationsServices=new ReclamationsServices();

    @FXML
    void ajouter(ActionEvent event) {
        try {
            LocalDate localDate = dateFx.getValue();
            Date date = Date.valueOf(localDate); // Conversion de LocalDate en java.util.Date
            reclamationsServices.add(new Reclamations(typeFx.getText(), desFx.getText(), statFx.getText(), date, Integer.parseInt(userFx.getText())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Personne ajoutée avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherRec.fxml"));
            Parent root=loader.load();
            typeFx.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
