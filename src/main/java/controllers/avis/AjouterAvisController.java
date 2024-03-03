package controllers.avis;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Avis;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Reclamations.AvisServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterAvisController {
    @FXML
    public AnchorPane listepubid;
    @FXML
    private TextField notefx;
    @FXML
    private TextField searchField1;

    @FXML
    private TextField mess,userid;

    @FXML
    private ComboBox<String> coursCombo;

    private AvisServices avisServices;
    @FXML
    private Button forumBtn;
    AvisServices pubs = new AvisServices();

    public AjouterAvisController() {
        this.avisServices = new AvisServices();
    }

    @FXML
    private void initialize() {
        // Remplir la combobox avec les noms des cours
        coursCombo.getItems().addAll(avisServices.recupererNomsCours());
    }

    @FXML
    private void handleAjouterAvis() {
        try {
            // Récupérer les valeurs des champs
            int note = Integer.parseInt(notefx.getText());
            int user = Integer.parseInt(userid.getText());
            String message = mess.getText();
            String coursNom = coursCombo.getValue();

            // Vérifier si tous les champs sont remplis
            if (note == 0 || message.isEmpty() || coursNom == null) {
                afficherAlerte("Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            // Récupérer l'identifiant du cours à partir de son nom
            int idCours = avisServices.getIdCoursByNom(coursNom);

            // Créer un nouvel avis
            Avis nouvelAvis = new Avis(note, message, user, idCours);

            // Ajouter l'avis à la base de données
            avisServices.ajouter(nouvelAvis);

            // Afficher une confirmation
            afficherAlerte("Confirmation", "Avis ajouté avec succès.");

            // Effacer les champs après l'ajout
            notefx.clear();
            mess.clear();
            coursCombo.getSelectionModel().clearSelection();
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Veuillez saisir une note valide (entier).");
        } catch (SQLException e) {
            afficherAlerte("Erreur", "Une erreur est survenue lors de l'ajout de l'avis : " + e.getMessage());
        }
    }

    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    List<Avis> mypub;
    private Pane createPublicationPane(Avis publication, int index) {
        Pane pane = new Pane();
        pane.setPrefSize(271, 75);
        pane.setLayoutX(-1);
        pane.setLayoutY(7 + index * (75));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");

        Text noteText = new Text("Note: " + publication.getNote());
        noteText.setLayoutX(14);
        noteText.setLayoutY(28);
        noteText.setFont(Font.font("System Bold", 12));
        noteText.setFill(Color.web("#494949"));

        Text dateText = new Text("Date: " + publication.getDate().toString());
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Avis/ModifyAvis.fxml"));
                    Parent root = loader.load();
                    ModifyAvisController modifyController = loader.getController();
                    modifyController.setPubId(mypub.get(index).getId_avis());
                    System.out.println(mypub.get(index).getId_avis());
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
                    pubs.supprimer(mypub.get(index).getId_avis());
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

        pane.getChildren().addAll(noteText, dateText, modifyButton, deleteButton);

        return pane;
    }


//    private Pane createPublicationPane(Avis publication, int index) {
//        Pane pane = new Pane();
//        pane.setPrefSize(271, 75);
//        pane.setLayoutX(-1);
//        pane.setLayoutY(7 + index * (75));
//        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");
//
//        Text titreText = new Text(Integer.parseInt(publication.getNote());
//        titreText.setLayoutX(14);
//        titreText.setLayoutY(28);
//        titreText.setFont(new Font("System Bold", 12));
//        titreText.setFill(Color.web("#494949"));
//
//        Text dateText = new Text(publication.getDate().toString());
//        dateText.setLayoutX(14);
//        dateText.setLayoutY(54);
//        dateText.setFill(Color.web("#a5a5a5"));
//
//
//        Button modifyButton = new Button();
//        modifyButton.setLayoutX(202);
//        modifyButton.setLayoutY(37);
//        modifyButton.setPrefSize(28, 25);
//        modifyButton.setStyle("-fx-background-color: #FFFFFF;");
//        FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
//        modifyIcon.setFill(Color.web("#2447f9"));
//        modifyIcon.setSize("15");
//        modifyButton.setGraphic(modifyIcon);
//        modifyButton.setCursor(Cursor.HAND);
//        modifyButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
//            @Override
//            public void handle(javafx.event.ActionEvent event) {
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamtions/ModifierRec.fxml"));
//                    Parent root = loader.load();
//                    ModifierRecController modifyController = loader.getController();
//                    modifyController.setPubId(mypub.get(index).getId_reclamation());
//                    System.out.println(mypub.get(index).getId_reclamation());
//                    forumBtn.getScene().setRoot(root);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        Button deleteButton = new Button();
//        deleteButton.setLayoutX(228);
//        deleteButton.setLayoutY(37);
//        deleteButton.setPrefSize(28, 25);
//        deleteButton.setStyle("-fx-background-color: #FFFFFF;");
//        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
//        deleteIcon.setFill(Color.web("#2447f9"));
//        deleteIcon.setSize("15");
//        deleteButton.setGraphic(deleteIcon);
//        deleteButton.setCursor(Cursor.HAND);
//        deleteButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    pubs.supprimer(mypub.get(index).getId_reclamation());
//                    System.out.println("deleted!");
//                    listepubid.getChildren().remove(pane);
//                    mypub.remove(index);
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Succes!");
//                    alert.setContentText("Publication supprimé!");
//                    alert.showAndWait();
//                } catch (SQLException e) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("ERROR!");
//                    alert.setContentText(e.getMessage());
//                    alert.showAndWait();
//                }
//            }
//        });
//
//        pane.getChildren().addAll(titreText, dateText, modifyButton, deleteButton);
//
//
//        return pane;
//    }

    public void searchPubByTitle1(ActionEvent actionEvent) {
        String searchText = searchField1.getText();
        int userID = 2;
        try {

            if (searchText.isEmpty()) {
                mypub = (List<Avis>) pubs.getAvisById(userID);
            } else {
                mypub = pubs.searchavisbyNoteType(searchText, userID);
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

    public void handleForum(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Avis/getAllAvis.fxml"));
            Parent root = loader.load();
            forumBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//package controllers.avis;
//
//import entities.Avis;
//import entities.Reclamations;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import services.AvisServices;
//import services.ReclamationsServices;
//import javafx.scene.control.Button;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class AjouterAvisController {
//
//
//
//    @FXML
//    private TableColumn<Avis, String> messcolun;
//
//    @FXML
//    private TextField mess, userid;
//
//
//    @FXML
//    private ComboBox<Integer> coursCombo;
//
//
//    @FXML
//    private TableColumn<Avis, Integer> notecolun;
//
//    @FXML
//    private TextField notefx;
//
//    @FXML
//    private TableView<Avis> tablefx;
//    @FXML
//    private Button back,forumBtn;
//    @FXML
//    void back(ActionEvent event) throws IOException {
//        try{
//        FXMLLoader loader=new FXMLLoader(getClass().getResource("/main.fxml"));
//        Parent root=loader.load();
//        back.getScene().setRoot(root);
//    } catch (IOException e) {
//        throw new RuntimeException(e);
//    }}
//
//    private AvisServices avisServices = new AvisServices();
//
//    @FXML
//    void ajouter(ActionEvent event) {
//        try {
//            int idCours = coursCombo.getValue();
//
//            String nomCours = avisServices.getNomCoursById(idCours); // Récupérer le nom du cours
//
//            Avis nouvelleAvis = new Avis(Integer.parseInt(notefx.getText()), mess.getText(), Integer.parseInt(userid.getText()),idCours, nomCours);
//
//            avisServices.ajouter(nouvelleAvis);
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Information");
//            alert.setContentText("Avis ajouté avec succès");
//            alert.showAndWait();
//
//            loadavis();
//            tablefx.scrollTo(tablefx.getItems().size() - 1);
//
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//        }
//    }
//
//
//    @FXML
//    void initialize() {
//        try {
//            List<Avis> avis = avisServices.recuperer();
//            ObservableList<Avis> observableList = FXCollections.observableList(avis);
//            tablefx.setItems(observableList);
//            messcolun.setCellValueFactory(new PropertyValueFactory<>("message"));
//            notecolun.setCellValueFactory(new PropertyValueFactory<>("note"));
//
//            List<Integer> userIds = avisServices.recupererIdsUtilisateurs();
//            List<String> nomsCours = avisServices.recupererNomsCours();
//            List<List<String>> listeDeListes = new ArrayList<>();
//
//
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Erreur lors de la récupération des données : " + e.getMessage());
//            alert.showAndWait();
//        }
//    }
//
//
//
//    private void loadavis() {
//        try {
//            tablefx.getItems().clear();
//            tablefx.getItems().addAll(avisServices.recuperer());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void handleForum(ActionEvent actionEvent) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Avis/getAllAvis.fxml"));
//            Parent root = loader.load();
//            forumBtn.getScene().setRoot(root);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
