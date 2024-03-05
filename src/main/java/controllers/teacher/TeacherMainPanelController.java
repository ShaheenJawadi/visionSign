package controllers.teacher;

import State.MainNavigations;
import State.TeacherNavigations;
import State.UserOPState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherMainPanelController  implements Initializable {


    private @FXML VBox spSubScene;
    private  @FXML Button dashboardBtnId ;

    private @FXML VBox teacherHolder ;



    public VBox getRootId() {
        return this.teacherHolder;
    }
    public void IntitalState(){

      dashboardBtnId.fire();
    }


    public void openDashboard(){

        TeacherNavigations.getInstance().openDashboardPage();

    }

    public void openCoursPage(){
        TeacherNavigations.getInstance().openCoursListPage();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        UserOPState userOPState = UserOPState.getInstance() ;

        TeacherNavigations mainNavigations = TeacherNavigations.getInstance() ;
        mainNavigations.setPaheHoloder(spSubScene);


        IntitalState();

    }



}
