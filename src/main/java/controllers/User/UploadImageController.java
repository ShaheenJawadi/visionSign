package controllers.User;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import apiUtils.UploadImage;
import entities.User;
import services.User.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class UploadImageController {
    private final UserService userService = new UserService();
    private final UploadImage uploadImage = new UploadImage();
    private UserProfileController userProfileController;

    @FXML
    private ImageView imageId;

    private File selectedImageFile;

    public void setUserProfileController(UserProfileController userProfileController) {
        this.userProfileController = userProfileController;
    }


    @FXML
    void initialize(){
        User user=userService.getCurrent();
        if (!user.getImage().isEmpty()){
            Image image=new Image(user.getImage());
            imageId.setImage(image);
        }


    }

    @FXML
    void Save(ActionEvent event) {
        if (selectedImageFile != null) {
            try {
                // Upload image to Cloudinary
                String cloudinaryUrl = uploadImage.uploadImage(selectedImageFile);

                // Update user image URL in the database
                User currentUser = userService.getCurrent();
                currentUser.setImage(cloudinaryUrl);
                userService.updateImage(currentUser);
                if (userProfileController != null) {
                    userProfileController.updateImage(cloudinaryUrl);
                }
                // Display success message
                showAlert("Image Saved Successfully", Alert.AlertType.INFORMATION);
                // Close the stage (optional)
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert("Error updating user image", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Please upload an image first", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void upload(javafx.scene.input.MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) imageId.getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage);

        if (selectedImageFile != null) {
            // Display the selected image in the ImageView
            Image image = new Image(selectedImageFile.toURI().toString());
            imageId.setImage(image);
        }
    }
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Image Upload");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
