package controllers.Reclamations;

import entities.Reclamations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Reclamations.ReclamationsServices;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierRecController {

    @FXML
    private TextField id;

    @FXML
    private TextField typeFx;

    @FXML
    private TextArea desFx;

    @FXML
    private TextField statFx;

    @FXML
    private DatePicker dateFx;

    @FXML
    private TextField userFx;

    private Reclamations selectedReclamation=new Reclamations();
private ReclamationsServices reclamationsServices=new ReclamationsServices();
    public void initData(Reclamations reclamation) {
        selectedReclamation = reclamation;
        id.setText(String.valueOf(reclamation.getId_reclamation())); // Affichage de l'ID de la réclamation
        typeFx.setText(reclamation.getType());
        desFx.setText(reclamation.getDescription());
        statFx.setText(reclamation.getStatus());

        dateFx.setValue(reclamation.getLocalDate());

        userFx.setText(String.valueOf(reclamation.getId_user()));
    }

    @FXML
    void modifier(ActionEvent event) {
        // Mettre à jour les attributs de la réclamation avec les valeurs des champs de saisie
        selectedReclamation.setType(typeFx.getText());
        selectedReclamation.setDescription(desFx.getText());
        selectedReclamation.setStatus(statFx.getText());
        selectedReclamation.setDate(Date.valueOf(dateFx.getValue()));
        selectedReclamation.setId_user(Integer.parseInt(userFx.getText()));

        // Appeler la méthode de service pour modifier la réclamation dans la base de données
        try {
            reclamationsServices.update(selectedReclamation);
            // Fermer la fenêtre de modification après avoir enregistré les modifications
            Stage stage = (Stage) id.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LocalDate localDate = dateFx.getValue();
        Date date = Date.valueOf(localDate);
        try {
            reclamationsServices.update(new Reclamations(typeFx.getText(), desFx.getText(), statFx.getText(), date, Integer.parseInt(userFx.getText())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
