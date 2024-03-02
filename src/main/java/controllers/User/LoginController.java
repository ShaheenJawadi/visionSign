package controllers.User;

import apiUtils.GoogleOAuth2;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import entities.User;
import entities.UserRole;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.User.PasswordHashing;
import services.User.UserService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private ImageView imgLogo;

    @FXML
    private MFXPasswordField pwdTF;

    @FXML
    private MFXTextField usernameTF;
    @FXML
    private Hyperlink signHP;
    @FXML
    private Hyperlink forgetHP;

    private final UserService userService=new UserService();

    @FXML
    void initialize(){
        // usernameTF.setItem("");

    }
    @FXML
    void login(ActionEvent event)throws SQLException {
        String username = usernameTF.getText();
        String password = pwdTF.getText();
        if(username.isEmpty() || password.isEmpty()) return;
        System.out.println(PasswordHashing.hashPassword(password));
        try {
            if (userService.login(username, PasswordHashing.hashPassword(password)) != null) {
                if (userService.getCurrent().getRole() == UserRole.ADMIN) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AdminDash.fxml"));
                    Parent adminDashRoot = loader.load();
                    ScrollPane adminDashScrollPane = new ScrollPane(adminDashRoot);
                    adminDashScrollPane.setFitToWidth(true);
                    adminDashScrollPane.setFitToHeight(true);
                    usernameTF.getScene().setRoot(adminDashScrollPane);
                } else {
                    System.out.println("currentUser" + userService.getCurrent());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfile.fxml"));
                    Parent userProfileRoot = loader.load();
                    ScrollPane userProfileScrollPane = new ScrollPane(userProfileRoot);
                    userProfileScrollPane.setFitToWidth(true);
                    userProfileScrollPane.setFitToHeight(true);
                    usernameTF.getScene().setRoot(userProfileScrollPane);
                    //UserProfileController userProfileController = loader.getController();
                }
            }else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login failed.");
                alert.setContentText("Invalid Username or password");
                alert.showAndWait();
            }
        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());
        }
    }
    @FXML
    void naviguer(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/SignUp.fxml"));
            Parent SignUpRoot = loader.load();
            ScrollPane signUpScrollPane=new ScrollPane(SignUpRoot);
            signUpScrollPane.setFitToWidth(true);
            signUpScrollPane.setFitToHeight(true);
            usernameTF.getScene().setRoot(signUpScrollPane);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @FXML
    void emailLogin(ActionEvent event) throws Exception {
        String authorizationUrl = GoogleOAuth2.buildAuthorizationUrl();
        System.out.println("Authorization URL: " + authorizationUrl);

        // Step 2: User grants permission and gets the authorization code

        // Step 3: Exchange authorization code for access token
        String authorizationCode = "CODE_RECEIVED_FROM_USER"; // Replace with the actual authorization code
        Credential credential = GoogleOAuth2.exchangeCodeForToken(authorizationCode);

        System.out.println("Credentials " + getUserEmail(credential));
        System.out.println("dddddddddddd");
        if (!verifyUser(credential)) {
            createUser(credential);
        }
        if (userService.loginWithEmail(getUserEmail(credential)) != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfile.fxml"));
            Parent userProfileRoot = loader.load();
            ScrollPane userProfileScrollPane = new ScrollPane(userProfileRoot);
            userProfileScrollPane.setFitToWidth(true);
            userProfileScrollPane.setFitToHeight(true);
            usernameTF.getScene().setRoot(userProfileScrollPane);


    }}

    private static String getUserEmail(Credential credential)  {
        GoogleCredential googleCredential = new GoogleCredential().setAccessToken(credential.getAccessToken());

        Oauth2 oauth2 = null;
        try {
            oauth2 = new Oauth2.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    googleCredential)
                    .setApplicationName("visionSignAcademy")
                    .build();
            Userinfoplus userInfo = oauth2.userinfo().get().execute();
            return userInfo.getEmail();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

private boolean verifyUser(Credential credential) throws SQLException, GeneralSecurityException, IOException {
        return userService.recuperer().stream().anyMatch(u->u.getEmail().equals(getUserEmail(credential)));
}
    private void createUser(Credential credential) throws SQLException{
        userService.ajouter(new User(getUserEmail(credential)));


    }

@FXML
   void forgetPwd(ActionEvent event){
    FXMLLoader loader=new FXMLLoader();
    loader.setLocation(getClass().getResource("/User/ForgetPwd.fxml"));
    try{
        loader.load();

    } catch (IOException e) {
        System.err.println(e.getMessage());
    }
    //ForgetPwdController muc=loader.getController();
    Parent parent=loader.getRoot();
    Stage stage=new Stage();
    stage.setScene(new Scene(parent));
    stage.initStyle(StageStyle.UTILITY);
    stage.show();

}


   }

