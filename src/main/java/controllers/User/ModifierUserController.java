package controllers.User;
import entities.User;
import entities.UserLevel;
import entities.UserRole;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import services.User.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class ModifierUserController {

    @FXML
    private TextField emailTF;

    @FXML
    private DatePicker dateTF;

    @FXML
    private TextField idTF;

    @FXML
    private ComboBox<UserLevel> levelTF;

    @FXML
    private Button modifyButton;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private MFXPasswordField pwdTF;

    @FXML
    private ComboBox<UserRole> roleTF;

    @FXML
    private ComboBox<String> statusTF;

    @FXML
    private TextField usernameTF;
    private final UserService userService=new UserService();

    @FXML
    void ModifierU(ActionEvent event) {
        String validationError = validateInputs();
        try {
            LocalDate selectedDate = dateTF.getValue();
            java.sql.Date dateToSet = null;
            if (selectedDate != null) {
                dateToSet = java.sql.Date.valueOf(selectedDate);
            }

            User userToUpdate = new User(Integer.parseInt(idTF.getText()),nomTF.getText(),prenomTF.getText(),usernameTF.getText(),dateToSet,emailTF.getText(),pwdTF.getText(),roleTF.getValue(),statusTF.getValue());

            switch (levelTF.getValue()) {
                case DEBUTANT -> {userToUpdate.setLevelId(1);}
                case INTERMEDIAIRE -> {userToUpdate.setLevelId(2);}
                case AVANCE -> {userToUpdate.setLevelId(3);}
            }

            if (validationError.isEmpty()) {
            userService.modifier(userToUpdate);
            showAlertSuccess("Sucess","user modified","user modified succesfully ");}
            else{
                showAlertError("Validation Error","",validationError);
             }

        }
        catch (SQLException e){
            showAlertError("ERROR","",e.getMessage());
        }

    }
    public void setText(User user)
    {
        ObservableList<UserLevel> userLevels = FXCollections.observableArrayList(UserLevel.values());
ObservableList<String> statusList = FXCollections.observableArrayList("Validated", "Not Validated");
        ObservableList<UserRole> userRoles = FXCollections.observableArrayList(UserRole.values());
       roleTF.setItems(userRoles);
       levelTF.setItems(userLevels);
       statusTF.setItems(statusList);
        String id =String.valueOf(user.getId());
        idTF.setText(id);
        nomTF.setText(user.getNom());
        prenomTF.setText(user.getPrenom());
        emailTF.setText(user.getEmail());
        pwdTF.setText(user.getPassword());
        statusTF.setValue(user.getStatus());
        roleTF.setValue(user.getRole());
        usernameTF.setText(user.getUsername());
        switch (user.getLevelId()) {
            case 1 -> levelTF.setValue(UserLevel.DEBUTANT);
            case 2 -> levelTF.setValue(UserLevel.INTERMEDIAIRE);
            case 3 -> levelTF.setValue(UserLevel.AVANCE);
            case 0 -> levelTF.setValue(UserLevel.NULL);
        }
           if(user.getDateNaissance()!=null){dateTF.setValue(user.getDateNaissance().toLocalDate());}


    }

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
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = dateTF.getValue();
        if (selectedDate == null || Period.between(selectedDate, currentDate).getYears() < 12) {
            validationError.append("Please select a valid date of birth (user must be at least 12 years old).\n");
        }

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
