package controllers.MainPages;

import Navigation.MainNavigations;
import controllers.MainPages.Cours.CoursGridController;
import controllers.MainPages.Cours.SingleCoursController;
import controllers.teacher.CoursItemController;
import entities.Cours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import services.cours.CoursService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {


    @FXML
    private FlowPane listCoursHolder ;




    public void OpenListCoursPage(ActionEvent actionEvent) {
        System.out.println("jdhfjsjdk");
        MainNavigations mainNavigations = MainNavigations.getInstance() ;
         mainNavigations.openCoursFilterPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            if(listCoursHolder != null)
                showCoursList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public  void showCoursList () throws SQLException {

        System.out.println("render");
        listCoursHolder.getChildren().clear();
        CoursService coursService=new CoursService();
        List<Cours> coursList =coursService.getAll();
        try {
            for (Cours cours : coursList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/Components/Cours/SingleGrid.fxml"));

                VBox customView = loader.load();

                CoursGridController controller = loader.getController();
                controller.renderGrid(cours);


                listCoursHolder.getChildren().add(customView);
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
