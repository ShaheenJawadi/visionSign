package controllers.Reclamations;

import entities.Reclamations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.Reclamations.ReclamationsServices;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AfficherRecController {

    @FXML
    private TableView<Reclamations> table;

    @FXML
    private TableColumn<Reclamations, LocalDate> date;

    @FXML
    private TableColumn<Reclamations, String> desc;

    @FXML
    private TableColumn<Reclamations, Integer> id;

    @FXML
    private TableColumn<Reclamations, String> rep;

    @FXML
    private TableColumn<Reclamations, String> stat;

    @FXML
    private TableColumn<Reclamations, String> type;

    @FXML
    private TableColumn<Reclamations, Integer> user;
    @FXML
    private Button back;
    @FXML
    void back(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/main.fxml"));
            Parent root=loader.load();
            back.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }}

    private final ReclamationsServices reclamationsServices = new ReclamationsServices();

    @FXML
    void initialize() {
        try {
            List<Reclamations> reclamations = reclamationsServices.recuperer();
            ObservableList<Reclamations> observableList = FXCollections.observableList(reclamations);
            table.setItems(observableList);
            configureTableColumns();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configureTableColumns() {
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        id.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        stat.setCellValueFactory(new PropertyValueFactory<>("status"));
        rep.setCellValueFactory(new PropertyValueFactory<>("repondre"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadReclamations() {
        try {
            table.getItems().clear();
            table.getItems().addAll(reclamationsServices.recuperer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamtions/AjouterRec.fxml"));
            Parent root = loader.load();
            table.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
