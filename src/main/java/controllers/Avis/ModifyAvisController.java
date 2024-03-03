package controllers.Avis;

import entities.Avis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.Reclamations.AvisServices;

import java.io.IOException;
import java.sql.SQLException;

public class ModifyAvisController {

    private int avisId;

    @FXML
    private TextField noteField;

    @FXML
    private TextField messageField;
    AvisServices avisServices=new AvisServices();

    @FXML
    private Button forumBtn;
    // Initialisation de la vue
    @FXML
    public void initialize() {
        try {
            // Récupérer les détails de l'avis à modifier
            Avis avis = avisServices.getAvisById(avisId);

            // Afficher les détails dans les champs de texte
            noteField.setText(String.valueOf(avis.getNote()));
            messageField.setText(avis.getMessage());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les détails de l'avis : " + e.getMessage());
        }
    }

    // Méthode appelée lors du clic sur le bouton de modification d'avis
    @FXML
    void handleUpdate() {
        try {
            // Récupérer la note et le message modifiés
            int note = Integer.parseInt(noteField.getText());
            String message = messageField.getText();

            Avis avis = new Avis();
            avis.setNote(note);
            avis.setMessage(message);

            // Mettre à jour l'avis avec les nouvelles données
            avisServices.modifier(avis);

            // Afficher une confirmation
            showAlert(Alert.AlertType.INFORMATION, "Avis modifié", "L'avis a été modifié avec succès.");
        } catch (NumberFormatException e) {
            // Gérer les erreurs de format de la note
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir une note valide (entier).");
        } catch (SQLException e) {
            // Gérer les erreurs de base de données
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la modification de l'avis : " + e.getMessage());
        }
    }

    // Méthode utilitaire pour afficher une boîte de dialogue
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode pour définir l'ID de l'avis à modifier
    public void setAvisId(int avisId) {
        this.avisId = avisId;
    }

    // Méthode pour injecter le service AvisServices
    public void setAvisServices(AvisServices avisServices) {
        this.avisServices = avisServices;
    }

    private int pubId;
    private void ChargerProductDetails() {
        try {
            AvisServices av = new AvisServices();

            System.out.println("Loading details for pubId: " + pubId);

            Avis p = av.getAvisById(pubId);

            if (p != null) {
                noteField.setText(String.valueOf(p.getNote()));
                messageField.setText(p.getMessage());
            } else {
                System.out.println("Aucun avis trouvé pour l'identifiant : " + pubId);
                // Affichez un message d'erreur à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Aucun avis trouvé");
                alert.setContentText("Aucun avis trouvé pour l'identifiant : " + pubId);
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des détails de l'avis : " + e.getMessage());
            // Affichez un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors du chargement des détails de l'avis");
            alert.setContentText("Une erreur s'est produite lors du chargement des détails de l'avis : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
        ChargerProductDetails();
    }
@FXML
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
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import entities.Avis;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Cursor;
//import javafx.scene.Parent;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import services.AvisServices;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.List;
//
//public class ModifyAvisController {
//        @FXML
//        public AnchorPane listepubid;
//
//        @FXML
//        private TextField searchField1, titreId, questionId,titreId1;
//        @FXML
//        private Button forumBtn, modPubBtn;
//        List<Avis> mypub;
//        AvisServices pubs = new AvisServices();
//        private int pubId;
//
//        public void setPubId(int pubId) {
//            this.pubId = pubId;
//            loadPublicationDetails();
//        }
//
//        private void loadPublicationDetails() {
//            System.out.println("Loading details for pubId: " + pubId);
//            try {
//                Avis getPub = pubs.getAvisById(pubId);
//                if (getPub != null) {
//                    titreId.setText(String.valueOf(getPub.getNote()));
//                    questionId.setText(getPub.getMessage());
//                    System.out.println("Loaded publication: " + getPub);
//                } else {
//                    System.out.println("No publication found with ID: " + pubId);
//                }
//            } catch (SQLException e) {
//                System.err.println("SQL error when trying to load publication details: " + e.getMessage());
//                e.printStackTrace();
//            } catch (Exception e) {
//                System.err.println("Unexpected error when trying to load publication details: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//        @FXML
//        private void handleUpdate(ActionEvent event) {
//            int titreText = Integer.parseInt(titreId.getText());
//            String questionText = questionId.getText();
//            String courid= String.valueOf(Integer.parseInt(titreId1.getText()));
//            try {
//                if (questionText != null && !questionText.isEmpty()) {
//                    System.out.println(pubId);
//                    pubs.modifier(new Avis(pubId,titreText, questionText,2,courid ));
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Succés!");
//                    alert.setContentText("Publication modifié!");
//                    alert.showAndWait();
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumGetAllPublications.fxml"));
//                    Parent root = loader.load();
//                    forumBtn.getScene().setRoot(root);
//
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("ERROR!");
//                    alert.setContentText("Impossible du modofier une publication!");
//                    alert.showAndWait();
//
//                }
//
//            } catch (SQLException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("ERROR!");
//                alert.setContentText(e.getMessage());
//                alert.showAndWait();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            try{
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumGetAllPublications.fxml"));
//                Parent root = loader.load();
//                AfficherAvisController controller = loader.getController();
//                controller.refreshPublications(); // Refresh the publications view
//                forumBtn.getScene().setRoot(root);}
//            catch(IOException e){
//                System.out.println(e.getMessage());
//            }
//
//        }
//
//        @FXML
//        private void handleForum(ActionEvent event) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumGetAllPublications.fxml"));
//                Parent root = loader.load();
//                forumBtn.getScene().setRoot(root);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        @FXML
//        void searchPubByTitle1(ActionEvent event) {
//            String searchText = searchField1.getText();
//            int userID = 2;
//            try {
//
//                if (searchText.isEmpty()) {
//                    mypub = (List<Avis>) pubs.getAvisById(userID);
//                } else {
//                    mypub = pubs.searchavisbyNoteType(searchText, userID);
//                }
//                listepubid.getChildren().clear();
//                for (int i = 0; i < mypub.size(); i++) {
//                    Pane pane = createPublicationPane(mypub.get(i), i);
//                    listepubid.getChildren().add(pane);
//                }
//                listepubid.setPrefHeight(mypub.size() * 85);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private Pane createPublicationPane(Avis publication, int index) {
//            Pane pane = new Pane();
//            pane.setPrefSize(271, 75);
//            pane.setLayoutX(-1);
//            pane.setLayoutY(7 + index * (75));
//            pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");
//
//            Text titreText = new Text(Integer.toString(publication.getNote()));
//            titreText.setLayoutX(14);
//            titreText.setLayoutY(28);
//            titreText.setFont(new Font("System Bold", 12));
//            titreText.setFill(Color.web("#494949"));
//
//            Text dateText = new Text(publication.getDate().toString());
//            dateText.setLayoutX(14);
//            dateText.setLayoutY(54);
//            dateText.setFill(Color.web("#a5a5a5"));
//
//
//            Button modifyButton = new Button();
//            modifyButton.setLayoutX(202);
//            modifyButton.setLayoutY(37);
//            modifyButton.setPrefSize(28, 25);
//            modifyButton.setStyle("-fx-background-color: #FFFFFF;");
//            FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
//            modifyIcon.setFill(Color.web("#2447f9"));
//            modifyIcon.setSize("15");
//            modifyButton.setGraphic(modifyIcon);
//            modifyButton.setCursor(Cursor.HAND);
//            modifyButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    try {
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyPublication.fxml"));
//                        Parent root = loader.load();
//                        ModifyAvisController modifyController = loader.getController();
//                        modifyController.setPubId(mypub.get(index).getId_avis());
//                        System.out.println(mypub.get(index).getId_avis());
//                        forumBtn.getScene().setRoot(root);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            Button deleteButton = new Button();
//            deleteButton.setLayoutX(228);
//            deleteButton.setLayoutY(37);
//            deleteButton.setPrefSize(28, 25);
//            deleteButton.setStyle("-fx-background-color: #FFFFFF;");
//            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
//            deleteIcon.setFill(Color.web("#2447f9"));
//            deleteIcon.setSize("15");
//            deleteButton.setGraphic(deleteIcon);
//            deleteButton.setCursor(Cursor.HAND);
//            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    try {
//                        pubs.supprimer(mypub.get(index).getId_avis());
//                        System.out.println("deleted!");
//                        listepubid.getChildren().remove(pane);
//                        mypub.remove(index);
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("Succes!");
//                        alert.setContentText("Publication supprimé!");
//                        alert.showAndWait();
//                    } catch (SQLException e) {
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setTitle("ERROR!");
//                        alert.setContentText(e.getMessage());
//                        alert.showAndWait();
//                    }
//                }
//            });
//
//            pane.getChildren().addAll(titreText, dateText, modifyButton, deleteButton);
//
//
//            return pane;
//        }
//
//        @FXML
//        public void initialize() {
//
//            try {
//                mypub = (List<Avis>) pubs.getAvisById(2);
//                if (mypub.isEmpty()) {
//                    Text emptyText = new Text("Vous n'avez pas encore publié!");
//                    emptyText.setFont(new Font("System", 15));
//                    emptyText.setFill(Color.GRAY);
//                    emptyText.setLayoutX(19);
//                    emptyText.setLayoutY(172);
//                    listepubid.getChildren().add(emptyText);
//                } else {
//                    for (int i = 0; i < mypub.size(); i++) {
//                        Pane pane = createPublicationPane(mypub.get(i), i);
//                        listepubid.getChildren().add(pane);
//                    }
//                }
//                listepubid.setPrefHeight(mypub.isEmpty() ? 100 : mypub.size() * 85);
//
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//
//            }
//
//        }
//
//
//    }
//
