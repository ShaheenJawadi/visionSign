package controllers.User;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
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
    private final UserService userService=new UserService();

    @FXML
    void initialize(){
        // usernameTF.setItem("");

    }
    @FXML
    void naviguer(ActionEvent event)throws SQLException {
        String username = usernameTF.getText();
        String password = pwdTF.getText();
        if(username.isEmpty() || password.isEmpty()) return;
        try {
            if (userService.login(username, password) != null) {
                System.out.println("currentUser"+userService.getCurrent());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
                Parent root = loader.load();
                usernameTF.getScene().setRoot(root);
                // Get the controller instance to access its methods
                //UserProfileController userProfileController = loader.getController();
            } else {
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
    void naviguer2(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
            Parent root = loader.load();
            usernameTF.getScene().setRoot(root);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
