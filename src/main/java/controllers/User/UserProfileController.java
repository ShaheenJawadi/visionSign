package controllers.User;

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
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import services.User.UserService;

public class UserProfileController {

    @FXML
    private DatePicker dateTF=new DatePicker();

    @FXML
    private MFXComboBox<UserLevel> levelTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField usernameTF;
    @FXML
    private Label userTF;
    private final UserService userService=new UserService();

 @FXML
    void initialize(){
    User user=userService.getCurrent();
        userTF.setText(user.getUsername());
        ObservableList<UserLevel> userLevels = FXCollections.observableArrayList(UserLevel.values());
        levelTF.setItems(userLevels);
        nomTF.setText(user.getNom());
        prenomTF.setText(user.getPrenom());
        usernameTF.setText(user.getUsername());
        if(user.getDateNaissance()!=null){dateTF.setValue(user.getDateNaissance().toLocalDate());}
     switch (user.getLevelId()) {
         case 1 -> levelTF.setValue(UserLevel.DEBUTANT);
         case 2 -> levelTF.setValue(UserLevel.INTERMEDIAIRE);
         case 3 -> levelTF.setValue(UserLevel.AVANCE);
         case 0 -> levelTF.setValue(UserLevel.NULL);
     }
    }

    @FXML
    void modifier(ActionEvent event) {
        String validationError = validateInputs();
        try {
            LocalDate selectedDate = dateTF.getValue();
            java.sql.Date dateToSet = null;
            if (selectedDate != null) {
                dateToSet = java.sql.Date.valueOf(selectedDate);
            }

            User userToUpdate = new User(userService.getCurrent().getId(),nomTF.getText(),prenomTF.getText(),usernameTF.getText(),dateToSet,userService.getCurrent().getEmail(),userService.getCurrent().getPassword(),userService.getCurrent().getRole(),userService.getCurrent().getStatus());
            System.out.println("Useerr to updateee"+userToUpdate.getId());
            System.out.println("neww datee"+userToUpdate.getDateNaissance());
            switch (levelTF.getText()) {
                case "DEBUTANT" -> userToUpdate.setLevelId(1);
                case "INTERMEDIAIRE" -> userToUpdate.setLevelId(2);
                case "AVANCE" -> userToUpdate.setLevelId(3);
            }
            System.out.println("aaaaaaaaaaaaaaaaaaaaa");

            if (validationError.isEmpty()) {
            userService.modifier(userToUpdate);
            showAlertSucces("Success", "User modified", "User modified successfully.");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setContentText(validationError);
                alert.showAndWait();

            }
        }
        catch (SQLException e){
            showAlertError("Error", "", e.getMessage());
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
            usernameTF.getScene().setRoot(rootScrollPane);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @FXML
    void privacyButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfilePrivacy.fxml"));
            Parent privacyRoot = loader.load();
            ScrollPane privacyScrollPane=new ScrollPane(privacyRoot);
            privacyScrollPane.setFitToWidth(true);
            privacyScrollPane.setFitToHeight(true);
            usernameTF.getScene().setRoot(privacyScrollPane);
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

    private String validateInputs() {
        StringBuilder validationError = new StringBuilder();

        if (!nomTF.getText().matches("[a-zA-Z ]+")) {
            validationError.append("Nom should only contain characters.\n");
        }

        if (!prenomTF.getText().matches("[a-zA-Z ]+")) {
            validationError.append("Prenom should only contain characters.\n");
        }

        if (!usernameTF.getText().matches("[a-zA-Z]{4,}[a-zA-Z0-9_ ]*")) {
            validationError.append("Username should have at least 4 alphabetical characters and can contain numbers, _, and spaces.\n");
        }

       if(levelTF.getText().isEmpty()){
           validationError.append("You must choose a level");
       }
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = dateTF.getValue();
        if (selectedDate == null || Period.between(selectedDate, currentDate).getYears() < 12) {
            validationError.append("Please select a valid date of birth (user must be at least 12 years old).\n");
        }

        return validationError.toString();
    }
}