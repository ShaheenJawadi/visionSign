package controllers.MainPages.Cours;

import State.MainNavigations;
import controllers.MainPages.Cours.SinglePageComponants.CoursLessonItemController;
import entities.Cours;
import entities.Lesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;
public class SingleCoursController implements Initializable {

//TODO ADD AVIS ATTR


    @FXML
    private VBox SingleCoursVboxHolder ;

    @FXML
    private  VBox coursPageLessonItem ;

    @FXML
    private Button categoryname;

    @FXML
    private Text descripton;

    @FXML
    private ImageView rating;

    @FXML
    private Text teacher;

    @FXML
    private ImageView thumbnail;

    @FXML
    private Text title;

    @FXML
    private Text uploadedDate;
    @FXML
    private  Button accessCoursBtn ;





    @FXML
    private Text nbLesson;

    @FXML
    private Text niveau;

    @FXML
    private Text totalDuration;


    private Cours cours ;

    private boolean  isEnrolled ;

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {

        this.cours = cours;
        this.isEnrolled = cours.isEnrolled();

    }

    public VBox getVBoxRoot() {
        return SingleCoursVboxHolder;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }




    public void  renderContent(Cours cours){
        this.setCours(cours);




        try {

            fillTextContent(title , cours.getNom());
            fillTextContent(descripton , cours.getDescription());

            fillTextContent(nbLesson , cours.nbLessons());
            fillTextContent(niveau , cours.getLevel());
            fillTextContent(totalDuration , cours.lessonsDuration());

            if(categoryname != null){
                categoryname.setText(cours.getCategory().getNom());
            }

            setupEnrollBtnState();

            renderListLessons();

        if(thumbnail!=null){

            Image img = new Image(cours.getImage());
            thumbnail.setImage(img);
        }
        }catch (Exception e ){
            System.out.println("sqddfsdf"+e);

        }

               //todo rating
                //todo teacher


                //Todo addDtae      uploadedDate



    }



    public void fillTextContent(Text textView , String text){
        if(textView!= null){
            textView.setText(text);
        }
    }

    public  void  renderListLessons (){
        coursPageLessonItem.getChildren().clear();
        try {
            // Load customItem.fxml for each item

            for (Lesson l : cours.getLessons()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Main/sectionContent.fxml"));
                loader.load();

                CoursLessonItemController controller = loader.getController();
                controller.renderItem(l.getTitre() , l.getTextDuration());

                coursPageLessonItem.getChildren().add(controller.getRooot());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void setupEnrollBtnState() {
        if (isEnrolled){
                accessCoursBtn.setText("Accéder a ce cours");
        }
        else {

            accessCoursBtn.setText("Ajouter à mes cours");


        }
    }




    public void accessCours(ActionEvent actionEvent) {
        if(isEnrolled){


           MainNavigations.getInstance().openCoursLessonPage();

        }
        else {
            cours.addToBag();
            this.isEnrolled = true ;
            setupEnrollBtnState();
        }
    }
}
