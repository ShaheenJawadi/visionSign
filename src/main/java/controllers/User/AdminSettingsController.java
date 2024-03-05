package controllers.User;


import State.UserSessionManager;
import entities.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.User.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminSettingsController implements Initializable {




    @FXML
    private VBox pagesHolder;

    @FXML
    private VBox rootId;
    @FXML
    private  Button listButton ;


    public VBox getRootId() {
        return  this.rootId;
    }
    @FXML
    void listButton(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AdminDash.fxml"));
            loader.load();
            AdminDashController dashController = loader.getController();

            pagesHolder.getChildren().clear();
            pagesHolder.getChildren().add(dashController.getRootId());
            }
        catch(IOException e)
        {
            System.out.println(e);

            throw new RuntimeException(e.getMessage());}

    }


    @FXML
    private void statButton(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AdminStat.fxml"));
            loader.load();
            AdminStatController dashController = loader.getController();

            pagesHolder.getChildren().clear();
            pagesHolder.getChildren().add(dashController.getRootId());
        }
        catch(IOException e)
        {throw new RuntimeException(e.getMessage());}

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listButton.fire();

    }
}
