package controllers.User;

import apiUtils.MailerAPI;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import entities.User;
import services.User.PasswordHashing;
import services.User.UserService;

import java.sql.SQLException;
import java.util.List;

public class ForgetPwdController{

    @FXML
    private TextField codeTF;

    @FXML
    private TextField emailTF;

    @FXML
    private MFXPasswordField pwdTF;

    @FXML
    private MFXPasswordField pwdTF1;
    private final UserService userService=new UserService();

    //private final MailerAPI mailer=new MailerAPI();

    @FXML
    void changePwd(ActionEvent event) {
        String validationError = validateInputs();
        String userEmail = emailTF.getText();

        try {
            // Fetch the user from the database using the email
            List<User> searchResults = userService.searchByEmail(userEmail);

            if (!searchResults.isEmpty()) {
                User user = searchResults.get(0); // Assuming there's only one user with a given email

                // Check if the recovery code matches
                if (codeTF.getText().equals(MailerAPI.getCode())) {
                    if (pwdTF.getText().equals(pwdTF1.getText())) {
                        if (validationError.isEmpty()) {
                            // Update the user's password in the database
                            user.setPassword( pwdTF1.getText());
                            userService.updatePassword(user);
                            showAlertSuccess("Success", "Password Changed", "Password changed successfully.");
                        } else {
                            showAlertError("Validation Error", "", validationError);
                        }
                    } else {
                        showAlertError("Error", "Password Mismatch", "Passwords don't match.");
                    }
                } else {
                    showAlertError("Error", "Incorrect Code", "Recovery code is incorrect.");
                }
            } else {
                showAlertError("Error", "User Not Found", "User with email " + userEmail + " not found.");
            }
        } catch (SQLException e) {
            showAlertError("Error", "", e.getMessage());
        }

    }

    @FXML
    void codeButton() {
        String email = emailTF.getText().trim();

        if (email.isEmpty()) {
            showAlert("Error", "Please enter your email address");
            return;
        }

        try {
            List<User> searchResults = userService.searchByEmail(email);

            if (!searchResults.isEmpty()) {
                // Email exists in the database, send recovery email
                MailerAPI.sendMail("VisionSignAcademy@outlook.com","Azerty123!",email);
                showAlertSuccess("Success", "","Recovery email sent successfully");
            } else {
                showAlertError("Error", "","Email not found in the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlertError("Error", "","An error occurred while searching for email: " + e.getMessage());
        }
    }
    private String validateInputs() {
        StringBuilder validationError = new StringBuilder();
        String password = pwdTF.getText();
        if (password.length() < 8 ||
                !password.matches(".*[a-z].*") ||  // At least one lowercase letter
                !password.matches(".*[A-Z].*") ||  // At least one uppercase letter
                !password.matches(".*\\d.*")) {    // At least one digit
            validationError.append("Password should have at least 8 characters, including at least one uppercase letter, one lowercase letter, and one number.\n");
        }
        return validationError.toString();

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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