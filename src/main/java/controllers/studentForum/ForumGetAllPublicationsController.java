package controllers.studentForum;

import entities.Publications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;

public class ForumGetAllPublicationsController extends BaseForumController {
    @FXML
    public void initialize() {
        refreshPublications();
        try {
            mypub = pubs.getPublicationsByUserId(6);
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
            throw new RuntimeException(e);

        }
    }

    @FXML
    void navigateAddPub(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/AddPublication.fxml"));
            Parent root = loader.load();
            addBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
