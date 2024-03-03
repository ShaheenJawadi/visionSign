package controllers.avis;


import entities.Avis;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.Reclamations.AvisServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AvisCoursController {


    @FXML
    private Text AvgAvis ;



    @FXML
    private VBox ListAvisHolder ;





    @FXML
    private  Text totalAvis ;

    @FXML
    private Text nbrAvis1 ;
    @FXML
    private Text nbrAvis2 ;
    @FXML
    private Text nbrAvis3 ;
    @FXML
    private Text nbrAvis4 ;
    @FXML
    private Text nbrAvis5 ;



    @FXML
    private TextArea avisContent;



    @FXML
    private VBox rootId ;


    private  int CoursId ;



    public  VBox getRoot(){
        return  this.rootId;
    }
    public  void  setCoursId(int coursId){
        this.CoursId= coursId ;

    }

    public void renderLessonList() {
        ListAvisHolder.getChildren().clear();
        try {
            AvisServices avisServices = new AvisServices(); // Créer une instance de AvisServices
            List<Avis> avisList = avisServices.recuperer(); // Récupérer la liste d'avis à partir de la base de données

            int totalNotes = 0;
            int totalAvisCount = avisList.size(); // Nombre total d'avis

            // Initialisation des compteurs pour chaque note
            int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
            int sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0, sum5 = 0;

            for (Avis avis : avisList) {
                // Ajouter la note à la somme totale
                totalNotes += avis.getNote();

                // Ajouter la note à la somme appropriée et incrémenter le compteur approprié
                switch (avis.getNote()) {
                    case 1:
                        sum1 += 1;
                        count1 += avis.getNote();
                        break;
                    case 2:
                        sum2 += 2;
                        count2 += avis.getNote();
                        break;
                    case 3:
                        sum3 += 3;
                        count3 += avis.getNote();
                        break;
                    case 4:
                        sum4 += 4;
                        count4 += avis.getNote();
                        break;
                    case 5:
                        sum5 += 5;
                        count5 += avis.getNote();
                        break;
                    default:
                        break;
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Avis/SingleAvis.fxml"));
                loader.load();

                controllers.avis.SingleAvisController controller = loader.getController();
                controller.renderItem(); // Il est possible que vous deviez passer l'objet "avis" au contrôleur ici, selon vos besoins

                ListAvisHolder.getChildren().add(controller.getRoot());
            }

            // Calcul des moyennes
            double moyenne1 = count1 > 0 ? sum1 / (double) count1 : 0;
            double moyenne2 = count2 > 0 ? sum2 / (double) count2 : 0;
            double moyenne3 = count3 > 0 ? sum3 / (double) count3 : 0;
            double moyenne4 = count4 > 0 ? sum4 / (double) count4 : 0;
            double moyenne5 = count5 > 0 ? sum5 / (double) count5 : 0;
            double moyenneTotale = totalNotes / (double) totalAvisCount;

            // Affichage des moyennes et du nombre total d'avis
            nbrAvis1.setText(String.valueOf(moyenne1));
            nbrAvis2.setText(String.valueOf(moyenne2));
            nbrAvis3.setText(String.valueOf(moyenne3));
            nbrAvis4.setText(String.valueOf(moyenne4));
            nbrAvis5.setText(String.valueOf(moyenne5));
            AvgAvis.setText(String.format("%.2f", moyenneTotale)); // Formatage à deux chiffres après la virgule pour la moyenne totale
            totalAvis.setText(String.valueOf(totalAvisCount));

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }






    @FXML
    void postAvis(ActionEvent event) {
        String message = avisContent.getText();
        Avis nouvelAvis = new Avis(2, message, 16, CoursId);
        afficherAlerte("Confirmation", "Avis ajouté avec succès.");

    }



    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();


    }
}
