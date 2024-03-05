package controllers.User;

import State.UserSessionManager;
import com.cloudinary.Cloudinary;
import entities.User;
import entities.UserLevel;
import entities.UserRole;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.User.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class UserSettingController {

    @FXML
    private DatePicker dateTF=new DatePicker();


    @FXML
    private Label userTF;
    @FXML
    private ImageView imageId;


    @FXML
    private AnchorPane rootId ;

    @FXML
    private VBox settingHolder ;




    public  AnchorPane getRootId(){
        return  this.rootId ;
    }

    private final UserService userService=new UserService();
    private final UserSessionManager userSessionManager=new UserSessionManager();
    private Cloudinary cloudinary;


 @FXML
    void initialize(){
     User user = userService.getCurrent();

     userTF.setText(user.getUsername());
     ObservableList<UserLevel> userLevels = FXCollections.observableArrayList(UserLevel.values());

     // Check if the user is an admin before showing the levelTF


     System.out.println("hahahahaa");
     String url = user.getImage();
     if(!(url ==null)){
         Image image = new Image(url);
         imageId.setImage(image);
     }
     else{

     }
         Image image=new Image("assets/user/UserDefault.png");
         imageId.setImage(image);
     System.out.println("bababab");


     if (user.getDateNaissance() != null) {
         dateTF.setValue(user.getDateNaissance().toLocalDate());
     }


    }

    @FXML
    void profileButton(ActionEvent event){
        System.out.println("click");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfile.fxml"));
             loader.load();

            UserProfileController page = loader.getController();


            settingHolder.getChildren().clear();
            settingHolder.getChildren().add(page.getRootId());

        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e.getMessage());
        }}
    @FXML
    void pwdButton(ActionEvent event){
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfilePwd.fxml"));
          loader.load();

            UserProfilePwdController page = loader.getController();
            settingHolder.getChildren().clear();
            settingHolder.getChildren().add(page.getRootId());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @FXML
    void privacyButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfilePrivacy.fxml"));
            loader.load();
            UserProfilePrivacyController page = loader.getController();
            settingHolder.getChildren().clear();
            settingHolder.getChildren().add(page.getRootId());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
   @FXML
   void logout(ActionEvent event){
       userSessionManager.clearSession();
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
           Parent privacyRoot = loader.load();
           ScrollPane privacyScrollPane=new ScrollPane(privacyRoot);
           privacyScrollPane.setFitToWidth(true);
           privacyScrollPane.setFitToHeight(true);

       } catch (IOException e) {
           throw new RuntimeException(e.getMessage());
       }

   }

    private void showAlertError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlertSucces(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

@FXML
    public void changePic(javafx.scene.input.MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/User/UploadImage.fxml"));
        try {
            loader.load();
            UploadImageController uploadImageController = loader.getController();
            // Set a reference to this UserProfileController
          //Todo  uploadImageController.setUserProfileController(this);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

    }
    public void updateImage(String imageUrl) {
        Image image = new Image(imageUrl);
        imageId.setImage(image);
    }

}
