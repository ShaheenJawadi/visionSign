package controllers.User;

import apiUtils.PdfGenerator;
import entities.User;
import entities.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.User.UserService;
import state.UserSessionManager;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AdminDashController {
    @FXML
    public ImageView imageId;
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
    @FXML
    private TextField searchTF;
    @FXML
    private ComboBox<String> comboId;


    private final UserService userService=new UserService();

    private FilteredList<User> filteredList;
    private final UserSessionManager userSessionManager=new UserSessionManager();

    @FXML
    void initialize() {
        User user=userService.getCurrent();
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

        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call the search method whenever the text changes
            dynamicSearch(newValue);
        });
        filteredList = new FilteredList<>(tableView.getItems(), p -> true);
        initializeSearchTypeComboBox();
        String url = user.getImage();
        if(!(url ==null)){
            Image image = new Image(url);
            imageId.setImage(image);
        }
        else{

        }
        Image image=new Image("assets/user/UserDefault.png");
        imageId.setImage(image);

    }
    @FXML
    private void initializeSearchTypeComboBox() {
        // Define the search types
        ObservableList<String> searchTypes = FXCollections.observableArrayList("ID", "Email");

        // Set the items for the searchTypeComboBox
        comboId.setItems(searchTypes);
        // Set a default value (you can set it to "ID" or "Email" based on your preference)
        comboId.setValue("ID");
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
    }


    @FXML
    private void dynamicSearch(String searchText) {
        // Perform the search only if the searchText is not empty
        if (!searchText.isEmpty()) {
            try {
                List<User> searchResults;

                // Check the selected value in the ComboBox
                String searchType = comboId.getValue();

                if ("ID".equals(searchType) && searchText.matches("\\d+")) {
                    // If "ID" is selected and input is numeric, search by user ID
                    int userId = Integer.parseInt(searchText);
                    searchResults = userService.searchById(userId);
                } else if ("Email".equals(searchType)) {
                    // If "Email" is selected, search by email
                    searchResults = userService.searchByEmail(searchText);
                } else {
                    // If the selected value is neither "ID" nor "Email," do nothing
                    return;
                }

                updateFilteredList(searchResults, searchText);
            } catch (NumberFormatException e) {
                System.err.println("Invalid ID format: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Error searching users: " + e.getMessage());
            }
        } else {
            // If the search text is empty, refresh the table with all users
            initialize();
        }
    }
    @FXML
    private void updateFilteredList(List<User> searchResults, String searchText) {
        // Update the FilteredList predicate based on the current input
        filteredList.setPredicate(user -> {
            String searchType = comboId.getValue();

            if ("ID".equals(searchType)) {
                // If searching by ID, check for a match in the user ID
                try {
                    int searchId = Integer.parseInt(searchText);
                    return user.getId() == searchId;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid ID format: " + e.getMessage());
                    return false;
                }
            } else if ("Email".equals(searchType)) {
                // If searching by Email, check for a match in the email field
                return user.getEmail().toLowerCase().contains(searchText.toLowerCase());
            }

            // Default to not matching if the selected type is neither "ID" nor "Email"
            return false;
        });

        // Update the tableView with the filtered results
        tableView.setItems(filteredList);
    }
    @FXML
    private void pdf(ActionEvent event){
        try {
            List<User> userList = userService.recuperer(); // Fetch your list of users
            String outputPath = "src/main/resources/pdf/output.pdf";
            PdfGenerator.generatePdf(userList, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void statButton(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AdminStat.fxml"));
            Parent tableRoot = loader.load();
            ScrollPane dashboardScrollPane=new ScrollPane(tableRoot);
            dashboardScrollPane.setFitToWidth(true);
            dashboardScrollPane.setFitToHeight(true);
            tableView.getScene().setRoot(dashboardScrollPane);}
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
            tableView.getScene().setRoot(privacyScrollPane);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @FXML
    public void profile(javafx.scene.input.MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/User/UserProfile.fxml"));
        try {
            loader.load();
           // UploadImageController uploadImageController = loader.getController();
            // Set a reference to this UserProfileController
            //uploadImageController.setUserProfileController(this);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

    }

}




