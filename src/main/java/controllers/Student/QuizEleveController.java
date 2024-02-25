package controllers.Student;

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
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import services.quiz.NotesService;
import services.quiz.QuestionsService;
import services.quiz.QuizService;
import services.quiz.SuggestionService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class QuizEleveController {

    @FXML
    private Pane leftPane;

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


    private int remainingSeconds;
    private QuizService quizService= new QuizService();;
    private QuestionsService questionsService=new QuestionsService();
    private SuggestionService suggestionService=new SuggestionService();
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


    static int iterator=0, note=0;
    static int quizId=4; // à changer apres
    static List<Questions> questionsList;
    static List<Suggestion> suggestionList;
    private Timeline timeline = null;
    @FXML
    void initialize() {
        timeline = new Timeline();

        suggestion1.setOnAction(event -> handleRadioButtonSelection(suggestion1));
        suggestion2.setOnAction(event -> handleRadioButtonSelection(suggestion2));
        suggestion3.setOnAction(event -> handleRadioButtonSelection(suggestion3));
        suggestion4.setOnAction(event -> handleRadioButtonSelection(suggestion4));

        try {
            Quiz quiz = quizService.getQuizById(4); // à changer dynamique fel integration quizId
            tempsRestant(quiz.getDuree());
            questionsList=quiz.getQuizQuestions();
            numeroQuestion(iterator,questionsList.size());
            questionCourant(questionsList.get(iterator));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void handleRadioButtonSelection(RadioButton radioButton) {

        if (selectedRadioButton != null) {
            selectedRadioButton.setSelected(false);
        }

        radioButton.setSelected(true);
        selectedRadioButton = radioButton;
    }
    @FXML
    public void tempsRestant(String duree){
        String[] durationParts = duree.split(":");
        int hours = Integer.parseInt(durationParts[0]);
        int minutes = Integer.parseInt(durationParts[1]);
        int seconds = Integer.parseInt(durationParts[2]);
        remainingSeconds = hours * 3600 + minutes * 60 + seconds;

        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (remainingSeconds <= 0) {
                        calculateNote();
                        NotesService notesService = new NotesService();
                        try {
                            notesService.ajouterNote(new Notes(note,1,quizId));

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Student/NoteQuiz.fxml"));
                            Parent root = loader.load();

                            NoteQuizController noteQuizController = loader.getController();
                            noteQuizController.setNoteQuiz(note);
                            noteQuizController.setNombreQuestions(questionsList.size());
                            hoursLabel.getScene().setRoot(root);

                            timeline.stop();
                        } catch (SQLException | IOException e) {
                            throw new RuntimeException(e);
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
    public void numeroQuestion(int num, int size){
        String n= String.valueOf(num+1);
        questionNumber.setText(n+" / "+size);
    }

    @FXML
    public void questionCourant(Questions question) {
        suggestionList = question.getSuggestionsQuestion();

        String questionText = question.getQuestion();
        questionLabel.setText(questionText);

        Collections.shuffle(suggestionList);
            suggestion1.setText(suggestionList.get(0).getSuggestion());
            suggestion2.setText(suggestionList.get(1).getSuggestion());
            suggestion3.setText(suggestionList.get(2).getSuggestion());
            suggestion4.setText(suggestionList.get(3).getSuggestion());

    }

    public void calculateNote() {

        int score = 0;
        for (int i = 0; i < suggestionList.size(); i++) {
            Suggestion suggestion = suggestionList.get(i);
            String correctAnswer = suggestion.getSuggestion();
            boolean isCorrect = suggestion.isStatus();
            String selectedAnswer = null;
            boolean isSelected = false;

            if (i == 0) {
                selectedAnswer = suggestion1.getText();
                isSelected = suggestion1.isSelected();
            } else if (i == 1) {
                selectedAnswer = suggestion2.getText();
                isSelected = suggestion2.isSelected();
            } else if (i == 2) {
                selectedAnswer = suggestion3.getText();
                isSelected = suggestion3.isSelected();
            } else if (i == 3) {
                selectedAnswer = suggestion4.getText();
                isSelected = suggestion4.isSelected();
            }

            if (correctAnswer.equals(selectedAnswer) && isCorrect && isSelected) {
                score++;
            }
        }

        note += score;

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
            iterator+=1;

        suggestion1.setSelected(false);
        suggestion2.setSelected(false);
        suggestion3.setSelected(false);
        suggestion4.setSelected(false);

        if(iterator==questionsList.size()-1){
            nextBtn.setText("Finish");
        }
        if(iterator<questionsList.size()){
            numeroQuestion(iterator,questionsList.size());
            questionCourant(questionsList.get(iterator));
        }else{
            NotesService notesService=new NotesService();
            try {
                notesService.ajouterNote(new Notes(note,1,quizId));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Student/NoteQuiz.fxml"));
                Parent root = loader.load();

                NoteQuizController noteQuizController=loader.getController();
                noteQuizController.setNoteQuiz(note);
                noteQuizController.setNombreQuestions(questionsList.size());

                questionLabel.getScene().setRoot(root);

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
