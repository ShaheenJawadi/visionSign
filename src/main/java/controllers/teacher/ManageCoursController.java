package controllers.teacher;

import State.TeacherNavigations;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import entities.Cours;
import entities.Lesson;
import entities.Ressource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import mock.Category;
import mock.Level;
import mock.SubCategory;
import services.cours.CoursService;
import services.lesson.LessonService;
import services.ressource.RessourceService;
import validation.ValidateCours;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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




    @FXML
    private HBox imageContainer;

    private  String imgLink ;


    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dkdx59xe9",
            "api_key", "464462256124751",
            "api_secret", "h0D2KPEbrpHqzK3tSlxRJHadeLE"
    ));

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
                TeacherNavigations.getInstance().openCoursListPage();
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


          c.setEnseignantId(3);
          c.setImage(imgLink);

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



    public VBox getVBoxRoot()
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TeacherSpace/cours/create/SingleLesson.fxml"));
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




    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(null);

        if (files != null && !files.isEmpty()) {
            for (File file : files) {
                // Create a new Task for the upload process
                Task<Void> uploadTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        uploadFile(file);
                        return null;
                    }
                };

                // Add a ProgressIndicator to the HBox
                ProgressIndicator progressIndicator = new ProgressIndicator();

                imageContainer.getChildren().add(progressIndicator);


                // Run the upload task in a background thread
                new Thread(uploadTask).start();

                // Remove the ProgressIndicator when the task is done
                uploadTask.setOnSucceeded(event -> {
                    imageContainer.getChildren().remove(progressIndicator);
                });
            }
        }
    }

    private void uploadFile(File file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

            String imageUrl = (String) uploadResult.get("url");

            imgLink=imageUrl;
            System.out.println("eee");
            System.out.println(imageUrl);

            Image image = new Image(imageUrl);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            // Update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                imageContainer.getChildren().add(imageView);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}


