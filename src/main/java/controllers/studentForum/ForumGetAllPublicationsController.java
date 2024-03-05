package controllers.studentForum;

import javafx.event.EventHandler;
import javafx.scene.Cursor;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Publications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.Forum.PublicationsService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ForumGetAllPublicationsController extends BaseForumController {



    @FXML
    public  AnchorPane rootId ;
    private int userId=State.UserSessionManager.getInstance().getCurrentUser().getId();




    public  AnchorPane getRootBox(){
        return this.rootId ;
    }

    @FXML
    public void initialize() {
        System.out.println("ttttttttttt"+userId);
        refreshPublications();
        try {
            mypub = pubs.getPublicationsByUserId(userId); //todo userId
            mypub.sort(Comparator.comparing(Publications::getDate_creation).reversed());

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

            allPub = pubs.getAllPublications();
            allPub.sort(Comparator.comparing(Publications::getDate_creation).reversed());
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
            System.out.println(e);
            throw new RuntimeException(e);

        }
    }


}
