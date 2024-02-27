package controllers.teacher;

import entities.Cours;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import services.cours.CoursService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CoursGridPane  implements Initializable {
    private @FXML FlowPane  coursGridList ;
    private  @FXML AnchorPane coursGridHolder;
    private StackPane spSubScene;
    public void setStackPane(StackPane stackPane) {
        this.spSubScene = stackPane;
    }


    public AnchorPane getCoursGridHolder() {
        return coursGridHolder;
    }

    @FXML
    public void renderDataCours() throws SQLException {
        System.out.println("render");
        coursGridList.getChildren().clear();
        CoursService coursService=new CoursService();
        List<Cours> coursList =coursService.getAll();
        try {
        for (Cours cours : coursList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/cours/list/CoursItem.fxml"));

            VBox customView = loader.load();

            CoursItemController controller = loader.getController();
            controller.putData(cours);
            controller.setParentController(this);

            coursGridList.getChildren().add(customView);
        }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public  void openAddCoursPageBtn(Cours cours)   {
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/cours/create/NewCours.fxml"));

            loader.load();

            ManageCoursController addCoursPage = loader.getController();
            addCoursPage.setStackPane(spSubScene);
            addCoursPage.setCours(cours);
            spSubScene.getChildren().clear();
            spSubScene.getChildren().add(addCoursPage.getVBoxRoot());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }





    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            renderDataCours();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
