package controllers.teacher;

import entities.Cours;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import services.cours.CoursService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CoursGridPane  implements Initializable {
    private @FXML FlowPane  coursGridList ;
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
            controller.putData(cours ,
                    cours.getNom(),
                    "kqs",
                    String.valueOf(cours.getNiveauId()),
                    "kqs",
                    "kqs",
                    "kqs");
            controller.setParentController(this);

            coursGridList.getChildren().add(customView);
        }



        } catch (IOException e) {
            throw new RuntimeException(e);
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
