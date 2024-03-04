package controllers.Categorie;


import entities.Categorie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import services.Categorie.CategorieService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SingleCategorieGridController implements Initializable {



    @FXML
    private Button category;

    @FXML
    private Text last_updated;

    @FXML
    private Text description;


    @FXML
    private Text nbSousCategorie;

    @FXML
    private ImageView thumbnail;

    @FXML
    private Text nom;

    private int catId;
    private CategorieService categorieService;


    private Categorie categorie ;

    public void renderGrid (Categorie categorie ){

        this.categorie = categorie ;

        nom.setText(categorie.getNom());
        nbSousCategorie.setText(categorie.nbsousCategories());
        description.setText(categorie.getDescription());
        last_updated.setText(categorie.getLast_updated().toString());

    }

    @FXML
    void viewSubCat(MouseEvent event) throws SQLException {


        System.out.println("enter");

        //categorieService.getSousCategorieListByCategoryId();



    }




    public void openSingleCategorie(MouseEvent mouseEvent) {

        System.out.println("click");
        System.out.println(categorie);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

