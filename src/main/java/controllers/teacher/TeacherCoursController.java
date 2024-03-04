package controllers.teacher;

import State.TeacherNavigations;
import entities.Cours;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import services.cours.CoursService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherCoursController implements Initializable{

    private @FXML VBox vbRoot;

    private  @FXML VBox gridHolder ;


    @FXML
    private TextFlow succMsg;

    private boolean showMessage ;

    private @FXML FlowPane coursGridList ;
    private  @FXML AnchorPane coursGridHolder;


    public boolean isShowMessage() {
        return showMessage;
    }

    public void setShowMessage(boolean showMessage) {
        this.showMessage = showMessage;
    }

    public VBox getVBoxRoot()
    {
        return vbRoot;
    }



    @FXML
    public  void openAddCoursPageBtn()   {
        TeacherNavigations.getInstance().openAddCoursPage();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/cours/list/CoursItem.fxml"));

                VBox customView = loader.load();

                CoursItemController controller = loader.getController();
                controller.putData(cours);
       //         controller.setParentController(this); TODO

                coursGridList.getChildren().add(customView);
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public  void openAddCoursPageBtn(Cours cours)   {
        TeacherNavigations.getInstance().openAddCoursPage();





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
