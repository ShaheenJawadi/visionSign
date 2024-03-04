package controllers.Categorie;

import entities.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.Categorie.CategorieService;

import java.sql.SQLException;
import java.util.List;

public class AfficherCategorie {

    @FXML
    private TableColumn<Categorie, Integer> nbCol;

    @FXML
    private TableColumn<Categorie, String> nomCol;

    @FXML
    private TableColumn<Categorie, String> descCol;

    @FXML
    private TableView<Categorie> tableView;

    private final CategorieService categorieService = new CategorieService();

    @FXML
    void initialize() {
        try {
            List<Categorie> Categories = categorieService.getAllCategories();
            ObservableList<Categorie> observableList = FXCollections.observableList(Categories);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            nbCol.setCellValueFactory(new PropertyValueFactory<>("nbSousCategorie"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
