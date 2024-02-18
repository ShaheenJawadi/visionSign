package controllers.Reclamations;

import entities.Reclamations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private TableView<Reclamations> table;

    @FXML
    private TableColumn<Reclamations, String> type;

    @FXML
    private TableColumn<Reclamations, Integer> user;
    private final ReclamationsServices reclamationsServices=new ReclamationsServices();
    @FXML
    void initialize() {
        try{
            List<Reclamations> reclamations=reclamationsServices.getAll();
            ObservableList<Reclamations> observableList= FXCollections.observableList(reclamations);
            table.setItems(observableList);
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            id.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
            user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            stat.setCellValueFactory(new PropertyValueFactory<>("status"));
            rep.setCellValueFactory(new PropertyValueFactory<>("repondre"));
            desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        }catch(SQLException e){ }
    }

    private void loadReclamations() {
        try {
            table.getItems().clear();
            table.getItems().addAll(reclamationsServices.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeleteButton(ActionEvent event) {
        Reclamations selectedReclamation = table.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                reclamationsServices.delete(selectedReclamation.getId_reclamation());
                loadReclamations(); // Recharger les données après la suppression
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
