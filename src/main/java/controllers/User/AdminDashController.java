package controllers.User;

import entities.User;
import entities.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.User.UserService;

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

    private final UserService userService=new UserService();

    @FXML
    void initialize() {
        try{
            List<User> users=userService.recuperer();
            ObservableList<User> observableList= FXCollections.observableList(users);
            tableView.setItems(observableList);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
            roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            tokenCol.setCellValueFactory(new PropertyValueFactory<>("token"));
            levelIdCol.setCellValueFactory(new PropertyValueFactory<>("level_id"));

        }catch(SQLException e){System.err.println(e.getMessage()); }
    }
    @FXML
    void delete(){}

}


