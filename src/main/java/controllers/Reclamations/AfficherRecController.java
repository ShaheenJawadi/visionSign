package controllers.Reclamations;

import entities.Reclamations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import services.Reclamations.ReclamationsServices;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
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
    private PieChart pieChartStatistiques;
    @FXML
    private Button back, mailid;


    private final ReclamationsServices reclamationsServices = new ReclamationsServices();

    @FXML
    void initialize() {
        try {
            List<Reclamations> reclamations = reclamationsServices.recuperer();
            trierReclamationsParReponse(reclamations); // Tri des réclamations par statut de réponse
            ObservableList<Reclamations> observableList = FXCollections.observableList(reclamations);
            table.setItems(observableList);
            configureTableColumns();
            loadReclamations();
            afficherStatistiques();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void trierReclamationsParReponse(List<Reclamations> reclamations) {
        Comparator<Reclamations> reclamationComparator = new Comparator<Reclamations>() {
            @Override
            public int compare(Reclamations r1, Reclamations r2) {
                if (r1.isRepondre() && !r2.isRepondre()) {
                    return -1;
                } else if (!r1.isRepondre() && r2.isRepondre()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        Collections.sort(reclamations, reclamationComparator);
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
    void enoyerrep(ActionEvent actionEvent) {
        Reclamations reclamations = table.getSelectionModel().getSelectedItem();
        if (reclamations != null) {
            int userId = reclamations.getId_user();
            String userEmail = ReclamationsServices.getUserEmailById(userId);
            if (userEmail != null) {
                sendEmailToGmail(userEmail);
                try {
                    reclamationsServices.marquerCommeRepondu(reclamations.getId_reclamation());
                    loadReclamations(); // Recharger la liste pour montrer la mise à jour
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Erreur lors de la mise à jour de la réclamation.");
                }
            } else {
                showAlert("Impossible de trouver l'adresse e-mail de l'utilisateur.");
            }
        } else {
            showAlert("Veuillez sélectionner une réclamation.");
        }
    }

    private void sendEmailToGmail(String userEmail) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().mail(new URI("mailto:" + userEmail));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("L'ouverture de Gmail n'est pas prise en charge sur cette plateforme.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private ListView<String> listViewStatistiques;

    public void afficherStatistiques() {

        try {
            Map<String, Integer> stats = reclamationsServices.compterReclamationsParType();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            stats.forEach((type, count) -> pieChartData.add(new PieChart.Data(type, count)));
            pieChartStatistiques.setData(pieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur lors de la récupération des statistiques.");
        }
    }
//    public void afficherStatistiques() {
//        try {
//            Map<String, Integer> stats = reclamationsServices.compterReclamationsParReponse(); // Utilisez la méthode mise à jour
//            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
//            stats.forEach((reponse, count) -> pieChartData.add(new PieChart.Data(reponse, count))); // Utilisez les réponses pour créer les données du graphique
//            pieChartStatistiques.setData(pieChartData);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert("Erreur lors de la récupération des statistiques.");
//        }
//    }
//
//    }
}


