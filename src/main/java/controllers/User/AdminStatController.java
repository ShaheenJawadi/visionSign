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
import state.UserSessionManager;

import java.io.IOException;
import java.util.Map;

public class AdminStatController {
    @FXML
    private Button listButton;

    @FXML
    private AnchorPane table;

    @FXML
    private Label userTF;
    @FXML
    private PieChart pieId;
    @FXML
    private BarChart<String, Number> barId;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    private final UserService userService=new UserService();
    private final UserSessionManager userSessionManager=new UserSessionManager();

    @FXML
    void initialize() {
        userTF.setText(userService.getCurrent().getUsername());
        barId.setVisible(false);
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

    @FXML
    void listButton(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AdminDash.fxml"));
            Parent tableRoot = loader.load();
            ScrollPane dashboardScrollPane=new ScrollPane(tableRoot);
            dashboardScrollPane.setFitToWidth(true);
            dashboardScrollPane.setFitToHeight(true);
            userTF.getScene().setRoot(dashboardScrollPane);}
        catch(IOException e)
        {throw new RuntimeException(e.getMessage());}

    }
    @FXML
    void logout(ActionEvent event){
        userSessionManager.clearSession();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent privacyRoot = loader.load();
            ScrollPane privacyScrollPane=new ScrollPane(privacyRoot);
            privacyScrollPane.setFitToWidth(true);
            privacyScrollPane.setFitToHeight(true);
            barId.getScene().setRoot(privacyScrollPane);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
