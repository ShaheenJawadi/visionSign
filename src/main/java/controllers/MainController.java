package controllers;
import State.MainNavigations;
import State.UserOPState;
import State.UserSessionManager;
import entities.UserRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

    @FXML
    private HBox unAuthBox;
    @FXML
    private HBox authBox;
    @FXML
    private ImageView userImage;

    @FXML
    private Text userName;
    @FXML
    private MenuItem dashboardBtnAcc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        UserSessionManager.getInstance();


        UserOPState userOPState = UserOPState.getInstance() ;
        userOPState.setNbEnrolementTextView(nbEnrolledCours);
        MainNavigations mainNavigations = MainNavigations.getInstance() ;
        mainNavigations.setPaheHoloder(mainPageHolder);
        mainNavigations.setAuthComponents(authBox ,unAuthBox , userName , userImage,dashboardBtnAcc);

        mainNavigations.manageHeaderAuth();




    }

    public void OpenListCoursPage(MouseEvent mouseEvent) {
        MainNavigations.getInstance().openCoursFilterPage();
    }

    public void openHomePage(MouseEvent mouseEvent) {
        MainNavigations.getInstance().openMainHomePage();

    }

    public void OpenForumPage(MouseEvent mouseEvent) {
        MainNavigations.getInstance().openForumPage();

    }

@FXML
    public void OpenReclamationPage(MouseEvent mouseEvent) {
        MainNavigations.getInstance().openReclamationPage();

    }

    @FXML
    void openSignIn(MouseEvent event) {
        MainNavigations.getInstance().openSignIn();

    }

    @FXML
    void openSignUp(MouseEvent event) {

        MainNavigations.getInstance().openSignUp();

    }

    @FXML
    void logoutBtn(ActionEvent event) {
        UserSessionManager.getInstance().clearSession();
        MainNavigations.getInstance().manageHeaderAuth();
        MainNavigations.getInstance().openMainHomePage();
    }



    @FXML
    void openProfileUser(ActionEvent event) {

            MainNavigations.getInstance().openUserProfile();

    }

    @FXML
    void openDashboard(ActionEvent event) {

            MainNavigations.getInstance().openAdminProfile();


    }


}
