package controllers.MainPages.Cours.Filter;

import State.MainNavigations;
import entities.Cours;
import entities.UserCours;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import services.cours.UserCoursServices;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursGridController implements Initializable {



    @FXML
    private Button category;

    @FXML
    private Text duration;

    @FXML
    private Text enseignant;

    @FXML
    private Text level;

    @FXML
    private Text nbLessons;

    @FXML
    private ImageView thumbnail;

    @FXML
    private Text title;


    private  Cours cours ;


    public void renderGrid (Cours cours ){

        this.cours = cours ;

        title.setText(cours.getNom());
         nbLessons.setText(cours.nbLessons());
         level.setText(cours.getLevel());
         enseignant.setText(cours.getEnseignant());
         duration.setText(cours.lessonsDuration());
         category.setText(cours.getCategory().getNom());




    }

    @FXML
    void addToBag(MouseEvent event) throws SQLException {


        System.out.println("enter");

        cours.addToBag();



    }




    public void openSingleCours(MouseEvent mouseEvent) {

        System.out.println("click");
        System.out.println(cours);

        MainNavigations mainNavigations = MainNavigations.getInstance() ;
        mainNavigations.openSingleCoursPage(cours);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
