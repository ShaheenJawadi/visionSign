package controllers.studentForum;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Publications;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import services.Forum.PublicationsService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ModifyPublicationController extends BaseForumController  {


    @FXML
    private TextField titreId, questionId;

    @FXML
    private Button modPubBtn,trashBtn;
    @FXML
    private HBox imageContainer;
    private int pubId;
    private Map<ImageView, String> selectedImagePaths = new HashMap<>();
    @FXML
    private ImageView uploadImageView;
    private ImageView selectedImageView ;
    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dkdx59xe9",
            "api_key", "464462256124751",
            "api_secret", "h0D2KPEbrpHqzK3tSlxRJHadeLE"
    ));

    public void setPubId(int pubId) {
        this.pubId = pubId;
        loadPublicationDetails();
        loadImages();
    }

    public void loadImages() {
        try {
            Publications getPub = pubs.getByIdPublicationOrCommentaire(pubId);
            if (getPub != null) {
                String imageUrls = getPub.getImages();
                if (imageUrls != null && !imageUrls.isEmpty()) {
                    String[] urls = imageUrls.split(";");
                    for (String url : urls) {
                        Image image = new Image(url);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        imageView.setOnMouseClicked(event -> {
                            if (selectedImageView != null) {
                                selectedImageView.setStyle(""); // Remove border from previously selected image
                            }
                            imageView.setStyle("-fx-border-color: red; -fx-border-width: 10; -fx-border-style: solid;");
                            selectedImageView = imageView;
                        });
                        imageContainer.getChildren().add(imageView);
                        selectedImagePaths.put(imageView, url);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error when trying to load images: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error when trying to load images: " + e.getMessage());
            e.printStackTrace();
        }
        trashBtn.setVisible(!selectedImagePaths.isEmpty());

    }



    @FXML
    private void deleteImage() {
        if (selectedImageView != null) {
            imageContainer.getChildren().remove(selectedImageView);
            selectedImagePaths.remove(selectedImageView);
            updateImagePathsInDatabase();
            selectedImageView = null;

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Image deleted successfully!");
            successAlert.showAndWait();
            trashBtn.setVisible(!selectedImagePaths.isEmpty());

        }
    }


    private void updateImagePathsInDatabase() {
        String imageUrls = String.join(";", selectedImagePaths.values());

        try {
            Publications updatedPub = pubs.getByIdPublicationOrCommentaire(pubId);
            if (updatedPub != null) {
                updatedPub.setImages(imageUrls);
                pubs.updatePublicationOrCommentaire(updatedPub);
            }
        } catch (SQLException e) {
            System.err.println("SQL error when trying to update image paths in database: " + e.getMessage());
            e.printStackTrace();
        }
    }

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
        fileChooser.setTitle("Choose Image Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(null);

        if (files != null && !files.isEmpty()) {
            for (File file : files) {
                // Create a new Task for the upload process
                Task<Void> uploadTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        uploadFile(file);
                        return null;
                    }
                };

                // Add a ProgressIndicator to the HBox
                ProgressIndicator progressIndicator = new ProgressIndicator();

                imageContainer.getChildren().add(progressIndicator);
                // Run the upload task in a background thread
                new Thread(uploadTask).start();

                // Remove the ProgressIndicator when the task is done
                uploadTask.setOnSucceeded(event -> {
                    imageContainer.getChildren().remove(progressIndicator);
                });
            }
        }
    }

    private void uploadFile(File file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

            String imageUrl = (String) uploadResult.get("url");

            Image image = new Image(imageUrl);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setOnMouseClicked(event -> {
                if (selectedImageView != null) {
                    selectedImageView.setStyle(""); // Remove border from previously selected image
                }
                imageView.setStyle("-fx-border-color: red; -fx-border-width: 10; -fx-border-style: solid;");
                selectedImageView = imageView;
            });

            // Add the ImageView and its URL to the selectedImagePaths map
            selectedImagePaths.put(imageView, imageUrl);

            // Update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                imageContainer.getChildren().add(imageView);
                imageContainer.getChildren().remove(uploadImageView);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        trashBtn.setVisible(!selectedImagePaths.isEmpty());

    }

    public void loadPublicationDetails() {
        try {
            Publications getPub = pubs.getByIdPublicationOrCommentaire(pubId);
            if (getPub != null) {
                titreId.setText(getPub.getTitre());
                questionId.setText(getPub.getContenu());
            } else {
                System.out.println("No publication found with ID: " + pubId);
            }
        } catch (SQLException e) {
            System.err.println("SQL error when trying to load publication details: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error when trying to load publication details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        String titreText = titreId.getText();
        String questionText = questionId.getText();

        try {
            if (titreText != null && !titreText.isEmpty() && questionText != null && !questionText.isEmpty()) {
                // Join the image URLs with semicolons
                String imageUrls = String.join(";", selectedImagePaths.values());

                // Update the publication with the new image URLs
                pubs.updatePublicationOrCommentaire(new Publications(pubId, titreText, questionText, new Date(), imageUrls, 6));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Publication updated successfully!");
                alert.showAndWait();

                // Load the updated list of publications
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));
                Parent root = loader.load();
                modPubBtn.getScene().setRoot(root);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("Title and content cannot be empty!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        trashBtn.setVisible(false);
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

}
