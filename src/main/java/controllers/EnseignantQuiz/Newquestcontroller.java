package controllers.EnseignantQuiz;

import entities.Questions;
import entities.Suggestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import services.quiz.QuestionsService;
import services.quiz.SuggestionService;

import javafx.scene.image.ImageView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;

import java.util.Map;

public class Newquestcontroller {
    @FXML
    private TextField question;
    @FXML
    private TextField reponse;
    @FXML
    private TextField suggestion1;
    @FXML
    private TextField suggestion2;
    @FXML
    private TextField suggestion3;
    @FXML
    public Button finishBtn;
    @FXML
    public Button saveAndAdd;
    @FXML
    public Text errorMessage;
    private  QuestionsService questionService = new QuestionsService();
    private  SuggestionService suggestionService = new SuggestionService();

    public int quizId;

    private String selectedImage;

    public void setQuizId(int id) {
        this.quizId = id;
    }
    private List<String> suggestionsUniques = new ArrayList<>();

    @FXML
    private ImageView imageQuestion;

    private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dcgmqrlth",
            "api_key", "212948246846792",
            "api_secret", "de15yReatA8XLLdJoLhA4M8rvRw",
            "secure", true));

    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                selectedImage = (String) uploadResult.get("url");
                Image image = new Image(selectedImage);
                imageQuestion.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    void handleFinishClick(ActionEvent event) {

        suggestionsUniques.clear();


        if (question.getText() == null || question.getText().isEmpty()) {
            errorMessage.setText("Question est obligatoire");
        }
        else if (reponse.getText() == null || reponse.getText().isEmpty()) {
            errorMessage.setText("Reponse est obligatoire");
        }
        else if (suggestion1.getText() == null || suggestion1.getText().isEmpty() ||
                suggestion2.getText() == null || suggestion2.getText().isEmpty() ||
                suggestion3.getText() == null || suggestion3.getText().isEmpty()) {
            errorMessage.setText("Tous les suggestions sont obligatoires");
        }else if (!verifierEtAjouterSuggestionUnique(reponse.getText())) ;
        else if (!verifierEtAjouterSuggestionUnique(suggestion1.getText())) ;
        else if (!verifierEtAjouterSuggestionUnique(suggestion2.getText())) ;
        else if (!verifierEtAjouterSuggestionUnique(suggestion3.getText())) ;
        else {
            errorMessage.setText("");
            try {
                if (questionService.isQuestionUniqueInQuiz(question.getText(), quizId)) {
                    Questions q = new Questions(question.getText(), quizId, 1,selectedImage); // à changer apres pour etre dynamique integration
                    questionService.ajouterGestionQuiz(q);
                    suggestionService.ajouterGestionQuiz(new Suggestion(reponse.getText(), true, q.getId()));
                    suggestionService.ajouterGestionQuiz(new Suggestion(suggestion1.getText(), false, q.getId()));
                    suggestionService.ajouterGestionQuiz(new Suggestion(suggestion2.getText(), false, q.getId()));
                    suggestionService.ajouterGestionQuiz(new Suggestion(suggestion3.getText(), false, q.getId()));


                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Question ajoutée avec succès");
                    alert.showAndWait();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/quiz/DisplayQuiz.fxml"));
                    Parent root = loader.load();

                    DisplayQuizController displayQuizController = loader.getController();
                    displayQuizController.setDisplayQuizId(quizId);

                    question.getScene().setRoot(root);
                }else {
                    errorMessage.setText("Cette question existe déjà dans le quiz actuel.");
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleSaveAndAddClick(ActionEvent event) {
        suggestionsUniques.clear();

        if (question.getText() == null || question.getText().isEmpty()) {
            errorMessage.setText("champ question obligatoire");
        } else if (reponse.getText() == null || reponse.getText().isEmpty()) {
            errorMessage.setText("champ reponse obligatoire");
        } else if (suggestion1.getText() == null || suggestion1.getText().isEmpty() ||
                suggestion2.getText() == null || suggestion2.getText().isEmpty() ||
                suggestion3.getText() == null || suggestion3.getText().isEmpty()) {
            errorMessage.setText("tous les suggestions sont obligatoires");
        }else if (!verifierEtAjouterSuggestionUnique(reponse.getText())) ;
        else if (!verifierEtAjouterSuggestionUnique(suggestion1.getText())) ;
        else if (!verifierEtAjouterSuggestionUnique(suggestion2.getText())) ;
        else if (!verifierEtAjouterSuggestionUnique(suggestion3.getText())) ;
        else {
            errorMessage.setText("");
            try {
                if (questionService.isQuestionUniqueInQuiz(question.getText(), quizId)) {
                    Questions q = new Questions(question.getText(), quizId, 1,selectedImage); // à changer apres pour etre dynamique integration
                    questionService.ajouterGestionQuiz(q);
                    suggestionService.ajouterGestionQuiz(new Suggestion(reponse.getText(), true, q.getId()));
                    suggestionService.ajouterGestionQuiz(new Suggestion(suggestion1.getText(), false, q.getId()));
                    suggestionService.ajouterGestionQuiz(new Suggestion(suggestion2.getText(), false, q.getId()));
                    suggestionService.ajouterGestionQuiz(new Suggestion(suggestion3.getText(), false, q.getId()));

                    imageQuestion.setImage(null);

                    question.clear();
                    reponse.clear();
                    suggestion1.clear();
                    suggestion2.clear();
                    suggestion3.clear();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Question ajoutée avec succès");
                    alert.showAndWait();
                }else {
                    errorMessage.setText("Cette question existe déjà dans le quiz actuel.");
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private boolean verifierEtAjouterSuggestionUnique(String suggestion) {
        if (suggestionsUniques.contains(suggestion)) {
            errorMessage.setText("Cette suggestion est déjà insérée : " + suggestion);
            return false;
        } else {
            suggestionsUniques.add(suggestion);
            return true;
        }
    }

}
