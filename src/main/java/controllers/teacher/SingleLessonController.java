package controllers.teacher;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import entities.Lesson;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import validation.ValidateCours;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SingleLessonController implements Initializable {


    @FXML
    private HTMLEditor Content;

    @FXML
    private TextField Duration;

    @FXML
    private TextField Order;

    @FXML
    private TextField Title;

    @FXML
    private TextField Video;

    @FXML
    private Button leconImage;

    @FXML
    private VBox singleLessonBox;

    @FXML
    private Label validateContent;

    @FXML
    private Label validateDuration;

    @FXML
    private Label validateLink;

    @FXML
    private Label validateOrder;

    @FXML
    private Label validateTitle;


    @FXML
    private HBox imageContainer;

    private  String imgLink ;


    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dkdx59xe9",
            "api_key", "464462256124751",
            "api_secret", "h0D2KPEbrpHqzK3tSlxRJHadeLE"
    ));


    public VBox getSingleLessonBox() {
        return singleLessonBox;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initValidation();
    }

    public  void initValidation(){


        validateContent.setVisible(false);

        validateDuration.setVisible(false);

        validateLink.setVisible(false);

        validateOrder.setVisible(false);

        validateTitle.setVisible(false);
    }

    public String getLessonData() {

        return Title.getText()+"sdqdq";
    }



    public boolean vaidateLessonFields(){





      boolean  isValid =   ValidateCours.isNotEmpty(Video ,validateLink , "ee")
                        && ValidateCours.isNotEmpty(Title ,validateTitle , "ee")
                        && ValidateCours.isNotEmptyNumber(Order ,validateOrder , "ee")
              && ValidateCours.isNotEmptyNumber(Duration ,validateDuration , "ee")
              && ValidateCours.isNotHTML(Content ,validateContent , "ee") ;

      return isValid ;
    }

    public Lesson getLesson(int coursId){
        if(vaidateLessonFields()){


            Lesson lesson = new Lesson();
            lesson.setCoursId(coursId);
            lesson.setTitre(Title.getText());
            lesson.setContent(Content.getHtmlText());
            lesson.setDuree(Integer.valueOf(Duration.getText()));
            lesson.setClassement(Integer.valueOf(Order.getText()));
            lesson.setImage(imgLink);
            lesson.setVideo(Video.getText());
            System.out.println(lesson);
            return  lesson ;


        }
        else
            return null;

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

            imgLink=imageUrl;
            System.out.println("eee");
            System.out.println(imageUrl);

            Image image = new Image(imageUrl);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            // Update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                imageContainer.getChildren().add(imageView);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
