package controllers.studentForum;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Commentaires;
import entities.Publications;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Forum.CommentairesService;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PublicationDetailsController extends BaseForumController{

    @FXML
    protected AnchorPane anchorepubid,CommentsId;
    @FXML
    private  Pane CommentId;
    @FXML
    private TextField commentField;
    @FXML
    private ScrollPane scrollid;
    private int pubId;
    private CommentairesService commentService = new CommentairesService();
    private   List<Commentaires> allCom ;


    public void setPubId(int pubId) {
        this.pubId = pubId;
        System.out.println("Passing pubId2: " +pubId);
        pubDetails();



    }

    @FXML
    public void initialize() {
        System.out.println("initialize"+pubId);
        try {
            mypub = pubs.getPublicationsByUserId(6);
            mypub.sort(Comparator.comparing(Publications::getDate_creation).reversed());

            if (mypub.isEmpty()) {
                Text emptyText = new Text("Vous n'avez pas encore publié!");
                emptyText.setFont(new Font("System", 15));
                emptyText.setFill(Color.GRAY);
                emptyText.setLayoutX(19);
                emptyText.setLayoutY(172);
                listepubid.getChildren().add(emptyText);
            } else {
                for (int i = 0; i < mypub.size(); i++) {
                    Pane pane = createPublicationPane(mypub.get(i), i, false);
                    listepubid.getChildren().add(pane);
                }
            }
            listepubid.setPrefHeight(mypub.isEmpty() ? 100 : mypub.size() * 85);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
    public void loadComments() {
        CommentId.getChildren().clear();
        try {
            allCom = commentService.getCommentairesByPublicationId(pubId);
            if (allCom.isEmpty()) {
                Text noCommentsText = new Text("No comments yet, add yours!");
                noCommentsText.setFont(new Font(14));
                noCommentsText.setLayoutX(10);
                noCommentsText.setLayoutY(20);
                CommentId.getChildren().add(noCommentsText);
                CommentId.setPrefHeight(40);
                CommentsId.setPrefHeight(60);
            } else {
                double paneHeight = 0;
                double spacing = 10;
                for (int i = 0; i < allCom.size(); i++) {
                    Pane pane = createCommentPane(allCom.get(i), i);
                    pane.setLayoutY(paneHeight + i * spacing);
                    CommentId.getChildren().add(pane);
                    paneHeight += pane.getPrefHeight();
                }
                paneHeight += (allCom.size() - 1) * spacing;
                CommentId.setPrefHeight(paneHeight);
                CommentsId.setPrefHeight(paneHeight + 20);
            }

            Platform.runLater(() -> scrollid.setVvalue(0.0));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Pane createCommentPane(Commentaires comment, int index) {
        Pane pane = new Pane();
        pane.setPrefSize(680, 105);
        pane.setLayoutX(5);
        pane.setLayoutY(0 + index * 115);
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");

        Text dateText = new Text(comment.getDate().toString());
        dateText.setLayoutX(530);
        dateText.setLayoutY(63);
        dateText.setFill(Color.web("#a5a5a5"));

        Pane iconPane = new Pane();
        iconPane.setLayoutX(14);
        iconPane.setLayoutY(22);
        iconPane.setPrefSize(41, 38);
        iconPane.setStyle("-fx-background-color: #2447f9; -fx-border-radius: 1000; -fx-background-radius: 1000;");
        FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        userIcon.setFill(Color.WHITE);
        userIcon.setLayoutX(12);
        userIcon.setLayoutY(25);
        userIcon.setSize("22");
        iconPane.getChildren().add(userIcon);

        Text userIdText = new Text(comment.getUserName());
        userIdText.setLayoutX(62);
        userIdText.setLayoutY(37);
        userIdText.setFont(new Font(14));



        Text roleText = new Text("Enseignant");
        roleText.setLayoutX(62);
        roleText.setLayoutY(54);
        roleText.setFill(Color.web("#a5a5a5"));
        roleText.setFont(new Font(11));

        Text contenuText = new Text(comment.getCommentaire());
        contenuText.setLayoutX(31);
        contenuText.setLayoutY(85);
        contenuText.setFill(Color.web("#7a757d"));
        if (comment.getUserId() == 6) {
            Button deleteButton = new Button();
            deleteButton.setLayoutX(userIdText.getLayoutBounds().getWidth() + 70);
            deleteButton.setLayoutY(37);
            deleteButton.setPrefSize(28, 25);
            deleteButton.setStyle("-fx-background-color: #FFFFFF;");
            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            deleteIcon.setFill(Color.web("#2447f9"));
            deleteIcon.setSize("15");
            deleteButton.setGraphic(deleteIcon);
            deleteButton.setCursor(Cursor.HAND);
            deleteButton.setOnAction(event -> {
                try {
                    int commentId = comment.getId();
                    commentService.deletePublicationOrCommentaire(commentId);
                    System.out.println("Comment deleted!");

                    allCom.removeIf(c -> c.getId() == commentId);

                    CommentId.getChildren().clear();
                    double paneHeight = 0;
                    double spacing = 10;
                    for (int i = 0; i < allCom.size(); i++) {
                        Pane commentPane = createCommentPane(allCom.get(i), i);
                        CommentId.getChildren().add(commentPane);
                        paneHeight += commentPane.getPrefHeight() + spacing;
                    }
                    CommentId.setPrefHeight(paneHeight);
                    CommentsId.setPrefHeight(paneHeight + 20);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setContentText("Comment deleted!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            Button modifyButton = new Button();
            modifyButton.setLayoutX(userIdText.getLayoutBounds().getWidth() + 100);
            modifyButton.setLayoutY(37);
            modifyButton.setPrefSize(28, 25);
            modifyButton.setStyle("-fx-background-color: #FFFFFF;");
            FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
            modifyIcon.setFill(Color.web("#2447f9"));
            modifyIcon.setSize("15");
            modifyButton.setGraphic(modifyIcon);
            modifyButton.setCursor(Cursor.HAND);
            modifyButton.setOnAction(event -> {
                TextField editField = new TextField(contenuText.getText());
                editField.setLayoutX(contenuText.getLayoutX());
                editField.setLayoutY(contenuText.getLayoutY() - 20);
                editField.setPrefWidth(600);


                editField.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        comment.setCommentaire(editField.getText());
                        try {
                            commentService.updatePublicationOrCommentaire(comment);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        pane.getChildren().remove(editField);
                        contenuText.setText(editField.getText());
                        pane.getChildren().add(contenuText);
                    }
                });

                pane.getChildren().remove(contenuText);
                pane.getChildren().add(editField);
            });

            pane.getChildren().addAll(deleteButton, modifyButton);
        }

        pane.getChildren().addAll(iconPane, userIdText, roleText, contenuText, dateText);

        return pane;
    }
    @FXML
    public void handleadd() {
        String commentText = commentField.getText();
        if (!commentText.isEmpty() && commentText!=null) {
            try {
                if (!commentService.commentaireExists(commentText, pubId, 6)) {
                    Commentaires newComment = new Commentaires();
                    newComment.setCommentaire(commentText);
                    newComment.setDate(new Date());
                    newComment.setId_pub(pubId);
                    newComment.setUserId(6);
                    commentService.addPublicationOrCommentaire(newComment);
                    loadComments();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès!");
                    alert.setContentText("Commentaire ajouté!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur!");
                    alert.setContentText("Un commentaire identique existe déjà pour cette publication.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            commentField.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention!");
            alert.setContentText("Le commentaire ne peut pas être vide.");
            alert.showAndWait();
        }
    }
   public void pubDetails() {
try {
    Publications selectedPublication = pubs.getByIdPublicationOrCommentaire(pubId);

        if (selectedPublication != null) {
            Pane pubPane = createPublicationPane(selectedPublication, 0, true);

            anchorepubid.getChildren().remove(pubPane);
            anchorepubid.getChildren().add(pubPane);
            loadComments();
        loadComments();}
         else {
            Text notFoundText = new Text("Publication introuvable!");
            notFoundText.setFont(new Font("System", 15));
            notFoundText.setFill(Color.RED);
            notFoundText.setLayoutX(19);
            notFoundText.setLayoutY(172);
            anchorepubid.getChildren().add(notFoundText);
        }}catch (SQLException e){
    System.out.println(e.getMessage());
}
    }
}
