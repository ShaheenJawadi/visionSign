package controllers.studentForum;

import entities.Publications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;

public class ModifyPublicationController extends BaseForumController {


    @FXML
    private TextField titreId, questionId;

    @FXML
    private Button modPubBtn;
    private int pubId;

    public void setPubId(int pubId) {
        this.pubId = pubId;
        loadPublicationDetails();
    }

    public void loadPublicationDetails() {
        try {
            Publications getPub = pubs.getByIdPublicationOrCommentaire(pubId);
            if (getPub != null) {
                titreId.setText(getPub.getTitre());
                questionId.setText(getPub.getContenu());
            } else {
                System.out.println("No publication found with ID: " + pubId);
            }
        } catch (SQLException e) {
            System.err.println("SQL error when trying to load publication details: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error when trying to load publication details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        String titreText = titreId.getText();
        String questionText = questionId.getText();

        try {
            Publications originalPub = pubs.getByIdPublicationOrCommentaire(pubId);
            if (titreText != null && !titreText.isEmpty() && questionText != null && !questionText.isEmpty()) {
                if (!titreText.equals(originalPub.getTitre()) || !questionText.equals(originalPub.getContenu())) {
                    pubs.updatePublicationOrCommentaire(new Publications(pubId, titreText, questionText, new Date(), 6));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès!");
                    alert.setContentText("Publication modifiée!");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));
                    Parent root = loader.load();
                    forumBtn.getScene().setRoot(root);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur!");
                    alert.setContentText("Aucune modification détectée!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur!");
                alert.setContentText("Impossible de modifier une publication avec des champs vides!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void initialize() {
        try {
            mypub = pubs.getPublicationsByUserId(6);
            mypub.sort(Comparator.comparing(Publications::getDate_creation).reversed());

            if (mypub.isEmpty()) {
                Text emptyText = new Text("Vous n'avez pas encore publié!");
                emptyText.setFont(new Font("System", 15));
                emptyText.setFill(Color.GRAY);
                emptyText.setLayoutX(19);
                emptyText.setLayoutY(172);
                listepubid.getChildren().add(emptyText);
            } else {
                for (int i = 0; i < mypub.size(); i++) {
                    Pane pane = createPublicationPane(mypub.get(i), i, false);
                    listepubid.getChildren().add(pane);
                }
            }
            listepubid.setPrefHeight(mypub.isEmpty() ? 100 : mypub.size() * 85);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
