package controllers.User;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.User.UserService;
import State.UserSessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class UserProfilePrivacyController {


    @FXML
    private TextField emailTF;


    @FXML
    private AnchorPane rootId;



    public AnchorPane getRootId() {
        return  this.rootId;
    }
private final UserService userService=new UserService();
private final UserSessionManager userSessionManager=new UserSessionManager();
@FXML
void initialize(){

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
