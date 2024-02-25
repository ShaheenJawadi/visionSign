package controllers.studentForum;

import entities.Publications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AddPublicationController extends BaseForumController {

    @FXML
    private TextField titreId;

    @FXML
    private TextField questionId;


    @FXML
    public void handlePublish(ActionEvent event) {
        String titreText = titreId.getText();
        String questionText = questionId.getText();

        try {
            if (titreText != null && !titreText.isEmpty() && questionText != null && !questionText.isEmpty()) {
                if (!pubs.publicationExists(titreText, questionText,6)) {
                    pubs.addPublicationOrCommentaire(new Publications(titreText, questionText, new Date(), 6));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès!");
                    alert.setContentText("Publication ajoutée!");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));
                    Parent root = loader.load();
                    forumBtn.getScene().setRoot(root);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur!");
                    alert.setContentText("Une publication avec le même titre et contenu existe déjà.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur!");
                alert.setContentText("Impossible d'ajouter une publication vide.");
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

}
