package controllers.Avis;

import entities.Avis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.Reclamations.AvisServices;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class AvisCoursController implements Initializable {
    // Vos autres attributs et méthodes ici




    @FXML
    private Text AvgAvis;

    @FXML
    private VBox ListAvisHolder;

    @FXML
    private Text totalAvis;

    @FXML
    private Text nbrAvis1;
    @FXML
    private Text nbrAvis2;
    @FXML
    private Text nbrAvis3;
    @FXML
    private Text nbrAvis4;
    @FXML
    private Text nbrAvis5;

    @FXML
    private TextArea avisContent;

    @FXML
    private VBox rootId;

    private int CoursId;
    private int userId;

    public VBox getRoot() {
        return this.rootId;
    }

    public void setCoursId(int coursId) {
        this.CoursId = coursId;
        renderLessonList(); // Appeler cette méthode ici assure que les avis sont chargés une fois l'ID défini

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderLessonList(); // Ceci va charger la liste des avis dès que la vue est prête
    }

    public void setUesrId(int UserId) {
        this.userId = UserId;
    }
    public void renderLessonList() {
        ListAvisHolder.getChildren().clear();
        try {
            AvisServices avisServices = new AvisServices();
            List<Avis> avisList = avisServices.recupererParCours(CoursId);

            int totalNotes = 0;
            int totalAvisCount = avisList.size();

            int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
            int sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0, sum5 = 0;

            for (Avis avis : avisList) {
                totalNotes += avis.getNote();

                switch (avis.getNote()) {
                    case 1:
                        sum1 += 1;
                        count1++;
                        break;
                    case 2:
                        sum2 += 2;
                        count2++;
                        break;
                    case 3:
                        sum3 += 3;
                        count3++;
                        break;
                    case 4:
                        sum4 += 4;
                        count4++;
                        break;
                    case 5:
                        sum5 += 5;
                        count5++;
                        break;
                    default:
                        break;
                }

                // Dans AvisCoursController, là où vous créez SingleAvisController :
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Avis/SingleAvis.fxml"));
                loader.load();

                SingleAvisController controller = loader.getController();
                controller.setAvisCoursController(this); // Ajoutez cette ligne
                controller.renderItem(avis);

                ListAvisHolder.getChildren().add(controller.getRoot());

            }

            double moyenne1 = count1 > 0 ? sum1 / (double) count1 : 0;
            double moyenne2 = count2 > 0 ? sum2 / (double) count2 : 0;
            double moyenne3 = count3 > 0 ? sum3 / (double) count3 : 0;
            double moyenne4 = count4 > 0 ? sum4 / (double) count4 : 0;
            double moyenne5 = count5 > 0 ? sum5 / (double) count5 : 0;
            double moyenneTotale = totalNotes / (double) totalAvisCount;

            nbrAvis1.setText(String.valueOf(count1));
            nbrAvis2.setText(String.valueOf(count2));
            nbrAvis3.setText(String.valueOf(count3));
            nbrAvis4.setText(String.valueOf(count4));
            nbrAvis5.setText(String.valueOf(count5));
            AvgAvis.setText(String.format("%.2f", moyenneTotale));
            totalAvis.setText(String.valueOf(totalAvisCount));

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void postAvis(ActionEvent event)  {
        String message = avisContent.getText();
        int userId = 3; // Utilisateur fictif pour l'exemple
        Avis nouvelAvis = new Avis(5, message, userId, CoursId);

        try {
            AvisServices avisServices = new AvisServices();
            avisServices.ajouter(nouvelAvis);

            afficherAlerte("Confirmation", "Avis ajouté avec succès.");

            renderLessonList();
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Une erreur s'est produite lors de l'ajout de l'avis.");
        }
    }

    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
