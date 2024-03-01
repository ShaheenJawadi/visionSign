package controllers;
import Navigation.MainNavigations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox mainPageHolder;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        MainNavigations mainNavigations = MainNavigations.getInstance() ;
        mainNavigations.setPaheHoloder(mainPageHolder);

    }
}
