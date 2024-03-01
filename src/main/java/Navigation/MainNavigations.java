package Navigation;

import controllers.MainPages.Cours.FilterController;
import controllers.MainPages.Cours.SingleCoursController;
import controllers.teacher.TeacherCoursController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainNavigations {
    private static MainNavigations instance;


    @FXML
    private VBox mainPageHolder;


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

    public void openSingleCoursPage(){
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Main/index.fxml"));

            loader.load();

            SingleCoursController coursFilterPage = loader.getController();



            mainPageHolder.getChildren().clear();
            mainPageHolder.getChildren().add(coursFilterPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }








}