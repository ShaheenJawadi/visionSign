package controllers.Avis;

import entities.Avis;
import entities.Reclamations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.Reclamations.AvisServices;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AfficherAvisController {

    @FXML
    private TableColumn<Avis, LocalDate> Date;

    @FXML
    private TableColumn<Avis, Integer> cour;

    @FXML
    private TableColumn<Avis, Integer> id;

    @FXML
    private TableColumn<Avis, String> mess;

    @FXML
    private TableColumn<Avis, Integer> note;

    @FXML
    private TableView<Avis> table;

    @FXML
    private TableColumn<Avis, Integer> user;
    private final AvisServices avisServices = new AvisServices();
    @FXML
    private Button back;

    @FXML
    void initialize() {
        try {
            List<Avis> avis = avisServices.recuperer();
            trierAvisParNote(avis); // Tri des avis par note
            ObservableList<Avis> observableList = FXCollections.observableList(avis);
            table.setItems(observableList);
            configureTableColumns();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour trier les avis par note
    private void trierAvisParNote(List<Avis> avis) {
        Comparator<Avis> avisComparator = Comparator.comparingInt(Avis::getNote);
        Collections.sort(avis, avisComparator.reversed());
    }

    // Autres méthodes de la classe


    private void configureTableColumns() {
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        id.setCellValueFactory(new PropertyValueFactory<>("id_avis"));
        user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        mess.setCellValueFactory(new PropertyValueFactory<>("message"));
        cour.setCellValueFactory(new PropertyValueFactory<>("coursid"));

    }


    public void refreshPublications() {
    }

}
