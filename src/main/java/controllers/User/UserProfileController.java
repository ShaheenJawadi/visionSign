package controllers.User;

import entities.User;
import entities.UserLevel;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
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
    private TextField emailTF;
    @FXML
    private TextField pwdTF;

    @FXML
    private TextField usernameTF;
    private final UserService userService=new UserService();
    @FXML
    void initialize(){
        ObservableList<UserLevel> userLevels = FXCollections.observableArrayList(UserLevel.values());

        //ObservableList<String> levels = FXCollections.observableArrayList("1", "2", "3");
        levelTF.setItems(userLevels);
        levelTF.setValue(UserLevel.DEBUTANT);
        User currentUser = userService.getCurrent();
        System.out.println("current user ID "+currentUser.getId());

        System.out.println("current user "+currentUser.getNom());
        nomTF.setText(currentUser.getNom());
        prenomTF.setText(currentUser.getPrenom());
        usernameTF.setText(currentUser.getUsername());
        //emailTF.setText(currentUser.getEmail());

        System.out.println("emaaaaaaaaail"+currentUser.getEmail());
        emailTF.setText(currentUser.getEmail());
        System.out.println(currentUser.getDateNaissance());
        //dateTF.setValue(currentUser.getDateNaissance().toLocalDate());

        //levelTF.setValue(currentUser.getLevelId());
    }
    @FXML
    void modifier(ActionEvent event) {

        try {
            LocalDate selectedDate = dateTF.getValue();
            java.sql.Date dateToSet = null;
            if (selectedDate != null) {
                dateToSet = java.sql.Date.valueOf(selectedDate);
            }
            System.out.println("currreeent useeer"+userService.getCurrent().getId());

            User userToUpdate = new User(userService.getCurrent().getId(),nomTF.getText(),prenomTF.getText(),usernameTF.getText(),dateToSet,emailTF.getText(),pwdTF.getText(),userService.getCurrent().getRole(),userService.getCurrent().getStatus());
            System.out.println("Useerr to updateee"+userToUpdate.getId());
            System.out.println("neww datee"+userToUpdate.getDateNaissance());
            switch (levelTF.getText()){
                case "DEBUTANT"   :
                    userToUpdate.setLevelId(1);
                    System.out.println("dd");
                    break;
                case "INTERMEDIAIRE"   :
                    System.out.println("II");
                    userToUpdate.setLevelId(2);
                    break;

                case "AVANCE"   :
                    System.out.println("22");
                    userToUpdate.setLevelId(3);
                    break;
            }
            System.out.println("hgghfhghg "+userToUpdate);

            userService.modifier(userToUpdate);

            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("sucess");
            alert.setContentText("person modifed succesfully");
            alert.showAndWait();

        }
        catch (SQLException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}
