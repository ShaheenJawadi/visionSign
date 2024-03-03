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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import services.quiz.QuestionsService;
import services.quiz.SuggestionService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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


    private byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buf)) != -1) {
                bos.write(buf, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }

    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(null);
        System.out.println("open");
        if (file != null) {
            System.out.println("not null");

            try {
                String clientId = "5c696d00e331543";
                URL url = new URL("https://api.imgur.com/3/image");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Client-ID " + clientId);
                connection.setDoOutput(true);
                byte[] imageData = readFileToByteArray(file);
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                String requestBody = "image=" + java.net.URLEncoder.encode(base64Image, "UTF-8");
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(requestBody.getBytes());
                    outputStream.flush();
                }

                int responseCode = connection.getResponseCode();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                int linkIndex = response.indexOf("\"link\":");
                int linkStartIndex = response.indexOf("\"", linkIndex + 7) + 1;
                int linkEndIndex = response.indexOf("\"", linkStartIndex);
                String link = response.substring(linkStartIndex, linkEndIndex);
                selectedImage=link;

                System.out.println("Image link: " + link);
                Image image = new Image(file.toURI().toString());
                imageQuestion.setImage(image);

            } catch (IOException e) {
                System.out.println(e);
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
                    Questions q = new Questions(question.getText(), quizId, 1,selectedImage); // à changer dynamique
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
                    Questions q = new Questions(question.getText(), quizId, 1,selectedImage); // userId à changer dynamique
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
