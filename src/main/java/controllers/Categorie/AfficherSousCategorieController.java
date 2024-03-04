package controllers.Categorie;

import entities.Categorie;
import entities.SousCategorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.SousCategorie.SousCategorieService;

import java.sql.SQLException;
import java.util.List;

public class AfficherSousCategorieController {

    @FXML
    private TableColumn<Categorie, String> statusCol;

    @FXML
    private TableColumn<SousCategorie, String> nomCol;

    @FXML
    private TableColumn<SousCategorie, String> descriptionCol;

    @FXML
    private TableView<SousCategorie> tableView;

    private final SousCategorieService sousCategorieService = new SousCategorieService();

    @FXML
    void initialize() {
        try {
            List<SousCategorie> sousCategories = sousCategorieService.getSousCategorieList();
            ObservableList<SousCategorie> observableList = FXCollections.observableList(sousCategories);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
