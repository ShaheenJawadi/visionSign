package State;

import controllers.MainPages.Cours.CoursLessonsPageController;
import controllers.MainPages.Cours.Filter.FilterController;
import controllers.MainPages.Cours.SingleCoursController;
import controllers.MainPages.MainPageController;
import controllers.Student.QuizEleveController;
import controllers.User.*;
import controllers.studentForum.*;
import entities.Cours;
import entities.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainNavigations {
    private static MainNavigations instance;


    @FXML
    private VBox mainPageHolder;

    @FXML
    private HBox authBox ;
    @FXML
    private  HBox unauthBox ;


    @FXML
    private ImageView userImage ;
    @FXML
    private Text userName ;
    @FXML
    private MenuItem dashboardBtnAcc;

    public void setAuthComponents(HBox authBox , HBox unauthBox , Text userName , ImageView userImage, MenuItem dashboardBtnAcc){
        this.authBox = authBox;
        this.unauthBox = unauthBox;
        this.userName = userName ;
        this.userImage = userImage ;
        this.dashboardBtnAcc=dashboardBtnAcc ;
    }

    private MainNavigations() {

    }

    public static MainNavigations getInstance() {
        if (instance == null) {
            instance = new MainNavigations();
        }
        return instance;
    }



    public  void setPaheHoloder(VBox vBox){
        mainPageHolder =vBox;

    }


    public void openCoursFilterPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Filter/index.fxml"));

            loader.load();

            FilterController coursFilterPage = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(coursFilterPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void openSingleCoursPage(Cours cours){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Main/index.fxml"));

            loader.load();

            SingleCoursController coursFilterPage = loader.getController();
             coursFilterPage.renderContent(cours);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(coursFilterPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void openMainHomePage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/HomePage/Componants.fxml"));

            loader.load();

            MainPageController page = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }



    public void openCoursLessonPage(Cours cours){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Lesson/index.fxml"));

            loader.load();

            CoursLessonsPageController coursLessonPage = loader.getController();

            coursLessonPage.setCours(cours);
            coursLessonPage.renderCoursLessons();




            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(coursLessonPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void openQuizzPage(int coursId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Student/QuizEleve.fxml"));

            loader.load();

            QuizEleveController page = loader.getController();
            page.setCoursId(coursId);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }




    public void openForumPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));

            loader.load();

            ForumGetAllPublicationsController page = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }




    public void openChatbotForumPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ChatBot.fxml"));

            loader.load();

            ChatBotController page = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }



    public void openAddPublicationForumPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/AddPublication.fxml"));

            loader.load();

            AddPublicationController page = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }



    public void openModifyPublicationForumPage(int pubId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ModifyPublication.fxml"));

            loader.load();

            ModifyPublicationController page = loader.getController();
            page.setPubId(pubId);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void openPublicationDetailsForumPage(int pubId){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/PublicationDetails.fxml"));

            loader.load();

            PublicationDetailsController page = loader.getController();
            page.setPubId(pubId);



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void openSignIn(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));

            loader.load();

            LoginController page = loader.getController();


            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }



    public void openSignUp(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/SignUp.fxml"));

            loader.load();

            SignUpController page = loader.getController();


            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootBox());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }





    public void openUserProfile(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserSettingPage.fxml"));

            loader.load();

            UserSettingController page = loader.getController();


            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootId());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }



    public void openAdminProfile(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AdminSettingPage.fxml"));

            loader.load();

            AdminSettingsController page = loader.getController();


            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(page.getRootId());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }











    ///////////////////////////
    public  void authHeaderSetup(){
        authBox.setVisible(true);
        unauthBox.setVisible(false);
        userName.setText(UserSessionManager.getInstance().getCurrentUser().getUsername());
        //TODO IMAGE   userName.setText(UserSessionManager.getInstance().getCurrentUser().getUsername());

    }

    public  void unAuthHeaderSetup(){

            authBox.setVisible(false);
            unauthBox.setVisible(true);


    }

    public void manageHeaderAuth(){
        if(UserSessionManager.getInstance().isUserLoggedIn()) {
            authHeaderSetup();
            showDachBtn(UserSessionManager.getInstance().getCurrentUser().getRole()== UserRole.ADMIN);

        }
        else {
            unAuthHeaderSetup();
        }
    }

    public  void showDachBtn(boolean b){
        dashboardBtnAcc.setVisible(b);
    }


}