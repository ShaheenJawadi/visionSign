package controllers.User;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.User.PasswordHashing;
import services.User.UserService;
import entities.User;
import State.UserSessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class UserProfilePwdController {

    @FXML
    private MFXPasswordField newPwdTF1;

    @FXML
    private MFXPasswordField newPwdTF2;

    @FXML
    private MFXPasswordField oldPwdTF;



    @FXML
    private AnchorPane rootId ;


    public  AnchorPane getRootId(){
        return  this.rootId ;
    }

private final UserService userService=new UserService();
    private final UserSessionManager userSessionManager=new UserSessionManager();
@FXML
void initialize(){

}
    @FXML
    void modifier(ActionEvent event) {
        String validationError = validateInputs();
        User user=userService.getCurrent();
       // String oldPassword= PasswordHashing.hashPassword(oldPwdTF.getText().trim());
        String oldPassword= oldPwdTF.getText().trim();
        System.out.println( user +":" + user.getPassword());
        System.out.println(oldPassword);
        try{

            if (oldPassword.equals(user.getPassword()) || user.getPassword().equals(" ")){
                if(newPwdTF1.getText().equals(newPwdTF2.getText())){
                    if (validationError.isEmpty()){
                        user.setPassword(newPwdTF2.getText());
                    userService.updatePassword(user);
                    showAlertSuccess("Success", "Password Changed", "Password changed successfully.");
                    }else{showAlertError("Validation Error","",validationError);}

                }

                else
                    showAlertError("Error", "Password Mismatch", "Passwords don't match.");
            }
            else
                showAlertError("Error", "Incorrect Password", "Wrong password.");
        }catch(SQLException e){
            showAlertError("Error","" , e.getMessage());
        }

    }




    private void showAlertError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlertSuccess(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private String validateInputs() {
        StringBuilder validationError = new StringBuilder();
        String password = newPwdTF1.getText();
        if (password.length() < 8 ||
                !password.matches(".*[a-z].*") ||  // At least one lowercase letter
                !password.matches(".*[A-Z].*") ||  // At least one uppercase letter
                !password.matches(".*\\d.*")) {    // At least one digit
            validationError.append("Password should have at least 8 characters, including at least one uppercase letter, one lowercase letter, and one number.\n");
    }
        return validationError.toString();

}}

