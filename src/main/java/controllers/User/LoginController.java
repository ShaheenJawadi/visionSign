package controllers.User;

import entities.UserRole;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import services.User.UserService;

import java.io.IOException;
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
        try {
            if (userService.login(username, password) != null) {
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


}
