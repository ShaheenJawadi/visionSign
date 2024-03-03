package controllers.Avis;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Avis;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Reclamations.AvisServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class gatAllAvisController  implements Initializable {

        @FXML
        public AnchorPane listepubid, allpubid;
        @FXML
        private Button addBtn, forumBtn;
        @FXML
        private TextField searchField;

        List<Avis> mypub, allPub;
        AvisServices pubs = new AvisServices();

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            refreshPublications(); // Effacez les anciennes publications des AnchorPanes
            try {
                // Récupérez la liste complète des avis
                allPub = pubs.recuperer();
                if (allPub.isEmpty()) {
                    Text emptyText = new Text("Aucune publication n'a été publiée.");
                    emptyText.setFont(new Font("System", 16));
                    emptyText.setFill(Color.GRAY);
                    emptyText.setLayoutX(10);
                    emptyText.setLayoutY(50);
                    allpubid.getChildren().add(emptyText);
                } else {
                    for (int i = 0; i < allPub.size(); i++) {
                        Pane pane = createPublicationPane(allPub.get(i), i, true);
                        allpubid.getChildren().add(pane);
                    }
                }
                allpubid.setPrefHeight(allPub.isEmpty() ? 100 : allPub.size() * 165);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    private Pane createPublicationPane(Avis publication, int index, boolean isAllPublications) {
        Pane pane = new Pane();
        pane.setPrefSize(isAllPublications ? 682 : 271, isAllPublications ? 158 : 75);
        pane.setLayoutX(isAllPublications ? 6 : -1);
        pane.setLayoutY(7 + index * (isAllPublications ? 165 : 75));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");

        Text titreText = new Text(Integer.toString(publication.getNote()));
        titreText.setLayoutX(14);
        titreText.setLayoutY(isAllPublications ? 24 : 28);
        titreText.setFont(new Font("System Bold", isAllPublications ? 16 : 12));
        titreText.setFill(Color.web("#494949"));

        Text dateText = new Text(publication.getDate().toString());
        dateText.setLayoutX(isAllPublications ? 500 : 14);
        dateText.setLayoutY(isAllPublications ? 63 : 54);
        dateText.setFill(Color.web("#a5a5a5"));

        if (isAllPublications) {
            Pane iconPane = new Pane();
            iconPane.setLayoutX(14);
            iconPane.setLayoutY(39);
            iconPane.setPrefSize(41, 38);
            iconPane.setStyle("-fx-background-color: #2447f9; -fx-border-radius: 1000; -fx-background-radius: 1000;");
            FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
            userIcon.setFill(Color.WHITE);
            userIcon.setLayoutX(12);
            userIcon.setLayoutY(27);
            userIcon.setSize("22");
            iconPane.getChildren().add(userIcon);

            Text userIdText = new Text(publication.getUserName());
            userIdText.setLayoutX(62);
            userIdText.setLayoutY(54);
            userIdText.setFont(new Font(14));

            Text roleText = new Text("Elève");
            roleText.setLayoutX(62);
            roleText.setLayoutY(71);
            roleText.setFill(Color.web("#a5a5a5"));
            roleText.setFont(new Font(11));

            Text contenuText = new Text(publication.getMessage());
            contenuText.setLayoutX(31);
            contenuText.setLayoutY(102);
            contenuText.setFill(Color.web("#7a757d"));

            pane.getChildren().addAll(titreText, iconPane, userIdText, roleText, contenuText, dateText);
        } else {
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/avis/ModifyAvis.fxml"));
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

            pane.getChildren().addAll(titreText, dateText, modifyButton, deleteButton);
        }

        return pane;
    }


    @FXML
        void navigateAddPub(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Avis/AjouterAvis.fxml"));
                Parent root = loader.load();
                addBtn.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        private void handleForum(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumGetAllPublications.fxml"));
                Parent root = loader.load();
                forumBtn.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void searchPubByTitle(ActionEvent event) {
            String searchText = searchField.getText();
            int userID = 2;
            try {

                if (searchText.isEmpty()) {
                    mypub = (List<Avis>) pubs.getAvisById(userID);
                } else {
                    mypub = pubs.searchavisbyNoteType(searchText, userID);
                }
                listepubid.getChildren().clear();
                for (int i = 0; i < mypub.size(); i++) {
                    Pane pane = createPublicationPane(mypub.get(i), i,true);
                    listepubid.getChildren().add(pane);
                }
                listepubid.setPrefHeight(mypub.size() * 85);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void refreshPublications() {
            listepubid.getChildren().clear();
            allpubid.getChildren().clear();

        }

    }


