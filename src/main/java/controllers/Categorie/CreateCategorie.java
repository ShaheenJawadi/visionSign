package controllers.Categorie;

import entities.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import services.Categorie.CategorieService;
import validation.ValidateCours;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateCategorie implements Initializable {


    private @FXML VBox vbRoot;
    private StackPane spSubScene;

    private Categorie categorie;

    @FXML
    private TextArea description;

    @FXML
    private TextField nom;


    @FXML
    private Label validateDescriptionCategorie;


    @FXML
    private Label validateTitleCategorie;


    @FXML
    private TextFlow validationErrorMessage;

    @FXML
    private Button upload;


    @FXML
    private VBox ListCategorieHolder;

    @FXML
    private Button addSousCat;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initValidation();
    }


    public void setCategorie(Categorie cat) {
        this.categorie = cat;
    }


    @FXML
    void submitCategorie(ActionEvent event) throws SQLException, IOException {

        if (validateCategorie()) {
            validationErrorMessage.setVisible(false);
            int cat_id = DbAddCategorieDetails();
            if (cat_id > 0) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/catgeorie/CategorieList.fxml"));

                loader.load();

                CategorieList categorieListPage = loader.getController();
                categorieListPage.setStackPane(spSubScene);
                spSubScene.getChildren().clear();
                spSubScene.getChildren().add(categorieListPage.getVBoxRoot());
            }
        } else {
            validationErrorMessage.setVisible(true);
        }

    }

    VBox getVBoxRoot() {
        return vbRoot;
    }

    public void setStackPane(StackPane stackPane) {
        this.spSubScene = stackPane;
    }

    public boolean validateCategorie() {
        boolean isValid;

        isValid = ValidateCours.isNotEmpty(nom, validateTitleCategorie, "ee")
                && ValidateCours.isNotEmptyTextErea(description, validateDescriptionCategorie, "ee");
        return isValid;
    }

    public int DbAddCategorieDetails() throws SQLException {
        Categorie c = new Categorie();


        if (validateCategorie()) {

            System.out.println("valid");
            c.setNom(nom.getText());
            c.setDescription(description.getText());
            c.setLast_updated(new Date());
            c.setImage("000");


            CategorieService cat = new CategorieService();
            cat.addCategorie(c);

            return c.getId();

        } else {

            return 0;
        }


    }

    public void initValidation() {

        validateDescriptionCategorie.setVisible(false);

        validateTitleCategorie.setVisible(false);


        validationErrorMessage.setVisible(false);
    }

    public void addSousCatClick(ActionEvent actionEvent) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/catgeorie/CreateSousCategorie.fxml"));
            VBox ll = loader.load();
            CreateSousCategorie controller = loader.getController();
            ll.getProperties().put("fxmlLoader", loader);


            ListCategorieHolder.getChildren().add(controller.getSousCategorieBox());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void uploadImage () {

    }
}
