package controllers.teacher;

import entities.Cours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import services.cours.CoursService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursItemController   {

    @FXML
    private Text niveau;

    @FXML
    private Text duree;

    @FXML
    private Text nbLecons;

    @FXML
    private Text ressource;

    @FXML
    private Text sousCategorie;

    @FXML
    private Text title;


    private  Cours cours ;

    private CoursGridPane parentController;

    public void setParentController(CoursGridPane parentController) {
        this.parentController = parentController;
    }
    public void putData(Cours cours , String coursTitle , String coursRessourceType , String coursNiveau , String coursSubCategory , String coursDuration , String coursNbLessons ) {
            this.cours  =cours ;

        title.setText(coursTitle);
        sousCategorie.setText(coursSubCategory);
        ressource.setText(coursRessourceType);
        nbLecons.setText(coursNbLessons);
        duree.setText(coursDuration);
        niveau.setText(coursNiveau);
    }


    @FXML
    void deleteCours(ActionEvent event) throws SQLException {
        System.out.println(cours.getId());
        CoursService coursService = new CoursService() ;
        coursService.delete(cours.getId());
        parentController.renderDataCours();

    }

    @FXML
    void editCours(ActionEvent event) {

    }

}
