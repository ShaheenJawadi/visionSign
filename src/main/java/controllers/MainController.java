package controllers;
import State.MainNavigations;
import State.UserOPState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox mainPageHolder;


    @FXML
    private Button donnationBtn;
    @FXML
    private Text nbEnrolledCours;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        UserOPState userOPState = UserOPState.getInstance() ;
        userOPState.setNbEnrolementTextView(nbEnrolledCours);
        MainNavigations mainNavigations = MainNavigations.getInstance() ;
        mainNavigations.setPaheHoloder(mainPageHolder);


    }

    public void OpenListCoursPage(MouseEvent mouseEvent) {
        MainNavigations.getInstance().openCoursFilterPage();
    }

    public void openHomePage(MouseEvent mouseEvent) {
        MainNavigations.getInstance().openMainHomePage();

    }
}
