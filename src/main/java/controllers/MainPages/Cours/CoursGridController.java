package controllers.MainPages.Cours;

import Navigation.MainNavigations;
import entities.Cours;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
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



    public void renderGrid (Cours cours ){


        title.setText(cours.getNom());
         nbLessons.setText(cours.nbLessons());
         level.setText(cours.getLevel());
         enseignant.setText(cours.getEnseignant());
         duration.setText(cours.lessonsDuration());
         category.setText(cours.getCategory().getNom());




    }

    @FXML
    void addToBag(MouseEvent event) {

    }




    public void openSingleCours(MouseEvent mouseEvent) {

        System.out.println("jsd");
        MainNavigations mainNavigations = MainNavigations.getInstance() ;
        mainNavigations.openSingleCoursPage();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
