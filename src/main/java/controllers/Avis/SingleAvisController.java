package controllers.Avis;

import entities.Avis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.Reclamations.AvisServices;

import java.sql.SQLException;

public class SingleAvisController {

    @FXML
    private VBox rootId;

    @FXML
    private Text texteid;

    @FXML
    private Text userNameid;

    // Variable globale pour stocker l'avis actuellement affiché
    private Avis currentAvis;
    private AvisCoursController avisCoursController;

    // Méthode pour définir la référence
    public void setAvisCoursController(AvisCoursController controller) {
        this.avisCoursController = controller;
    }
    public VBox getRoot() {
        return this.rootId;
    }

    public void renderItem(Avis avis) {
        // Assurez-vous que l'objet avis n'est pas nul
        if (avis != null) {
            // Stockez l'avis actuellement affiché
            currentAvis = avis;

            // Récupérez le nom d'utilisateur et le message de l'avis
            String userName = avis.getUserName();
            String message = avis.getMessage();

            // Définir le texte dans les éléments Text
            userNameid.setText(userName);
            texteid.setText(message);
        }
    }

    @FXML
    void suppAvis(ActionEvent event) {
        if (currentAvis != null) {
            try {
                AvisServices avisServices = new AvisServices();
                avisServices.supprimer(currentAvis.getId_avis());

                rootId.getChildren().clear();

                // Ajoutez cette ligne pour rafraîchir l'affichage dans AvisCoursController
                if (avisCoursController != null) {
                    avisCoursController.renderLessonList();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
