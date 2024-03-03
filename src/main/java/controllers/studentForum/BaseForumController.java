package controllers.studentForum;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Publications;
import entities.Reactions;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Forum.PublicationsService;
import services.Forum.ReactionsService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class BaseForumController {
    @FXML
    protected AnchorPane listepubid;

    @FXML
    protected VBox allpubid;
    @FXML
    protected Button addBtn, forumBtn,btnChat;

    @FXML
    protected TextField searchField;
    protected List<Publications> mypub, allPub = null;
    protected PublicationsService pubs = new PublicationsService();
    protected ReactionsService reactionsService=new ReactionsService();
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

    private void playSound(String soundFile) {
        try {
            Media sound = new Media(soundFile);
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Failed to play sound: " + e.getMessage());
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
        double layoutY= 7 + index * (isAllPublications ? 165 : 75);

        pane.setLayoutY(layoutY);
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


            Button likeButton = new Button(publication.getJaime() + " Like");
            likeButton.setLayoutX(160);
            likeButton.setLayoutY(publication.getImages().isEmpty() ? 121 :205);
            likeButton.setPrefSize(90, 27);
            likeButton.setStyle("-fx-background-color: white;");
            likeButton.setTextFill(Color.web("#34364a"));
            FontAwesomeIconView likeIcon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
            likeIcon.setFill(Color.web("#008000"));
            likeIcon.setSize("14");
            likeButton.setGraphic(likeIcon);


            Platform.runLater(() -> {
                try {
                    boolean isLiked = reactionsService.isLikedByUser(6, publication.getId());

                    if (isLiked) {
                        likeButton.setStyle("-fx-background-color: #E2FFE2;-fx-background-radius: 200;");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });




            Button dislikeButton = new Button(publication.getDislike() + " Dislike");
            dislikeButton.setLayoutX(250);
            dislikeButton.setLayoutY(publication.getImages().isEmpty() ? 121 :205);

            dislikeButton.setPrefSize(90, 27);
            dislikeButton.setStyle("-fx-background-color: white;");
            dislikeButton.setTextFill(Color.web("#34364a"));
            FontAwesomeIconView dislikeIcon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_DOWN);
            dislikeIcon.setFill(Color.web("#FF0000"));
            dislikeIcon.setSize("14");
            dislikeButton.setGraphic(dislikeIcon);
            likeButton.setOnAction(event -> {
                try {
                    reactionsService.addLike(6, publication.getId(), 1, 0);

                    int updatedLikes = reactionsService.getLikesCount(publication.getId());
                    int updatedDislikes = reactionsService.getDislikesCounts(publication.getId());

                    likeButton.setText(updatedLikes + " Like");
                    dislikeButton.setText(updatedDislikes + " Dislike");

                    likeButton.setStyle("-fx-background-color: #E2FFE2;-fx-background-radius: 200;");
                    dislikeButton.setStyle("-fx-background-color: white;");
                    playSound("https://res.cloudinary.com/dkdx59xe9/video/upload/v1709508219/Facebook_Like_Sound_Effect_nbc6ju.mp3");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            dislikeButton.setOnAction(event -> {
                try {
                    reactionsService.addLike(6, publication.getId(), 0, 1);

                    int updatedLikes = reactionsService.getLikesCount(publication.getId());
                    int updatedDislikes = reactionsService.getDislikesCounts(publication.getId());

                    likeButton.setText(updatedLikes + " Like");
                    dislikeButton.setText(updatedDislikes + " Dislike");

                    dislikeButton.setStyle("-fx-background-color: #FFE2E2;-fx-background-radius: 200;");
                    likeButton.setStyle("-fx-background-color: white;");

                    playSound("https://res.cloudinary.com/dkdx59xe9/video/upload/v1709508219/Facebook_Like_Sound_Effect_nbc6ju.mp3");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            Platform.runLater(() -> {
                try {
                    boolean isdisliked = reactionsService.isDislikedByUser(6, publication.getId());

                    if (isdisliked) {
                        dislikeButton.setStyle("-fx-background-color: #FFE2E2;-fx-background-radius: 200;");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            if (publication.getImages() != null && !publication.getImages().isEmpty()) {
                AnchorPane imagesPane = new AnchorPane();
                String[] images = publication.getImages().split(";");
                double imageWidth = 100; // Adjust the image size as needed
                double imageHeight = 90; // Adjust the image size as needed
                double imageGap = 7; // Adjust the gap between images as needed
                double x = imageGap;

                for (String imageUrl : images) {
                    ImageView imageView = new ImageView();

                    DisplayImgTest imageLoader = new DisplayImgTest(imageView, imageUrl);
                    imageLoader.loadImage();


                    imageView.setFitWidth(imageWidth);
                    imageView.setFitHeight(imageHeight);
                    imageView.setPreserveRatio(true);
                    AnchorPane.setLeftAnchor(imageView, x);
                    AnchorPane.setTopAnchor(imageView, imageGap);
                    imagesPane.getChildren().add(imageView);
                    x += imageWidth + imageGap;
                }
                imagesPane.setLayoutX(31);
                imagesPane.setLayoutY(105);
                imagesPane.setPrefHeight(imageHeight + 2 * imageGap); // Set height to accommodate the images
                commentButton.setLayoutY(205);
                // Add imagesPane to the main pane and adjust the main pane's height
                pane.getChildren().add(imagesPane);

                // Calculate the new height for the pane
                double newHeight = 158+ 100 + imageGap; // Additional space for margin
                layoutY = 7 + index * newHeight;
                pane.setLayoutY(layoutY);


                pane.setPrefSize(682,newHeight);
            }


            pane.getChildren().addAll(titreText, iconPane, userIdText, roleText, contenuText, dateText, commentButton,likeButton,dislikeButton);
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
