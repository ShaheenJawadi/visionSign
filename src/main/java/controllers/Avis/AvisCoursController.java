package controllers.Avis;


import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class AvisCoursController {


    @FXML
    private Text AvgAvis ;



    @FXML
    private VBox ListAvisHolder ;





    @FXML
    private  Text totalAvis ;

    @FXML
    private Text nbrAvis1 ;
    @FXML
    private Text nbrAvis2 ;
    @FXML
    private Text nbrAvis3 ;
    @FXML
    private Text nbrAvis4 ;
    @FXML
    private Text nbrAvis5 ;



    @FXML
    private TextArea avisContent;



    @FXML
    private VBox rootId ;


    private  int CoursId ;



    public  VBox getRoot(){
        return  this.rootId;
    }
    public  void  setCoursId(int coursId){
        this.CoursId= coursId ;

    }


    public void  renderLessonList(){
        ListAvisHolder.getChildren().clear();
        try {
            // Load customItem.fxml for each item
                //Lesson l : cours.getLessons()
            for (int i = 0 ; i<4 ; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Avis/SingleAvis.fxml"));
                loader.load();

                SingleAvisController controller = loader.getController();
                controller.renderItem();

                ListAvisHolder.getChildren().add(controller.getRoot());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void postAvis(ActionEvent event) {

    }





}
