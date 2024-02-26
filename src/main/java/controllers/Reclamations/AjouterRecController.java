package controllers.Reclamations;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Reclamations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Reclamations.ReclamationsServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AjouterRecController {
    ReclamationsServices pubs = new ReclamationsServices();
    @FXML
    private Button forumBtn;
    @FXML
    private DatePicker dateFx;
    @FXML
    public AnchorPane listepubid;
    @FXML
    private TextField desFx;
    @FXML
    private TextField searchField1;
    @FXML
    private TextField statFx,userid;

    @FXML
    private TableColumn<Reclamations, String> descfx;

    @FXML
    private TableColumn<Reclamations, Integer> idfx;
    List<Reclamations> mypub;



    @FXML
    private ComboBox<String> typeCombo;


    @FXML
    private Button back;

    private ReclamationsServices reclamationsServices = new ReclamationsServices();

    @FXML
    void back(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
            Parent root = loader.load();
            back.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouter(ActionEvent event) {
        try {
            String typeReclamation = typeCombo.getValue();
            //int idUtilisateur = userCombo.getValue();
            Reclamations nouvelleReclamation = new Reclamations(typeReclamation, desFx.getText(), statFx.getText(), Integer.parseInt(userid.getText()));
            reclamationsServices.ajouterRec(nouvelleReclamation, typeReclamation); // Utilisation de la méthode ajouterRec
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Réclamation ajoutée avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        try {
            // Charger les réclamations depuis le service
            mypub = reclamationsServices.recuperer(); // Initialise mypub
            // Créer une liste observable à partir des réclamations chargées
            ObservableList<Reclamations> observableList = FXCollections.observableList(mypub);
            // Définir les réclamations dans la table
            // Définir les cellules pour les colonnes ID et Description
            idfx.setCellValueFactory(new PropertyValueFactory<>("type"));
            descfx.setCellValueFactory(new PropertyValueFactory<>("date"));

            // Charger les IDs utilisateurs dans la combobox

            // Charger les types de réclamation dans la combobox
            typeCombo.getItems().addAll(Arrays.asList(ReclamationsServices.TYPES));

        } catch (SQLException e) {
            // Gérer toute exception SQL en affichant une alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite : " + e.getMessage());
            alert.showAndWait();
        }
    }





    private Pane createPublicationPane(Reclamations publication, int index) {
        Pane pane = new Pane();
        pane.setPrefSize(271, 75);
        pane.setLayoutX(-1);
        pane.setLayoutY(7 + index * (75));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");

        Text titreText = new Text(publication.getType());
        titreText.setLayoutX(14);
        titreText.setLayoutY(28);
        titreText.setFont(new Font("System Bold", 12));
        titreText.setFill(Color.web("#494949"));

        Text dateText = new Text(publication.getDate().toString());
        dateText.setLayoutX(14);
        dateText.setLayoutY(54);
        dateText.setFill(Color.web("#a5a5a5"));


        Button modifyButton = new Button();
        modifyButton.setLayoutX(202);
        modifyButton.setLayoutY(37);
        modifyButton.setPrefSize(28, 25);
        modifyButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        modifyIcon.setFill(Color.web("#2447f9"));
        modifyIcon.setSize("15");
        modifyButton.setGraphic(modifyIcon);
        modifyButton.setCursor(Cursor.HAND);
        modifyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamtions/ModifierRec.fxml"));
                    Parent root = loader.load();
                    ModifierRecController modifyController = loader.getController();
                    modifyController.setPubId(mypub.get(index).getId_reclamation());
                    System.out.println(mypub.get(index).getId_reclamation());
                    forumBtn.getScene().setRoot(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button deleteButton = new Button();
        deleteButton.setLayoutX(228);
        deleteButton.setLayoutY(37);
        deleteButton.setPrefSize(28, 25);
        deleteButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        deleteIcon.setFill(Color.web("#2447f9"));
        deleteIcon.setSize("15");
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    pubs.supprimer(mypub.get(index).getId_reclamation());
                    System.out.println("deleted!");
                    listepubid.getChildren().remove(pane);
                    mypub.remove(index);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succes!");
                    alert.setContentText("Publication supprimé!");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR!");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        });

        pane.getChildren().addAll(titreText, dateText, modifyButton, deleteButton);


        return pane;
    }

    public void handleForum(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamtions/AfficherRecU.fxml"));
            Parent root = loader.load();
            forumBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchPubByTitle1(ActionEvent actionEvent) {
        String searchText = searchField1.getText();
        int userID = 2;
        try {

            if (searchText.isEmpty()) {
                mypub = (List<Reclamations>) pubs.getReclmationsById(userID);
            } else {
                mypub = pubs.searchreclamationbyType(searchText, userID);
            }
            listepubid.getChildren().clear();
            for (int i = 0; i < mypub.size(); i++) {
                Pane pane = createPublicationPane(mypub.get(i), i);
                listepubid.getChildren().add(pane);
            }
            listepubid.setPrefHeight(mypub.size() * 85);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void refreshPublications() {
        listepubid.getChildren().clear();
    }
}
