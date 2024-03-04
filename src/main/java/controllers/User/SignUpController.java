package controllers.User;

import entities.User;
import entities.UserRole;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.User.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private MFXTextField emailTF;

    @FXML
    private ImageView imgLogo;

    @FXML
    private MFXTextField nomTF;

    @FXML
    private MFXTextField prenomTF;

    @FXML
    private MFXPasswordField pwdTF;

    @FXML
    private MFXComboBox<UserRole> roleTF;

    @FXML
    private MFXTextField usernameTF;
    @FXML
    private Hyperlink hpId1;


    @FXML
    private AnchorPane rootId ;


    public  AnchorPane getRootBox(){
        return  this.rootId ;
    }
    private final UserService userService=new UserService();


    @FXML
    void initialize() {
        // Populate the ComboBox with UserRole values
        ObservableList<UserRole> userRoles = FXCollections.observableArrayList(UserRole.values());
        roleTF.setItems(userRoles);
    }

    @FXML
    void ajouter(ActionEvent event) {
        String validationError = validateInputs();
        if(validationError.isEmpty()){

        try {
            userService.ajouter(new User(nomTF.getText(),prenomTF.getText(),usernameTF.getText(),emailTF.getText(),pwdTF.getText(),
                    UserRole.valueOf(roleTF.getText().toUpperCase())));
            showAlertSuccess("Success","Sign up successful","Account is Ready!");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
                Parent loginRoot = loader.load();
                ScrollPane loginScrollPane = new ScrollPane(loginRoot);
                loginScrollPane.setFitToWidth(true);
                loginScrollPane.setFitToHeight(true);
                usernameTF.getScene().setRoot(loginScrollPane);
            }catch(IOException e){
                throw new RuntimeException(e.getMessage());
            }

        }
        catch (SQLException e){
            showAlertError("Error","",e.getMessage());
        }
          }else
           {showAlertError("Validation error","",validationError);}


    }

    @FXML
    void naviguer(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();
            ScrollPane loginScrollPane = new ScrollPane(loginRoot);
            loginScrollPane.setFitToWidth(true);
            loginScrollPane.setFitToHeight(true);
            usernameTF.getScene().setRoot(loginScrollPane);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private String validateInputs() {
        StringBuilder validationError = new StringBuilder();

        if (!nomTF.getText().matches("[a-zA-Z ]+")) {
            validationError.append("Nom should only contain characters.\n");
        }

// Check if prenom contains only characters (including spaces)
        if (!prenomTF.getText().matches("[a-zA-Z ]+")) {
            validationError.append("Prenom should only contain characters.\n");
        }

        if (!usernameTF.getText().matches("[a-zA-Z]{4,}[a-zA-Z0-9_ ]*")) {
            validationError.append("Username should have at least 4 alphabetical characters and can contain numbers, _, and spaces.\n");
        }
        String password = pwdTF.getText();
        if (password.length() < 8 ||
                !password.matches(".*[a-z].*") ||  // At least one lowercase letter
                !password.matches(".*[A-Z].*") ||  // At least one uppercase letter
                !password.matches(".*\\d.*")) {    // At least one digit
            validationError.append("Password should have at least 8 characters, including at least one uppercase letter, one lowercase letter, and one number.\n");
        }
        String email = emailTF.getText();
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
            validationError.append("Email should be in a valid email format.\n");
        }

        // Add similar checks for email and password

        return validationError.toString();
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

}
