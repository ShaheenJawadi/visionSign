package controllers.avis;

import entities.Avis;
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

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
            ObservableList<Avis> observableList = FXCollections.observableList(avis);
            table.setItems(observableList);
            configureTableColumns();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configureTableColumns() {
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        id.setCellValueFactory(new PropertyValueFactory<>("id_avis"));
        user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        mess.setCellValueFactory(new PropertyValueFactory<>("message"));
        cour.setCellValueFactory(new PropertyValueFactory<>("coursId"));

    }

    public void back(javafx.event.ActionEvent actionEvent) {
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/main.fxml"));
            Parent root=loader.load();
            back.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshPublications() {
    }
}
