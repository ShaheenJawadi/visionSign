package controllers.User;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import services.User.UserService;
import state.UserSessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class UserProfilePrivacyController {

    @FXML
    private TextField emailTF;
    @FXML
    private Label userTF;
private final UserService userService=new UserService();
private final UserSessionManager userSessionManager=new UserSessionManager();
@FXML
void initialize(){
    userTF.setText(userService.getCurrent().getUsername());
    emailTF.setText(userService.getCurrent().getEmail());
}
    @FXML
    void modifier(ActionEvent event) {
        String validationError = validateInputs();
        User user=userService.getCurrent();
        try{
            if (!emailTF.getText().isEmpty()){
                if(validationError.isEmpty()){
            user.setEmail(emailTF.getText());
            userService.updateEmail(user);

            showAlertSuccess("Success", "Email Changed", "Email changed successfully.");}
            else{showAlertError("Validation Error","",validationError);}
            }
            else
                showAlertError("Error", "Email not Changed", "Email field is empty");
        }
        catch(SQLException e){
            showAlertError("Error","" , e.getMessage());
        }

    }
    @FXML
    void pwdButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfilePwd.fxml"));
            Parent pwdRoot = loader.load();
            ScrollPane rootScrollPane=new ScrollPane(pwdRoot);
            rootScrollPane.setFitToWidth(true);
            rootScrollPane.setFitToHeight(true);
            emailTF.getScene().setRoot(rootScrollPane);
            System.out.println(userService.getCurrent().getEmail());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @FXML
    void profileButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfile.fxml"));
            Parent profileRoot = loader.load();
            ScrollPane profileScrollPane=new ScrollPane(profileRoot);
            profileScrollPane.setFitToWidth(true);
            profileScrollPane.setFitToHeight(true);
            emailTF.getScene().setRoot(profileScrollPane);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }}

    @FXML
    void logout(ActionEvent event){
        userSessionManager.clearSession();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent privacyRoot = loader.load();
            ScrollPane privacyScrollPane=new ScrollPane(privacyRoot);
            privacyScrollPane.setFitToWidth(true);
            privacyScrollPane.setFitToHeight(true);
            emailTF.getScene().setRoot(privacyScrollPane);
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
    private void showAlertSuccess(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



    private String validateInputs() {
        StringBuilder validationError = new StringBuilder();
        // Check if email is in email format
        String email = emailTF.getText();
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
            validationError.append("Email should be in a valid email format.\n");
        }


        return validationError.toString();
    }

}
