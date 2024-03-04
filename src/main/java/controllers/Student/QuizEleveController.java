package controllers.Student;

import com.itextpdf.text.*;
import entities.Notes;
import entities.Questions;
import entities.Quiz;
import entities.Suggestion;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import services.quiz.NotesService;
import services.quiz.QuizService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class QuizEleveController {

    @FXML
    private Label hoursLabel;

    @FXML
    private Label minutesLabel;

    @FXML
    private Label secondsLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private Label  questionNumber;

    @FXML
    private VBox rootId ;


    public  VBox getVBoxRoot(){
        return  this.rootId;
    }


    private int remainingSeconds;
    private QuizService quizService= new QuizService();

    @FXML
    private RadioButton suggestion1;

    @FXML
    private RadioButton suggestion2;

    @FXML
    private RadioButton suggestion3;
    @FXML
    private Button nextBtn;


    @FXML
    private RadioButton suggestion4;

    @FXML
    private RadioButton selectedRadioButton;
    @FXML
    ImageView imageQuestion;

    private boolean quizCompleted = false;

    private int iterator = 0, note = 0;
    private float  noteSur20=0;
    private int quizId=1; // à changer apres pour etre dynamique integration
    private List<Questions> questionsList;
    private List<Suggestion> suggestionList;

    private List<String> answers = new ArrayList<>();

    private  int coursId ;


    public void setCoursId(int coursId ){
        this.coursId = coursId;
        initQuizz();
    }


    public  void  initQuizz(){
        suggestion1.setOnAction(event -> handleRadioButtonSelection(suggestion1));
        suggestion2.setOnAction(event -> handleRadioButtonSelection(suggestion2));
        suggestion3.setOnAction(event -> handleRadioButtonSelection(suggestion3));
        suggestion4.setOnAction(event -> handleRadioButtonSelection(suggestion4));
        try {
            Quiz quiz = quizService.getQuizByCoursId(coursId);
            tempsRestant(quiz.getDuree());
            questionsList = quiz.getQuizQuestions();
            numeroQuestion(iterator, questionsList.size());
            questionCourant(questionsList.get(iterator));
            quizId = quiz.getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void initialize() throws FileNotFoundException, DocumentException {

    }

    private void handleRadioButtonSelection(RadioButton radioButton) {
        if (selectedRadioButton != null) {
            selectedRadioButton.setSelected(false);
        }
        radioButton.setSelected(true);
        selectedRadioButton = radioButton;
    }

    private void changeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Student/NoteQuiz.fxml"));
            Parent root = loader.load();

            NoteQuizController noteQuizController = loader.getController();
            noteQuizController.setNoteQuiz(noteSur20);
            noteQuizController.setNombreQuestions(questionsList.size());
            noteQuizController.setDownload(questionsList, suggestionList, answers);

            questionLabel.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors du changement de scène");
            alert.setContentText("Une erreur est survenue lors du changement de scène : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void tempsRestant(String duree) {
        String[] durationParts = duree.split(":");
        int hours = Integer.parseInt(durationParts[0]);
        int minutes = Integer.parseInt(durationParts[1]);
        int seconds = Integer.parseInt(durationParts[2]);
        remainingSeconds = hours * 3600 + minutes * 60 + seconds;

        final boolean[] noteAdded = {false}; // Variable pour contrôler l'ajout de la note

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (remainingSeconds <= 0) {
                        if (!quizCompleted) {
                            if (!noteAdded[0]) { // Vérifier si la note n'a pas déjà été ajoutée
                                calculateNote();
                                NotesService notesService = new NotesService();
                                try {
                                    notesService.ajouterNote(new Notes(noteSur20, 3, quizId));
                                    noteAdded[0] = true; // Marquer la note comme ajoutée
                                    if (questionLabel.getScene() != null) {
                                        changeScene();
                                    } else {
                                        questionLabel.sceneProperty().addListener((observable, oldValue, newValue) -> {
                                            if (newValue != null) {
                                                changeScene();
                                            }
                                        });
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return;
                    }
                    remainingSeconds--;
                    int hoursRemaining = remainingSeconds / 3600;
                    int minutesRemaining = (remainingSeconds % 3600) / 60;
                    int secondsRemaining = remainingSeconds % 60;

                    hoursLabel.setText(String.format("%02d", hoursRemaining));
                    minutesLabel.setText(String.format("%02d", minutesRemaining));
                    secondsLabel.setText(String.format("%02d", secondsRemaining));
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }



    @FXML
    public void numeroQuestion(int num, int size) {
        String n = String.valueOf(num + 1);
        questionNumber.setText(n + " / " + size);
    }

    @FXML
    public void questionCourant(Questions question) {
        suggestionList = question.getSuggestionsQuestion();

        String questionText = question.getQuestion();
        questionLabel.setText(questionText);

        if (question.getImage() != null && !question.getImage().isEmpty()) {
            Image image = new Image(question.getImage());
            imageQuestion.setImage(image);
        } else {
            imageQuestion.setImage(null);
        }

        Collections.shuffle(suggestionList);
        suggestion1.setText(suggestionList.get(0).getSuggestion());
        suggestion2.setText(suggestionList.get(1).getSuggestion());
        suggestion3.setText(suggestionList.get(2).getSuggestion());
        suggestion4.setText(suggestionList.get(3).getSuggestion());
    }


    public void calculateNote() {
        int score = 0;
        String selectedAnswer = null;
        String correctAnswer = null;
        Integer moved = 0;
        for (int i = 0; i < suggestionList.size(); i++) {
            Suggestion suggestion = suggestionList.get(i);
            correctAnswer = suggestion.getSuggestion();
            boolean isCorrect = suggestion.isStatus();
            boolean isSelected = false;

            if (i == 0) {
                isSelected = suggestion1.isSelected();
                if(isSelected){
                    selectedAnswer = suggestion1.getText();
                }
            } else if (i == 1) {
                isSelected = suggestion2.isSelected();
                if(isSelected){
                    selectedAnswer = suggestion2.getText();
                }
            } else if (i == 2) {
                isSelected = suggestion3.isSelected();
                if(isSelected){
                    selectedAnswer = suggestion3.getText();
                }
            } else if (i == 3) {
                isSelected = suggestion4.isSelected();
                if(isSelected){
                    selectedAnswer = suggestion4.getText();
                }
            }

            if (correctAnswer.equals(selectedAnswer) && isCorrect && isSelected) {
                score++;
                answers.add("Votre réponse " + selectedAnswer + " est vraie");

                moved=1;
            }
            if( i==3 ){
                if(moved==0 && selectedAnswer!=null){
                    answers.add("Votre réponse " + selectedAnswer + " est fausse");
                }else if (moved==0 && selectedAnswer==null){
                    answers.add("Vous n'avez pas repondu a cette question");
                }
                moved=0;
            }
        }

        note += score;
        noteSur20 = (float) note / questionsList.size() * 20;
        noteSur20 = (float) (Math.round(noteSur20 * 100.0) / 100.0);
        //noteSur20 = (float) (Math.round(noteSur20 * 10) / 10.0);

    }

    public void nextFn(ActionEvent actionEvent) {
        if (!suggestion1.isSelected() && !suggestion2.isSelected() && !suggestion3.isSelected() && !suggestion4.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réponse avant de passer à la question suivante.");
            alert.showAndWait();
            return;
        }

        calculateNote();
        iterator += 1;

        suggestion1.setSelected(false);
        suggestion2.setSelected(false);
        suggestion3.setSelected(false);
        suggestion4.setSelected(false);

        if (iterator == questionsList.size() - 1) {
            nextBtn.setText("Finish");
        }
        if (iterator < questionsList.size()) {
            numeroQuestion(iterator, questionsList.size());
            questionCourant(questionsList.get(iterator));
        } else {
            NotesService notesService = new NotesService();
            try {
                notesService.ajouterNote(new Notes(noteSur20, 3, quizId)); // à changer apres pour etre dynamique integration
                quizCompleted=true;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Student/NoteQuiz.fxml"));
                Parent root = loader.load();

                NoteQuizController noteQuizController = loader.getController();
                noteQuizController.setNoteQuiz(noteSur20);
                noteQuizController.setNombreQuestions(questionsList.size());
                noteQuizController.setDownload(questionsList,suggestionList,answers);


                questionLabel.getScene().setRoot(root);

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}





