package controllers.MainPages.Cours;

import Navigation.MainNavigations;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class FilterController {


    @FXML
    private VBox FilterPageVbox;


    public VBox getVBoxRoot()
    {
        return FilterPageVbox;
    }

    public void openSingleCours(MouseEvent mouseEvent) {

        System.out.println("jsd");
        MainNavigations mainNavigations = MainNavigations.getInstance() ;
        mainNavigations.openSingleCoursPage();
    }
}
