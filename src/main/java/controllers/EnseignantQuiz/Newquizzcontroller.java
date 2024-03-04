package controllers.EnseignantQuiz;

import State.TeacherNavigations;
import entities.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.quiz.QuizService;

import java.io.IOException;
import java.sql.SQLException;

public class Newquizzcontroller {
    @FXML
    public TextField name;
    @FXML
    public TextField hours;
    @FXML
    public TextField minutes;
    @FXML
    public TextField seconds;
    @FXML
    public Button nextbtn;
    public Text errorMessage;

    public  int coursId ;


    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    @FXML
    public VBox rootId ;

    public VBox getVBoxRoot() {
        return rootId;
    }

    private final QuizService quizService = new QuizService();

    public void handleButtonClick(ActionEvent actionEvent) {
        String quizName = name.getText();
        int quizHours = Integer.parseInt(hours.getText());
        int quizMinutes = Integer.parseInt(minutes.getText());
        int quizSeconds = Integer.parseInt(seconds.getText());
        String duree=quizHours + ":" + quizMinutes + ":" + quizSeconds;

        if (quizName == null || quizName.isEmpty()) {
            errorMessage.setText("Nom du quiz est obligatoire");
        }else if (!quizName.matches("[A-Za-z].*")) {
            errorMessage.setText("Le nom du quiz doit commencer par une lettre.");

        } else if (quizHours < 0 || quizHours > 23 || quizMinutes < 0 || quizMinutes > 59 || quizSeconds < 0 || quizSeconds > 59) {
            errorMessage.setText("Les heures doivent être comprises entre 0 et 23, les minutes et les secondes entre 0 et 59");
        }else if (quizHours == 0 && quizMinutes == 0 && quizSeconds == 0) {
            errorMessage.setText("Hours, minutes, and seconds ne doivent pas etre 00");
        }
        else {
            errorMessage.setText("");
            try {
                if (quizService.isQuizNameUnique(quizName)) {
                    Quiz quiz = new Quiz(quizName, duree, coursId, 3); // TODO USER Id à changer apres pour etre dynamique integration
                    quizService.ajouterGestionQuiz(quiz);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Quizz ajouté avec succès");
                    alert.showAndWait();

                    TeacherNavigations.getInstance().openQuestionFromQuizz(coursId , quiz.getId());


                }else {
                    errorMessage.setText("Un quiz avec ce nom existe déjà.");
                }

            } catch ( SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }
    }

}
