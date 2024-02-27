package controllers.teacher;

import entities.Cours;
import entities.Lesson;
import entities.Ressource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import mock.Category;
import mock.Level;
import mock.SubCategory;
import services.cours.CoursService;
import services.lesson.LessonService;
import services.ressource.RessourceService;
import validation.ValidateCours;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageCoursController implements Initializable {


    private @FXML VBox vbRoot;
    private StackPane spSubScene;

    private Cours cours ;





    @FXML
    private ComboBox<Category> categorieSelect;

    @FXML
    private TextArea description;




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
    private Label validateCategory;

    @FXML
    private Label validateDescription;

    @FXML
    private Label validateLevel;

    @FXML
    private Label validateLien;

    @FXML
    private Label validateSubCategory;

    @FXML
    private Label validateTags;

    @FXML
    private Label validateTitleCours;

    @FXML
    private Label validateType;

    @FXML
    private TextFlow validationErrorMessage;



    @FXML
    private VBox ListCoursHolder;

    @FXML
    private Button addLesson;


    public void setCours(Cours cours) {
        this.cours = cours;
    }

    @FXML
    void submitCours(ActionEvent event) throws SQLException, IOException {

        if(validateCours() && validateRessources() && ValidateLessons())
        {
            validationErrorMessage.setVisible(false);
            int cours_id = DbAddCoursDetails();
            if (cours_id > 0) {
                DbAddCoursRessources(cours_id);
                gatherLessonData(cours_id);

            }
        }
        else {
            validationErrorMessage.setVisible(true);
        }

    }


    public boolean validateCours(){
        boolean isValid   ;

        isValid =   ValidateCours.isNotEmpty(nom ,validateTitleCours , "ee")
                && ValidateCours.isNotEmptySelectErea(categorieSelect ,validateCategory , "ee")
                && ValidateCours.isNotEmptySelectErea(subCategorieSelect ,validateSubCategory , "ee")
                && ValidateCours.isNotEmptySelectErea(niveauSelect ,validateLevel , "ee")
                && ValidateCours.isNotEmpty(tags ,validateTags , "ee")
                && ValidateCours.isNotEmptyTextErea(description ,validateDescription , "ee");
        return  isValid;
    }

    public int DbAddCoursDetails() throws SQLException{
        Cours  c = new Cours();




      if(validateCours()){

          System.out.println("valid");
          c.setEnseignantId(3);
          c.setImage("000");

          c.setNom(nom.getText());
          c.setDescription(description.getText());
          c.setTags(tags.getText());
          c.setSubCategoryId(subCategorieSelect.getSelectionModel().getSelectedItem().getId());
          c.setNiveauId(niveauSelect.getSelectionModel().getSelectedItem().getId());


          CoursService cours=new CoursService();
        int cours_id =  cours.add(c);

        return cours_id ;

      }

      else {

          return 0 ;
      }



    }

    public boolean validateRessources(){
        boolean isValid   ;

        isValid =   ValidateCours.isNotEmpty(ressource_lien ,validateLien , "ee")
                && ValidateCours.isNotEmpty(ressource_type ,validateType , "ee");

        return  isValid;
    }

    public void DbAddCoursRessources(int coursId) throws SQLException{

        boolean isValid   ;



        if(validateRessources()) {

            Ressource r = new Ressource();
            r.setLien(ressource_lien.getText());
            r.setType(ressource_type.getText());
            r.setCoursId(coursId);
            RessourceService ressource = new RessourceService();
            ressource.add(r);
        }
        else {

        }
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
        initValidation();
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


    public  void initValidation(){
      validateCategory.setVisible(false);

        validateDescription.setVisible(false);

        validateLevel.setVisible(false);

        validateLien.setVisible(false);

       validateSubCategory.setVisible(false);

         validateTags.setVisible(false);

         validateTitleCours.setVisible(false);

         validateType.setVisible(false);
        validationErrorMessage.setVisible(false);
    }

    public void addLessonClick(ActionEvent actionEvent) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teacher/cours/create/SingleLesson.fxml"));
           VBox ll=  loader.load();
            SingleLessonController controller = loader.getController();
            ll.getProperties().put("fxmlLoader", loader);


            ListCoursHolder.getChildren().add(controller.getSingleLessonBox());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }


    public boolean ValidateLessons()  {

        boolean isValid=true ;

        for (Node node : ListCoursHolder.getChildren()) {
            if(!isValid){
                break;
            }
            if (node instanceof VBox) {


                VBox singleLessonPane = (VBox) node;


                FXMLLoader loader = (FXMLLoader) singleLessonPane.getProperties().get("fxmlLoader");

                if (loader != null) {

                    SingleLessonController controller = loader.getController();

                    // Ensure the controller is not null
                    if (controller != null) {

                        if(!controller.vaidateLessonFields()){
                                isValid = controller.vaidateLessonFields();
                                break;
                        }



                    } else {
                        System.out.println("null for SingleLesson.");
                    }
                } else {
                    System.out.println("FXMLLoader is null for SingleLesson.");
                }
            }


        }
        System.out.println(isValid+"dskf");
    return isValid ;
    }



    public void gatherLessonData(int cours_id) throws IOException, SQLException {
            List<Lesson> allLessonData = new ArrayList<>();

        for (Node node : ListCoursHolder.getChildren()) {
            if (node instanceof VBox) {


                VBox singleLessonPane = (VBox) node;


                FXMLLoader loader = (FXMLLoader) singleLessonPane.getProperties().get("fxmlLoader");

                if (loader != null) {

                    SingleLessonController controller = loader.getController();

                    if (controller != null) {

                        LessonService lessonService = new LessonService() ;
                        lessonService.add(controller.getLesson(cours_id));




                    } else {
                        System.out.println("null for SingleLesson.");
                    }
                } else {
                    System.out.println("FXMLLoader is null for SingleLesson.");
                }
            }
        }

        System.out.println(allLessonData);
    }


}


