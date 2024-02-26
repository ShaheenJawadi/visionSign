package controllers.Reclamations;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Reclamations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Reclamations.ReclamationsServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ModifierRecController {
    @FXML
    public AnchorPane listepubid;
    List<Reclamations> mypub;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField descriptionField, statusField;
    @FXML
    private Button updateButton;
    @FXML
    private Button forumBtn;
    private Reclamations reclamations;
    private ReclamationsServices reclamationsServices;

    public void initialize() {
        // Initialize services
        reclamationsServices = new ReclamationsServices();
        pubs = new ReclamationsServices();
        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll(Arrays.asList(ReclamationsServices.TYPES));

        // Load details of the complaint to be modified
        loadComplaintDetails();
    }

    public void setReclamation(Reclamations reclamation) {
        this.reclamations = reclamation;
    }

    private void loadComplaintDetails() {
        // Check if complaint is not null
        if (reclamations != null) {
            // Set details to fields
            typeComboBox.setValue(reclamations.getType());
            descriptionField.setText(reclamations.getDescription());
            statusField.setText(reclamations.getStatus());
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        // Get updated details from fields
        String type = typeComboBox.getValue();
        String description = descriptionField.getText();
        String status = statusField.getText();

        try {
            // Update the complaint
            reclamations.setType(type);
            reclamations.setDescription(description);
            reclamations.setStatus(status);

            reclamationsServices.modifier(reclamations);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Complaint updated successfully");
            alert.showAndWait();
        } catch (SQLException e) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error updating complaint: " + e.getMessage());
            alert.showAndWait();
        }
    }


    public void searchPubByTitle1(ActionEvent actionEvent) {
    }

    public void handleForum(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamtions/AjouterRec.fxml"));
            Parent root = loader.load();
            forumBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private int pubId;
    ReclamationsServices pubs = new ReclamationsServices();

    public void setPubId(int pubId) {
        this.pubId = pubId;
        loadPublicationDetails();
    }
    private void loadPublicationDetails() {
        System.out.println("Loading details for pubId: " + pubId);
        try {
            String typeReclamation = typeComboBox.getValue();

            Reclamations getPub = pubs.getReclmationsById(pubId);
            if (getPub != null) {

                descriptionField.setText(getPub.getDescription());
                statusField.setText(getPub.getStatus());
                System.out.println("Loaded publication: " + getPub);
            } else {
                System.out.println("No publication found with ID: " + pubId);
            }
        } catch (SQLException e) {
            System.err.println("SQL error when trying to load publication details: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error when trying to load publication details: " + e.getMessage());
            e.printStackTrace();
        }
    }
private Pane createPublicationPane(Reclamations publication, int index) {
        Pane pane = new Pane();
        pane.setPrefSize(271, 75);
        pane.setLayoutX(-1);
        pane.setLayoutY(7 + index * (75));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");

        Text titreText = new Text(publication.getType());
        titreText.setLayoutX(14);
        titreText.setLayoutY(28);
        titreText.setFont(new Font("System Bold", 12));
        titreText.setFill(Color.web("#494949"));

        Text dateText = new Text(publication.getDate().toString());
        dateText.setLayoutX(14);
        dateText.setLayoutY(54);
        dateText.setFill(Color.web("#a5a5a5"));


        Button modifyButton = new Button();
        modifyButton.setLayoutX(202);
        modifyButton.setLayoutY(37);
        modifyButton.setPrefSize(28, 25);
        modifyButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        modifyIcon.setFill(Color.web("#2447f9"));
        modifyIcon.setSize("15");
        modifyButton.setGraphic(modifyIcon);
        modifyButton.setCursor(Cursor.HAND);
        modifyButton.setOnAction(new EventHandler<ActionEvent>() {
@Override
public void handle(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyAvis.fxml"));
        Parent root = loader.load();
        ModifierRecController modifyController = loader.getController();
        modifyController.setPubId(mypub.get(index).getId_reclamation());
        System.out.println(mypub.get(index).getId_reclamation());
        forumBtn.getScene().setRoot(root);
        } catch (IOException e) {
        e.printStackTrace();
        }
        }
        });

        Button deleteButton = new Button();
        deleteButton.setLayoutX(228);
        deleteButton.setLayoutY(37);
        deleteButton.setPrefSize(28, 25);
        deleteButton.setStyle("-fx-background-color: #FFFFFF;");
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        deleteIcon.setFill(Color.web("#2447f9"));
        deleteIcon.setSize("15");
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
@Override
public void handle(ActionEvent event) {
        try {
        pubs.supprimer(mypub.get(index).getId_reclamation());
        System.out.println("deleted!");
        listepubid.getChildren().remove(pane);
        mypub.remove(index);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes!");
        alert.setContentText("Publication supprimé!");
        alert.showAndWait();
        } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        }
        }
        });

        pane.getChildren().addAll(titreText, dateText, modifyButton, deleteButton);


        return pane;
        }

    public void ajouter(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {
    }
}