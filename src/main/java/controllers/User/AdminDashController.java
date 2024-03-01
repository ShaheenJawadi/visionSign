package controllers.User;

import entities.User;
import entities.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.User.UserService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AdminDashController {
    @FXML
    private TableColumn<User, String> nomCol;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Date> dateCol;


    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, Integer> idCol;

    @FXML
    private TableColumn<User, Integer> levelIdCol;

    @FXML
    private TableColumn<User, String> passwordCol;

    @FXML
    private TableColumn<User, String> prenomCol;

    @FXML
    private TableColumn<User, UserRole> roleCol;

    @FXML
    private TableColumn<User, String> statusCol;

    @FXML
    private TableColumn<User, String> tokenCol;

    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private Label userTF;

    private final UserService userService=new UserService();

    @FXML
    void initialize() {
        try{
            userTF.setText(userService.getCurrent().getUsername());
            List<User> users=userService.recuperer();
            ObservableList<User> observableList= FXCollections.observableList(users);
            tableView.setItems(observableList);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
            roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            tokenCol.setCellValueFactory(new PropertyValueFactory<>("token"));
            levelIdCol.setCellValueFactory(new PropertyValueFactory<>("levelId"));

        }catch(SQLException e){System.err.println(e.getMessage()); }
    }

    @FXML
    void delete(){
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser!=null){

        try {
            userService.supprimer(selectedUser.getId());
            tableView.getItems().remove(selectedUser);
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("sucess");
            alert.setContentText("person deleted succesfully");
            alert.showAndWait();

        }
        catch (SQLException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        }else{
            // If no user is selected, show a warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a user to delete");
            alert.showAndWait();
        }

    }
    @FXML
    void modify(ActionEvent event){
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser!=null){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/User/ModifierUser.fxml"));
        try{
            loader.load();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        ModifierUserController muc=loader.getController();
        muc.setText(selectedUser);
        Parent parent=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        initialize();

    }
        else{
            // If no user is selected, show a warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a user to modify");
            alert.showAndWait();
        }
    }
    @FXML
    void refresh(ActionEvent event) {
    try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AdminDash.fxml"));
        Parent tableRoot = loader.load();
        ScrollPane dashboardScrollPane=new ScrollPane(tableRoot);
        dashboardScrollPane.setFitToWidth(true);
        dashboardScrollPane.setFitToHeight(true);
        tableView.getScene().setRoot(dashboardScrollPane);}
    catch(IOException e)
    {throw new RuntimeException(e.getMessage());}
    }}


