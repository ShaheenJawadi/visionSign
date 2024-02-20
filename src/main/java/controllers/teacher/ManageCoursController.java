package controllers.teacher;

import entities.Cours;
import entities.Ressource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import mock.Category;
import mock.Level;
import mock.SubCategory;
import services.cours.CoursService;
import services.ressource.RessourceService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageCoursController implements Initializable {


    private @FXML VBox vbRoot;
    private StackPane spSubScene;





    @FXML
    private ComboBox<Category> categorieSelect;

    @FXML
    private TextArea description;

    @FXML
    private HTMLEditor leconContent;

    @FXML
    private TextField leconDuration;

    @FXML
    private Button leconImage;

    @FXML
    private TextField leconOrdre;

    @FXML
    private TextField leconTitle;

    @FXML
    private TextField leconVideo;

    @FXML
    private TextField ressource_lien;

    @FXML
    private ComboBox<Level> niveauSelect;

    @FXML
    private TextField nom;

    @FXML
    private ComboBox<SubCategory> subCategorieSelect;

    @FXML
    private TextField tags;

    @FXML
    private TextField ressource_type;





    @FXML
    void submitCours(ActionEvent event) throws SQLException {

        DbAddCoursDetails();
        DbAddCoursRessources(1);

    }



    public void DbAddCoursDetails() throws SQLException{
        Cours  c = new Cours();
        c.setEnseignantId(3);
        c.setNom(nom.getText());
        c.setImage("000");
        c.setDescription(description.getText());
        c.setTags(tags.getText());
        c.setSubCategoryId(subCategorieSelect.getSelectionModel().getSelectedItem().getId());
        c.setNiveauId(niveauSelect.getSelectionModel().getSelectedItem().getId());
        CoursService cours=new CoursService();
        cours.add(c);
    }

    public void DbAddCoursRessources(int coursId) throws SQLException{
        Ressource r = new Ressource();
        r.setLien(ressource_lien.getText());
        r.setType(ressource_type.getText());
        r.setCoursId(coursId);
        RessourceService ressource=new RessourceService();
        ressource.add(r);
    }



    VBox getVBoxRoot()
    {
        return vbRoot;
    }

    public void setStackPane(StackPane stackPane) {
        this.spSubScene = stackPane;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCategory();
        initSubCategory();
        initLevel();

    }




    public  void  initCategory(){

        Category[] fakeCategories = {
                new Category(1, "cat1"),
                new Category(2, "cat2"),

        };

        categorieSelect.setItems(FXCollections.observableArrayList(fakeCategories));



        categorieSelect.setCellFactory(param -> {
            ListCell<Category> cell = new ListCell<>() {
                @Override
                protected void updateItem(Category item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNom());
                    }
                }
            };
            return cell;
        });


        categorieSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue.getId());


            }
        });

    }
    public  void  initSubCategory(){

     SubCategory[] fakeSubCategories = {
                new SubCategory(1,1, "cat1"),
                new SubCategory(2,1, "cat2"),

        };


        subCategorieSelect.setItems(FXCollections.observableArrayList(fakeSubCategories));



        subCategorieSelect.setCellFactory(param -> {
            ListCell<SubCategory> cell = new ListCell<>() {
                @Override
                protected void updateItem(SubCategory item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNom());
                    }
                }
            };
            return cell;
        });


        subCategorieSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue.getId());


            }
        });

    }

    public  void  initLevel(){

        Level[] fakeLaevel = {
                new Level(1, 1),
                new Level(2, 2),

        };


        niveauSelect.setItems(FXCollections.observableArrayList(fakeLaevel));



        niveauSelect.setCellFactory(param -> {
            ListCell<Level> cell = new ListCell<>() {
                @Override
                protected void updateItem(Level item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item.getLevel()));
                    }
                }
            };
            return cell;
        });


        niveauSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue.getId());


            }
        });

    }

}


