package controllers.Reclamations;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Reclamations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Reclamations.ReclamationsServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherRecU implements Initializable{


    @FXML
    public AnchorPane listepubid, allpubid;

    @FXML
    private Button addBtn,forumBtn;
    @FXML
    private TextField searchField;
    List<Reclamations> mypub;
    List<Reclamations> allPub;
    ReclamationsServices pubs = new ReclamationsServices();


    public void initialize(URL url, ResourceBundle rb) {
        refreshPublications();
        try {
            //TODO userId=1

            mypub = pubs.getReclamationsById(1);
            System.out.println(mypub);
            mypub.sort(Comparator.comparing(Reclamations::getDate).reversed());

            if (mypub.isEmpty()) {
                Text emptyText = new Text("Vous n'avez pas encore publié!");
                emptyText.setFont(new Font("System", 15));
                emptyText.setFill(Color.GRAY);
                emptyText.setLayoutX(19);
                emptyText.setLayoutY(172);
                listepubid.getChildren().add(emptyText);
            } else {
                for (int i = 0; i < mypub.size(); i++) {
                    Pane pane = createPublicationPane(mypub.get(i), i, false);
                    listepubid.getChildren().add(pane);
                }
            }
            listepubid.setPrefHeight(mypub.isEmpty() ? 100 : mypub.size() * 85);

            allPub = pubs.recuperer();
            allPub.sort(Comparator.comparing(Reclamations::getDate).reversed());
            allpubid.getChildren().clear();

            if (allPub.isEmpty()) {
                Text emptyText = new Text("Aucune publication n'a été publiée.");
                emptyText.setFont(new Font("System", 16));
                emptyText.setFill(Color.GRAY);
                emptyText.setLayoutX(10);
                emptyText.setLayoutY(50);
                allpubid.getChildren().add(emptyText);
            } else {
                for (int i = 0; i < allPub.size(); i++) {
                    Pane pane = createPublicationPane(allPub.get(i), i, true);
                    allpubid.getChildren().add(pane);
                }
            }
            allpubid.setPrefHeight(allPub.isEmpty() ? 100 : allPub.size() * 165);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


    private Pane createPublicationPane(Reclamations publication, int index, boolean isAllPublications) {
        Pane pane = new Pane();
        pane.setPrefSize(isAllPublications ? 682 : 271, isAllPublications ? 158 : 75);
        pane.setLayoutX(isAllPublications ? 6 : -1);
        pane.setLayoutY(7 + index * (isAllPublications ? 165 : 75));
        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");

        Text titreText = new Text(publication.getType());
        titreText.setLayoutX(14);
        titreText.setLayoutY(isAllPublications ? 24 : 28);
        titreText.setFont(new Font("System Bold", isAllPublications ? 16 : 12));
        titreText.setFill(Color.web("#494949"));

        Text dateText = new Text(publication.getDate().toString());
        dateText.setLayoutX(isAllPublications ? 500 : 14);
        dateText.setLayoutY(isAllPublications ? 63 : 54);
        dateText.setFill(Color.web("#a5a5a5"));

        if (isAllPublications) {
            Pane iconPane = new Pane();
            iconPane.setLayoutX(14);
            iconPane.setLayoutY(39);
            iconPane.setPrefSize(41, 38);
            iconPane.setStyle("-fx-background-color: #2447f9; -fx-border-radius: 1000; -fx-background-radius: 1000;");
            FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
            userIcon.setFill(Color.WHITE);
            userIcon.setLayoutX(12);
            userIcon.setLayoutY(27);
            userIcon.setSize("22");
            iconPane.getChildren().add(userIcon);

            Text userIdText = new Text(publication.getUserName());
            userIdText.setLayoutX(62);
            userIdText.setLayoutY(54);
            userIdText.setFont(new Font(14));

            Text roleText = new Text("Elève");
            roleText.setLayoutX(62);
            roleText.setLayoutY(71);
            roleText.setFill(Color.web("#a5a5a5"));
            roleText.setFont(new Font(11));

            Text contenuText = new Text(publication.getDescription());
            contenuText.setLayoutX(31);
            contenuText.setLayoutY(102);
            contenuText.setFill(Color.web("#7a757d"));



            pane.getChildren().addAll(titreText, iconPane, userIdText, roleText, contenuText, dateText);
        } else {
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

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamations/ModifierRec.fxml"));
                        Parent root = loader.load();

                        ModifierRecController modifyController = loader.getController();
                        modifyController.setRecId(mypub.get(index).getId_reclamation());
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
                        int publicationId = mypub.get(index).getId_reclamation();
                        pubs.supprimer(mypub.get(index).getId_reclamation());
                        System.out.println("deleted!");
                        listepubid.getChildren().remove(pane);
                        mypub.remove(index);
                        allPub.removeIf(pub -> pub.getId_reclamation() == publicationId);
                        allpubid.getChildren().clear();
                        for (int i = 0; i < allPub.size(); i++) {
                            Pane pubPane = createPublicationPane(allPub.get(i), i, true);
                            allpubid.getChildren().add(pubPane);
                        }
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Succes!");
                        alert.setContentText("Reclamation supprimé!");
                        alert.showAndWait();
                    } catch (SQLException e) {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR!");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

            pane.getChildren().addAll(titreText, dateText, modifyButton, deleteButton);
        }

        return pane;
    }

    @FXML
    void navigateAddPub(ActionEvent event) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Reclamations/AjouterRec.fxml"));
            Parent root = loader.load();
            addBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private  void handleForum(ActionEvent event){
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Reclamations/AfficherRecU.fxml"));
            Parent root = loader.load();
            forumBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchPubByTitle(ActionEvent event) {
        String searchText = searchField.getText();
        //TODO userid=1
        int userID = 1;
        try {

            if (searchText.isEmpty()) {
                mypub = (List<Reclamations>) pubs.getReclmationsById(userID);
            } else {
                mypub = pubs.searchreclamationbyType(searchText, userID);
            }
            listepubid.getChildren().clear();
            for (int i = 0; i < mypub.size(); i++) {
                Pane pane = createPublicationPane(mypub.get(i), i,false);
                listepubid.getChildren().add(pane);
            }
            listepubid.setPrefHeight(mypub.size() * 85);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshPublications() {
        allpubid.getChildren().clear();

    }

}


