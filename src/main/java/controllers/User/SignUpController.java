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
import javafx.scene.image.ImageView;
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
    private Hyperlink hpId;
    private final UserService userService=new UserService();


    @FXML
    void initialize() {
        // Populate the ComboBox with UserRole values
        ObservableList<UserRole> userRoles = FXCollections.observableArrayList(UserRole.values());
        roleTF.setItems(userRoles);
    }

    @FXML
    void ajouter(ActionEvent event) {

        try {
            userService.ajouter(new User(nomTF.getText(),prenomTF.getText(),usernameTF.getText(),emailTF.getText(),pwdTF.getText(),
                    UserRole.valueOf(roleTF.getText().toUpperCase())));
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("sucess");
            alert.setContentText("person added succesfully");
            alert.showAndWait();

        }
        catch (SQLException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }
/*
    @FXML
    void naviguer(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            usernameTF.getScene().setRoot(root);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }  */

}
