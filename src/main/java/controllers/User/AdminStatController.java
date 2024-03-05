package controllers.User;


import entities.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import services.User.UserService;
import State.UserSessionManager;

import java.io.IOException;
import java.util.Map;

public class AdminStatController {



    @FXML
    private PieChart pieId;


    private final UserService userService=new UserService();
    private final UserSessionManager userSessionManager=new UserSessionManager();


    @FXML
    private AnchorPane rootId;

    public AnchorPane getRootId() {
        return  this.rootId;
    }

    @FXML
    void initialize() {

        // Assume you have a method to fetch user statistics, e.g., getRoleStatistics()
        Map<UserRole, Long> roleStatistics = userService.getRoleStatistics();

        // Create PieChart.Data objects
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        roleStatistics.forEach((userRole, count) -> {
            pieChartData.add(new PieChart.Data(userRole.toString(), count));
        });

        // Set the data to the PieChart
        pieId.setData(pieChartData);
/*
        Map<String, Long> ageStatistics = userService.getAgeStatistics();

        // Create BarChart.Series
        BarChart.Series<String, Number> ageSeries = new BarChart.Series<>();
        ageSeries.setName("Age Distribution");

        ageStatistics.forEach((ageRange, count) -> {
            ageSeries.getData().add(new BarChart.Data<>(ageRange, count));
        });

        // Set the data to the BarChart
        barId.getData().add(ageSeries);

        // Set axis labels
        xAxis.setLabel("Age Range");
        yAxis.setLabel("Number of Users");*/

    }



}
