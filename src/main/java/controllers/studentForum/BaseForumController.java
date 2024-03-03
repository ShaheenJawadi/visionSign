package controllers.studentForum;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Publications;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Forum.PublicationsService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class BaseForumController {
    @FXML
    protected AnchorPane listepubid, allpubid;
    @FXML
    protected Button addBtn, forumBtn,btnChat;

    @FXML
    protected TextField searchField;
    protected List<Publications> mypub, allPub = null;
    protected PublicationsService pubs = new PublicationsService();
    @FXML
    public void handleChat(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ChatBot.fxml"));
            Parent root = loader.load();

            forumBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void navigateAddPub(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/AddPublication.fxml"));
            Parent root = loader.load();
            addBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleForum(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));
            Parent root = loader.load();

            forumBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane createPublicationPane(Publications publication, int index, boolean isAllPublications) {
        Pane pane = new Pane();
        pane.setCursor(Cursor.HAND);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 1) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/PublicationDetails.fxml"));
                            Parent root = loader.load();

                            PublicationDetailsController pubDetailController = loader.getController();
                            pubDetailController.setPubId(publication.getId());
                            System.out.println("Passing pubId: " + publication.getId());
                            forumBtn.getScene().setRoot(root);

                        } catch (IOException e) {

                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        pane.setPrefSize(isAllPublications ? 682 : 271, isAllPublications ? 158 : 75);
        pane.setLayoutX(isAllPublications ? 6 : -1);
        pane.setLayoutY(7 + index * (isAllPublications ? 165 : 75));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");

        Text titreText = new Text(publication.getTitre());
        titreText.setLayoutX(14);
        titreText.setLayoutY(isAllPublications ? 24 : 28);
        titreText.setFont(new Font("System Bold", isAllPublications ? 16 : 12));
        titreText.setFill(Color.web("#494949"));

        Text dateText = new Text(publication.getDate_creation().toString());
        dateText.setLayoutX(isAllPublications ? 500 : 14);
        dateText.setLayoutY(isAllPublications ? 63 : 54);
        dateText.setFill(Color.web("#a5a5a5"));

        if (isAllPublications) {
            Pane iconPane = new Pane();
            iconPane.setLayoutX(14);
            iconPane.setLayoutY(39);
            iconPane.setPrefSize(41, 38);
            iconPane.setStyle("-fx-background-color: #2447f9; -fx-border-radius: 1000; -fx-background-radius: 1000;");
            FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
            userIcon.setFill(Color.WHITE);
            userIcon.setLayoutX(12);
            userIcon.setLayoutY(27);
            userIcon.setSize("22");
            iconPane.getChildren().add(userIcon);

            Text userIdText = new Text(publication.getUserName());
            userIdText.setLayoutX(62);
            userIdText.setLayoutY(54);
            userIdText.setFont(new Font(14));

            Text roleText = new Text("Elève");
            roleText.setLayoutX(62);
            roleText.setLayoutY(71);
            roleText.setFill(Color.web("#a5a5a5"));
            roleText.setFont(new Font(11));

            Text contenuText = new Text(publication.getContenu());
            contenuText.setLayoutX(31);
            contenuText.setLayoutY(102);
            contenuText.setFill(Color.web("#7a757d"));

            Button commentButton = new Button(publication.getPubCommentaires().size() + " Comment(s)");
            commentButton.setLayoutX(31);
            commentButton.setLayoutY(121);
            commentButton.setPrefSize(110, 27);
            commentButton.setStyle("-fx-background-color: white;");
            commentButton.setTextFill(Color.web("#34364a"));
            FontAwesomeIconView commentIcon = new FontAwesomeIconView(FontAwesomeIcon.COMMENT);
            commentIcon.setFill(Color.web("#34364a"));
            commentIcon.setSize("14");
            commentButton.setGraphic(commentIcon);

            pane.getChildren().addAll(titreText, iconPane, userIdText, roleText, contenuText, dateText, commentButton);
        } else {
            Button modifyButton = new Button();
            modifyButton.setLayoutX(202);
            modifyButton.setLayoutY(37);
            modifyButton.setPrefSize(28, 25);
            modifyButton.setStyle("-fx-background-color: #FFFFFF;");
            FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
            modifyIcon.setFill(Color.web("#2447f9"));
            modifyIcon.setSize("15");
            modifyButton.setGraphic(modifyIcon);
            modifyButton.setCursor(Cursor.HAND);

            modifyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ModifyPublication.fxml"));
                        Parent root = loader.load();

                        ModifyPublicationController modifyController = loader.getController();
                        modifyController.setPubId(mypub.get(index).getId());
                        System.out.println(mypub.get(index).getId());
                        forumBtn.getScene().setRoot(root);
                        refreshPublications();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Button deleteButton = new Button();
            deleteButton.setLayoutX(228);
            deleteButton.setLayoutY(37);
            deleteButton.setPrefSize(28, 25);
            deleteButton.setStyle("-fx-background-color: #FFFFFF;");
            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            deleteIcon.setFill(Color.web("#2447f9"));
            deleteIcon.setSize("15");
            deleteButton.setGraphic(deleteIcon);
            deleteButton.setCursor(Cursor.HAND);
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        int publicationId = mypub.get(index).getId();
                        pubs.deletePublicationOrCommentaire(publicationId);
                        System.out.println("deleted!");

                        mypub.remove(index);
                        listepubid.getChildren().clear();
                        for (int i = 0; i < mypub.size(); i++) {
                            Pane pubPane = createPublicationPane(mypub.get(i), i, false);
                            listepubid.getChildren().add(pubPane);
                        }

                        allPub.removeIf(pub -> pub.getId() == publicationId);
                        allpubid.getChildren().clear();
                        for (int i = 0; i < allPub.size(); i++) {
                            Pane pubPane = createPublicationPane(allPub.get(i), i, true);
                            allpubid.getChildren().add(pubPane);
                        }

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Succes!");
                        alert.setContentText("Publication supprimé!");
                        alert.showAndWait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            pane.getChildren().addAll(titreText, dateText, modifyButton, deleteButton);
        }

        return pane;
    }


    @FXML
    public void searchPubByTitle(ActionEvent event) {
        String searchText = searchField.getText();
        int userID = 6;
        try {

            if (searchText.isEmpty()) {
                mypub = pubs.getPublicationsByUserId(userID);
            } else {
                mypub = pubs.searchPublicationsByTitle(searchText, userID);
            }
            listepubid.getChildren().clear();
            for (int i = 0; i < mypub.size(); i++) {
                Pane pane = createPublicationPane(mypub.get(i), i, false);
                listepubid.getChildren().add(pane);
            }
            listepubid.setPrefHeight(mypub.size() * 75);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshPublications() {
        listepubid.getChildren().clear();
        allpubid.getChildren().clear();

    }


}
