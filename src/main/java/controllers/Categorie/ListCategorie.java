package controllers.Categorie;

import State.MainNavigations;
import controllers.MainPages.Cours.Filter.CoursGridController;
import entities.Categorie;
import entities.Cours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import services.Categorie.CategorieService;
import services.cours.CoursService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListCategorie implements Initializable {

    @FXML
    private FlowPane listCategorieHolder ;
    @FXML
    private VBox HomeVboxHolder;


    public VBox getVBoxRoot()
    {
        return HomeVboxHolder;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            if(listCategorieHolder != null)
                showCategoriesList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public  void showCategoriesList () throws SQLException {

        System.out.println("render");
        listCategorieHolder.getChildren().clear();
        CategorieService categorieService=new CategorieService();
        List<Categorie> categories =categorieService.getAllCategories();
        try {
            for (Categorie cat : categories) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Categorie/SingleGridCategorie.fxml"));

                VBox customView = loader.load();

                SingleCategorieGridController controller = loader.getController();
                controller.renderGrid(cat);


                listCategorieHolder.getChildren().add(customView);
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
