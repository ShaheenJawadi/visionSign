package controllers.teacher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CoursGridPane  implements Initializable {
    private @FXML FlowPane  coursGridList ;
    @FXML
    public void renderDataCours()  {

        try {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 5; j++) {


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/cours/list/CoursItem.fxml"));

                    VBox customView = loader.load();

                    CoursItemController controller = loader.getController();


                    coursGridList.getChildren().add(customView);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        renderDataCours();



    }
}
