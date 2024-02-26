package controllers.studentForum;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Commentaires;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Forum.CommentairesService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AddCommentController {
    @FXML
    private TextField commentField;
    private int pubId;
    private CommentairesService commentService = new CommentairesService();
    @FXML
    private Pane pubPane,CommentId;
    @FXML

    private AnchorPane CommentsId;
    private Pane publicationPane;
    List<Commentaires> allCom;
    public void setPubId(int pubId) {
        this.pubId = pubId;
        loadComments();
    }
    public void loadComments() {
        CommentId.getChildren().clear();
        try {
            List<Commentaires> allCom = commentService.getCommentairesByPublicationId(pubId);
            double paneHeight = 0;
            for (int i = 0; i < allCom.size(); i++) {
                Pane pane = createCommentPane(allCom.get(i), i);
                CommentId.getChildren().add(pane);
                paneHeight += pane.getPrefHeight();
            }
            CommentId.setPrefHeight(Math.max(paneHeight, 100));

            CommentsId.prefHeightProperty().bind(CommentId.heightProperty());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void handleForum(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));
            Parent root = loader.load();
            commentField.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public Pane createCommentPane(Commentaires comment, int index) {
        Pane pane = new Pane();
        pane.setPrefSize( 862 ,118 );
        pane.setLayoutX( 6 );
        pane.setLayoutY(7 + index * ( 115 ));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");


        Text dateText = new Text(comment.getDate().toString());
        dateText.setLayoutX( 700 );
        dateText.setLayoutY( 63 );
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


        pane.getChildren().addAll( iconPane, userIdText, roleText, contenuText, dateText);


        return pane;
    }


}
