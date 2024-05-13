package controllers.EnseignantQuiz;

import State.MainNavigations;
import State.TeacherNavigations;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Questions;
import entities.Quiz;
import entities.Suggestion;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import services.quiz.QuestionsService;
import services.quiz.QuizService;
import services.quiz.SuggestionService;

import java.io.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;



public class DisplayQuizController {
    @FXML
    private TextField quizNameId;
    @FXML
    private TextField searchField;




    @FXML
    private TextField hours,minutes,seconds;
    private List<Quiz> myQuiz;
    private QuizService quizService= new QuizService();;
    private QuestionsService questionsService=new QuestionsService();

    @FXML
    public AnchorPane listeQuizId;
    @FXML
    public VBox listeQuestionsOfQuiz;


    public  int coursId ;

    private int usserId=State.UserSessionManager.getInstance().getCurrentUser().getId();
    @FXML
    public AnchorPane rootId ;

    public AnchorPane getRootId() {
        return rootId;
    }

    private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dcgmqrlth",
            "api_key", "212948246846792",
            "api_secret", "de15yReatA8XLLdJoLhA4M8rvRw",
            "secure", true));


    public  int quizId;
    public void setDisplayQuizId(int id){
        this.quizId=id;
        getQuizUserRight();
    }

    @FXML
    public void initialize() {
        getQuizUserLeft();

    }

    public void getQuizUserLeft(){
        try {
            myQuiz = quizService.getQuizByUserId(usserId);// à changer apres pour etre dynamique integration
            if (myQuiz.isEmpty()) {
                Text emptyText = new Text("Vous n'avez pas de quiz");
                emptyText.setFont(new Font("System", 15));
                emptyText.setFill(Color.GRAY);
                emptyText.setLayoutX(19);
                emptyText.setLayoutY(172);
                listeQuizId.getChildren().add(emptyText);
            } else {
                Collections.reverse(myQuiz);

                for (int i = 0; i < myQuiz.size(); i++) {
                    Pane pane = createQuizPane(myQuiz.get(i), i);
                    listeQuizId.getChildren().add(pane);
                }
            }
            listeQuizId.setPrefHeight(myQuiz.isEmpty() ? 100 : myQuiz.size() * 85);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
    public void getQuizUserRight(){
        try {
            Quiz quiz = quizService.getQuizById(quizId);
            quizNameId.setText(quiz.getNom());
            String duree = quiz.getDuree();

            String[] durationParts = duree.split(":");
            hours.setText(durationParts[0]);
            minutes.setText(durationParts[1]);
            seconds.setText(durationParts[2]);
            //added
            listeQuestionsOfQuiz.getChildren().clear(); // Clear previous children
            listeQuestionsOfQuiz.setSpacing(10);

            List<Questions> quizQuestions = questionsService.getAllQuizQuestionsByQuizId(quizId);
            for (int i = 0; i < quizQuestions.size(); i++) {
                Pane pane = createQuestionPane(quizQuestions.get(i),i);
                listeQuestionsOfQuiz.getChildren().add(pane);
                listeQuestionsOfQuiz.setSpacing(10);
                Separator separator = new Separator();
                separator.setPrefWidth(listeQuestionsOfQuiz.getWidth());
                listeQuestionsOfQuiz.getChildren().add(separator);
            }
            listeQuestionsOfQuiz.setPrefHeight(VBox.USE_COMPUTED_SIZE);//added

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Pane createQuestionPane(Questions question,int i){
        Pane pane = new Pane();
        pane.setPrefSize(668, 378);
        // pane.setPrefSize(800, 878);

        //pane.setPrefHeight(278);
        pane.setStyle("-fx-background-color: white;");
        // pane.setLayoutY(0+i * (90));


        Text questionLabel = new Text("Question:");
        questionLabel.setLayoutX(27);
        questionLabel.setLayoutY(32);
        questionLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        ImageView imageView;

        TextField questionTextField = new TextField(question.getQuestion());
        questionTextField.setLayoutX(125);
        questionTextField.setLayoutY(10);
        questionTextField.setPrefWidth(355);
        questionTextField.setPrefHeight(27);
        questionTextField.setPromptText("Question...");
        questionTextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");

        Text reponseLabel = new Text("Reponse:");
        reponseLabel.setLayoutX(27);
        reponseLabel.setLayoutY(78);
        reponseLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));

        String responseText = "";
        for (Suggestion suggestion : question.getSuggestionsQuestion()) {
            if (suggestion.isStatus()) {
                responseText = suggestion.getSuggestion();
                break;
            }
        }

        TextField reponseTextField = new TextField(responseText);
        reponseTextField.setLayoutX(125);
        reponseTextField.setLayoutY(56);
        reponseTextField.setPrefWidth(355);
        reponseTextField.setPrefHeight(27);
        reponseTextField.setPromptText("Reponse...");
        reponseTextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");

        Text suggestion1Label = new Text("Suggestion 1:");
        suggestion1Label.setLayoutX(27);
        suggestion1Label.setLayoutY(124);
        suggestion1Label.setFont(Font.font("System", FontWeight.NORMAL, 16));

        TextField suggestion1TextField = new TextField(question.getSuggestionsQuestion().size() > 1 ? question.getSuggestionsQuestion().get(1).getSuggestion() : "");
        suggestion1TextField.setLayoutX(125);
        suggestion1TextField.setLayoutY(102);
        suggestion1TextField.setPrefWidth(355);
        suggestion1TextField.setPrefHeight(27);
        suggestion1TextField.setPromptText("Suggestion 1...");
        suggestion1TextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");

        Text suggestion2Label = new Text("Suggestion 2:");
        suggestion2Label.setLayoutX(27);
        suggestion2Label.setLayoutY(170);
        suggestion2Label.setFont(Font.font("System", FontWeight.NORMAL, 16));

        TextField suggestion2TextField = new TextField(question.getSuggestionsQuestion().size() > 2 ? question.getSuggestionsQuestion().get(2).getSuggestion() : "");
        suggestion2TextField.setLayoutX(125);
        suggestion2TextField.setLayoutY(148);
        suggestion2TextField.setPrefWidth(355);
        suggestion2TextField.setPrefHeight(27);
        suggestion2TextField.setPromptText("Suggestion 2...");
        suggestion2TextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");

        Text suggestion3Label = new Text("Suggestion 3:");
        suggestion3Label.setLayoutX(27);
        suggestion3Label.setLayoutY(216);
        suggestion3Label.setFont(Font.font("System", FontWeight.NORMAL, 16));

        TextField suggestion3TextField = new TextField(question.getSuggestionsQuestion().size() > 3 ? question.getSuggestionsQuestion().get(3).getSuggestion() : "");
        suggestion3TextField.setLayoutX(125);
        suggestion3TextField.setLayoutY(194);
        suggestion3TextField.setPrefWidth(355);
        suggestion3TextField.setPrefHeight(27);
        suggestion3TextField.setPromptText("Suggestion 3...");
        suggestion3TextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");


        Button modifierImageButton = new Button();
        modifierImageButton.setLayoutX(550);
        modifierImageButton.setLayoutY(195);
        modifierImageButton.setPrefSize(40, 40);
        modifierImageButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        modifyIcon.setFill(Color.web("#00aeef"));
        modifyIcon.setSize("25");
        modifierImageButton.setGraphic(modifyIcon);
        modifierImageButton.setCursor(Cursor.HAND);


        Button supprimerImageButton = new Button();
        supprimerImageButton.setLayoutX(600);
        supprimerImageButton.setLayoutY(200);
        supprimerImageButton.setPrefSize(28, 25);
        supprimerImageButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        deleteIcon.setFill(Color.web("#00aeef"));
        deleteIcon.setSize("25");
        supprimerImageButton.setGraphic(deleteIcon);
        supprimerImageButton.setCursor(Cursor.HAND);




        if (question.getImage() != null) {
            imageView = new ImageView(question.getImage());
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);
            imageView.setLayoutX(500);
            imageView.setLayoutY(10);
        } else {
            imageView = new ImageView();
        }


        Button modifyButton = new Button("Modifier");
        modifyButton.setLayoutX(130);
        modifyButton.setLayoutY(240);
        modifyButton.setPrefSize(120, 40);
        modifyButton.setStyle("-fx-background-color: #00aeef; -fx-background-radius: 50; -fx-border-radius: 50; -fx-text-fill: white;");
        modifyButton.setFont(Font.font("System Bold", 13));
        modifyButton.setCursor(Cursor.HAND);
        modifyButton.setOnAction(event -> {
            QuestionsService qs=new QuestionsService();
            SuggestionService ss=new SuggestionService();
            String newQuestion = questionTextField.getText();
            String newResponse = reponseTextField.getText();
            String newSuggestion1 = suggestion1TextField.getText();
            String newSuggestion2 = suggestion2TextField.getText();
            String newSuggestion3 = suggestion3TextField.getText();

            List<Suggestion> suggestionList=question.getSuggestionsQuestion();

            try {
                qs.modifierGestionQuiz(new Questions(question.getId(),newQuestion,question.getQuizId(),question.getUserId(),question.getImage()));
                for (Suggestion suggestion : suggestionList) {
                    if (suggestion.isStatus()) {
                        ss.modifierGestionQuiz(new Suggestion(suggestion.getId(), newResponse, true, suggestion.getQuestionId()));
                    } else if (suggestion.getId() == suggestionList.get(1).getId()) {
                        ss.modifierGestionQuiz(new Suggestion(suggestion.getId(), newSuggestion1, false, suggestion.getQuestionId()));
                    } else if (suggestion.getId() == suggestionList.get(2).getId()) {
                        ss.modifierGestionQuiz(new Suggestion(suggestion.getId(), newSuggestion2, false, suggestion.getQuestionId()));
                    } else if (suggestion.getId() == suggestionList.get(3).getId()) {
                        ss.modifierGestionQuiz(new Suggestion(suggestion.getId(), newSuggestion3, false, suggestion.getQuestionId()));
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Question et sugegstions modifiées!");
                alert.showAndWait();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        });
        modifierImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                try {
                    Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                    String imageUrl = (String) uploadResult.get("url");
                    question.setImage(imageUrl);

                    Platform.runLater(() -> {
                        try {
                            Image image = new Image(imageUrl);
                            imageView.setImage(image);
                            imageView.setFitWidth(180);
                            imageView.setFitHeight(180);
                            imageView.setLayoutX(500);
                            imageView.setLayoutY(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // supp image
        supprimerImageButton.setOnAction(event -> {
            try {
                question.setImage(null);
                imageView.setImage(null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Image supprimée!");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setContentText("Une erreur s'est produite lors de la suppression de l'image.");
                alert.showAndWait();
                e.printStackTrace();
            }
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.setLayoutX(280);
        deleteButton.setLayoutY(240);
        deleteButton.setPrefSize(120, 40);
        deleteButton.setStyle("-fx-background-color: #00aeef; -fx-background-radius: 50; -fx-border-radius: 50; -fx-text-fill: white;");
        deleteButton.setFont(Font.font("System Bold", 13));
        // deleteButton.setCursor(Cursor.HAND);
        deleteButton.setOnAction(event -> {
            QuestionsService qs = new QuestionsService();
            SuggestionService ss = new SuggestionService();

            try {
                List<Suggestion> suggestionList = question.getSuggestionsQuestion();
                for (Suggestion suggestion : suggestionList) {
                    ss.supprimerGestionQuiz(suggestion.getId());
                }
                qs.supprimerGestionQuiz(question.getId());

                listeQuestionsOfQuiz.getChildren().remove(pane);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Question et suggestions supprimées!");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        });

        pane.getChildren().addAll(questionLabel, questionTextField, reponseLabel, reponseTextField,
                suggestion1Label, suggestion1TextField, suggestion2Label, suggestion2TextField,
                suggestion3Label, suggestion3TextField,modifierImageButton,supprimerImageButton,imageView,modifyButton , deleteButton);

         /*HBox icons= new HBox(modifierImageButton,supprimerImageButton);
         VBox imgHolder = new VBox(imageView , icons);

         VBox op = new VBox(modifyButton , deleteButton);
         op.setSpacing(20);

         HBox holder= new HBox(op,imgHolder);
         holder.setSpacing(20);

         pane.getChildren().add(holder);*/
        return pane;
    }
   /* public void getQuizUserRight(){
        try {
            Quiz quiz = quizService.getQuizById(quizId);
            quizNameId.setText(quiz.getNom());
            String duree = quiz.getDuree();

            String[] durationParts = duree.split(":");
            hours.setText(durationParts[0]);
            minutes.setText(durationParts[1]);
            seconds.setText(durationParts[2]);

            List<Questions> quizQuestions = questionsService.getAllQuizQuestionsByQuizId(quizId);

            // Clear existing content in VBox before adding new ones.
            listeQuestionsOfQuiz.getChildren().clear();

            for (int i = 0; i < quizQuestions.size(); i++) {
                VBox questionVBox = createQuestionVBox(quizQuestions.get(i), i);
                listeQuestionsOfQuiz.getChildren().add(questionVBox);
                // No need to add a separator, VBox spacing will handle it.
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public VBox createQuestionVBox(Questions question, int i){
        VBox questionVBox = new VBox(); // Use VBox for vertical layout
        questionVBox.setSpacing(10); // Set spacing between children inside the VBox

        Text questionLabel = new Text("Question:");
        questionLabel.setLayoutX(27);
        questionLabel.setLayoutY(32);
        questionLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        ImageView imageView;

        TextField questionTextField = new TextField(question.getQuestion());
        questionTextField.setLayoutX(125);
        questionTextField.setLayoutY(10);
        questionTextField.setPrefWidth(355);
        questionTextField.setPrefHeight(27);
        questionTextField.setPromptText("Question...");
        questionTextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");

        Text reponseLabel = new Text("Reponse:");
        reponseLabel.setLayoutX(27);
        reponseLabel.setLayoutY(78);
        reponseLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));

        String responseText = "";
        for (Suggestion suggestion : question.getSuggestionsQuestion()) {
            if (suggestion.isStatus()) {
                responseText = suggestion.getSuggestion();
                break;
            }
        }

        TextField reponseTextField = new TextField(responseText);
        reponseTextField.setLayoutX(125);
        reponseTextField.setLayoutY(56);
        reponseTextField.setPrefWidth(355);
        reponseTextField.setPrefHeight(27);
        reponseTextField.setPromptText("Reponse...");
        reponseTextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");

        Text suggestion1Label = new Text("Suggestion 1:");
        suggestion1Label.setLayoutX(27);
        suggestion1Label.setLayoutY(124);
        suggestion1Label.setFont(Font.font("System", FontWeight.NORMAL, 16));

        TextField suggestion1TextField = new TextField(question.getSuggestionsQuestion().size() > 1 ? question.getSuggestionsQuestion().get(1).getSuggestion() : "");
        suggestion1TextField.setLayoutX(125);
        suggestion1TextField.setLayoutY(102);
        suggestion1TextField.setPrefWidth(355);
        suggestion1TextField.setPrefHeight(27);
        suggestion1TextField.setPromptText("Suggestion 1...");
        suggestion1TextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");

        Text suggestion2Label = new Text("Suggestion 2:");
        suggestion2Label.setLayoutX(27);
        suggestion2Label.setLayoutY(170);
        suggestion2Label.setFont(Font.font("System", FontWeight.NORMAL, 16));

        TextField suggestion2TextField = new TextField(question.getSuggestionsQuestion().size() > 2 ? question.getSuggestionsQuestion().get(2).getSuggestion() : "");
        suggestion2TextField.setLayoutX(125);
        suggestion2TextField.setLayoutY(148);
        suggestion2TextField.setPrefWidth(355);
        suggestion2TextField.setPrefHeight(27);
        suggestion2TextField.setPromptText("Suggestion 2...");
        suggestion2TextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");

        Text suggestion3Label = new Text("Suggestion 3:");
        suggestion3Label.setLayoutX(27);
        suggestion3Label.setLayoutY(216);
        suggestion3Label.setFont(Font.font("System", FontWeight.NORMAL, 16));

        TextField suggestion3TextField = new TextField(question.getSuggestionsQuestion().size() > 3 ? question.getSuggestionsQuestion().get(3).getSuggestion() : "");
        suggestion3TextField.setLayoutX(125);
        suggestion3TextField.setLayoutY(194);
        suggestion3TextField.setPrefWidth(355);
        suggestion3TextField.setPrefHeight(27);
        suggestion3TextField.setPromptText("Suggestion 3...");
        suggestion3TextField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: white; -fx-border-color: #D2D3D4;");


        Button modifierImageButton = new Button();
        modifierImageButton.setLayoutX(550);
        modifierImageButton.setLayoutY(195);
        modifierImageButton.setPrefSize(40, 40);
        modifierImageButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        modifyIcon.setFill(Color.web("#00aeef"));
        modifyIcon.setSize("25");
        modifierImageButton.setGraphic(modifyIcon);
        modifierImageButton.setCursor(Cursor.HAND);


        Button supprimerImageButton = new Button();
        supprimerImageButton.setLayoutX(600);
        supprimerImageButton.setLayoutY(200);
        supprimerImageButton.setPrefSize(28, 25);
        supprimerImageButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        deleteIcon.setFill(Color.web("#00aeef"));
        deleteIcon.setSize("25");
        supprimerImageButton.setGraphic(deleteIcon);
        supprimerImageButton.setCursor(Cursor.HAND);




        if (question.getImage() != null) {
            imageView = new ImageView(question.getImage());
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);
            imageView.setLayoutX(500);
            imageView.setLayoutY(10);
        } else {
            imageView = new ImageView();
        }


        Button modifyButton = new Button("Modifier");
        modifyButton.setLayoutX(130);
        modifyButton.setLayoutY(240);
        modifyButton.setPrefSize(120, 40);
        modifyButton.setStyle("-fx-background-color: #00aeef; -fx-background-radius: 50; -fx-border-radius: 50; -fx-text-fill: white;");
        modifyButton.setFont(Font.font("System Bold", 13));
        modifyButton.setCursor(Cursor.HAND);
        modifyButton.setOnAction(event -> {
            QuestionsService qs=new QuestionsService();
            SuggestionService ss=new SuggestionService();
            String newQuestion = questionTextField.getText();
            String newResponse = reponseTextField.getText();
            String newSuggestion1 = suggestion1TextField.getText();
            String newSuggestion2 = suggestion2TextField.getText();
            String newSuggestion3 = suggestion3TextField.getText();

            List<Suggestion> suggestionList=question.getSuggestionsQuestion();

            try {
                qs.modifierGestionQuiz(new Questions(question.getId(),newQuestion,question.getQuizId(),question.getUserId(),question.getImage()));
                for (Suggestion suggestion : suggestionList) {
                    if (suggestion.isStatus()) {
                        ss.modifierGestionQuiz(new Suggestion(suggestion.getId(), newResponse, true, suggestion.getQuestionId()));
                    } else if (suggestion.getId() == suggestionList.get(1).getId()) {
                        ss.modifierGestionQuiz(new Suggestion(suggestion.getId(), newSuggestion1, false, suggestion.getQuestionId()));
                    } else if (suggestion.getId() == suggestionList.get(2).getId()) {
                        ss.modifierGestionQuiz(new Suggestion(suggestion.getId(), newSuggestion2, false, suggestion.getQuestionId()));
                    } else if (suggestion.getId() == suggestionList.get(3).getId()) {
                        ss.modifierGestionQuiz(new Suggestion(suggestion.getId(), newSuggestion3, false, suggestion.getQuestionId()));
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Question et sugegstions modifiées!");
                alert.showAndWait();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        });
        modifierImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                try {
                    Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                    String imageUrl = (String) uploadResult.get("url");
                    question.setImage(imageUrl);

                    Platform.runLater(() -> {
                        try {
                            Image image = new Image(imageUrl);
                            imageView.setImage(image);
                            imageView.setFitWidth(180);
                            imageView.setFitHeight(180);
                            imageView.setLayoutX(500);
                            imageView.setLayoutY(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // supp image
        supprimerImageButton.setOnAction(event -> {
            try {
                question.setImage(null);
                imageView.setImage(null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Image supprimée!");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setContentText("Une erreur s'est produite lors de la suppression de l'image.");
                alert.showAndWait();
                e.printStackTrace();
            }
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.setLayoutX(280);
        deleteButton.setLayoutY(240);
        deleteButton.setPrefSize(120, 40);
        deleteButton.setStyle("-fx-background-color: #00aeef; -fx-background-radius: 50; -fx-border-radius: 50; -fx-text-fill: white;");
        deleteButton.setFont(Font.font("System Bold", 13));
        // deleteButton.setCursor(Cursor.HAND);
        deleteButton.setOnAction(event -> {
            QuestionsService qs = new QuestionsService();
            SuggestionService ss = new SuggestionService();

            try {
                List<Suggestion> suggestionList = question.getSuggestionsQuestion();
                for (Suggestion suggestion : suggestionList) {
                    ss.supprimerGestionQuiz(suggestion.getId());
                }
                qs.supprimerGestionQuiz(question.getId());

                listeQuestionsOfQuiz.getChildren().remove(questionVBox);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Question et suggestions supprimées!");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        });

        // Add all your components to the VBox instead of Pane
        questionVBox.getChildren().addAll(questionLabel, questionTextField, reponseLabel, reponseTextField,
                suggestion1Label, suggestion1TextField, suggestion2Label, suggestion2TextField,
                suggestion3Label, suggestion3TextField, modifyButton, deleteButton, imageView, modifierImageButton, supprimerImageButton);


        // Return the VBox instead of a Pane
        return questionVBox;
    }*/







    public Pane createQuizPane(Quiz quiz, int index) {
        Pane pane = new Pane();
        pane.setPrefSize( 271, 75);
        pane.setLayoutX( -1);
        pane.setLayoutY(7 + index * (75));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");
        pane.setCursor(Cursor.HAND);

        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 1) {
                        TeacherNavigations.getInstance().openDisplayQuestionFromQuizz(coursId, quiz.getId());
                    }
                }
            }
        });

        Text titreText = new Text(quiz.getNom());
        titreText.setLayoutX(14);
        titreText.setLayoutY(28);
        titreText.setFont(new Font("System Bold", 12));
        titreText.setFill(Color.web("#494949"));

        Text dateText = new Text(quiz.getDuree());
        dateText.setLayoutX(14);
        dateText.setLayoutY(54);
        dateText.setFill(Color.web("#a5a5a5"));

        Button deleteButton = new Button();
        deleteButton.setLayoutX(208);//228
        deleteButton.setLayoutY(37);
        deleteButton.setPrefSize(28, 25);
        deleteButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        deleteIcon.setFill(Color.web("#00aeef"));
        deleteIcon.setSize("20");
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //QuestionsService qs=new QuestionsService();
                    QuizService quizService1=new QuizService();

                    // qs.deleteAllQuestionsByQuizId(myQuiz.get(index).getId());

                    quizService1.supprimerGestionQuiz(myQuiz.get(index).getId());
                    myQuiz.remove(index);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setContentText("Quiz supprimée!");
                    alert.showAndWait();

                    listeQuizId.getChildren().clear();
                    for (int i = 0; i < myQuiz.size(); i++) {
                        Pane quizPane = createQuizPane(myQuiz.get(i), i);
                        listeQuizId.getChildren().add(quizPane);

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pane.getChildren().addAll(titreText, dateText, deleteButton);


        return pane;
    }

    @FXML
    void searchQuiz(ActionEvent event) {
        String searchText = searchField.getText();
        int userID = usserId; // à changer apres pour etre dynamique integration
        try {

            if (searchText.isEmpty()) {
                myQuiz = quizService.getQuizByUserId(userID);
            } else {
                myQuiz = quizService.getQuizByTitle(searchText, userID);
            }
            listeQuizId.getChildren().clear();
            for (int i = 0; i < myQuiz.size(); i++) {
                Pane pane = createQuizPane(myQuiz.get(i), i);
                listeQuizId.getChildren().add(pane);
            }
            listeQuizId.setPrefHeight(myQuiz.size() * 85);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterQuestionAction(ActionEvent event){
        TeacherNavigations.getInstance().openQuestionFromQuizz(coursId , quizId);
    }

    @FXML
    void modifierQuizAction(ActionEvent event){

        try {
            QuizService quizService1=new QuizService();
            String duree = hours.getText() + ":" + minutes.getText() + ":" + seconds.getText();
            quizService1.modifierGestionQuiz(new Quiz(quizId,quizNameId.getText(), duree,coursId,usserId));// à changer apres pour etre dynamique integration
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setContentText("Quiz modifié");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void quitterAction(ActionEvent event){
        MainNavigations.getInstance().openMainHomePage();
    }


    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }
}
