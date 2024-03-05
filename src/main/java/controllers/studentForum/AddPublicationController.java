package controllers.studentForum;

import State.MainNavigations;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import entities.Publications;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import services.Forum.PublicationsService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class AddPublicationController extends BaseForumController {
    private static final String API_KEY = "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";

    @FXML
    private  AnchorPane rootId ;

    public AnchorPane getRootBox(){
        return  this.rootId ;
    }
    private int userId=State.UserSessionManager.getInstance().getCurrentUser().getId();
    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dkdx59xe9",
            "api_key", "464462256124751",
            "api_secret", "h0D2KPEbrpHqzK3tSlxRJHadeLE"
    ));
    @FXML
    private TextField titreId;

    @FXML
    private TextField questionId;
    @FXML
    private HBox imageContainer;
    @FXML
    private ImageView uploadImageView;
    private List<String> selectedImagePaths = new ArrayList<>();


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

            selectedImagePaths.add(imageUrl);

            Image image = new Image(imageUrl);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            // Update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                imageContainer.getChildren().add(imageView);
                imageContainer.getChildren().remove(uploadImageView);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    private static boolean checkWithAiModel(String titre, String question) {
        try {
            String prompt = "Please check the following publication for any disrespectful language or inappropriate content for an application for e-learning, including words such as 'stupid,' 'idiot,' or any other derogatory terms. Return true if the publication contains any such language, and false otherwise. Here is the publication: this is the title of the publication '" + titre + "' this is the publication: '" + question + "'";

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]}");
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer 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")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);

            String chatResponse = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            boolean x = chatResponse.equalsIgnoreCase("true");

            return x;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    public void handlePublish(ActionEvent event) {
        String titreText = titreId.getText();
        String questionText = questionId.getText();
        try {
            if (titreText != null && !titreText.isEmpty() && questionText != null && !questionText.isEmpty()) {
                //todo USErid=6

                if (!pubs.publicationExists(titreText, questionText, 18)) {
                    boolean y = checkWithAiModel(titreText, questionText);
                    if (!y) {
                        String imageUrls = String.join(";", selectedImagePaths);
                        //todo USErid=6

                        pubs.addPublicationOrCommentaire(new Publications(titreText, questionText, new Date(), imageUrls, 18));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Succès!");
                        alert.setContentText("Publication ajoutée!");
                        alert.showAndWait();
                        MainNavigations.getInstance().openForumPage();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Contenu inapproprié!");
                        alert.setContentText("La publication contient du contenu inapproprié et ne peut pas être ajoutée.");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur!");
                    alert.setContentText("Une publication avec le même titre et contenu existe déjà.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur!");
                alert.setContentText("Impossible d'ajouter une publication vide.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
