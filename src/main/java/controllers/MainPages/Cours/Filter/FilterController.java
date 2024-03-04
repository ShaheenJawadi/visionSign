package controllers.MainPages.Cours.Filter;

import controllers.MainPages.Cours.SinglePageComponants.CoursSingleLessonItem;
import entities.Cours;
import entities.Lesson;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.input.MouseEvent;
import services.cours.CoursService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FilterController implements Initializable {


    @FXML
    private VBox FilterPageVbox;

     @FXML
     private FlowPane listCoursHolder ;

     @FXML
     private HBox listCategories ;

    protected    int categoryId;

    public VBox getVBoxRoot()
    {
        return FilterPageVbox;
    }




    public   void setCurrentCategory(int categoryId){


         this.categoryId= categoryId;
        renderCategories();
        try {
            showCoursList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public  void  renderCategories(){
        listCategories.getChildren().clear();
        try {
            for(int i = 0; i < 6 ; i++) {
                // Lesson l = cours.getLessons().get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/CoursPages/Filter/categoryBtnNavigation.fxml"));

                loader.load();


                singleCategoryFilterController controller = loader.getController();
                controller.setCategoryId(i);
                Button btn = controller.getCatBtn();

                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Handle button click event here
                        // For example, you can perform some action when the button is clicked
                        setCurrentCategory(controller.getCategoryId());
                        System.out.println("Button clicked: " + btn.getText());
                    }
                });

                if(categoryId == i){
                    btn.getStyleClass().remove("uploadBtn");
                }


                listCategories.getChildren().add(btn);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderCategories();

        try {
            showCoursList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    public  void showCoursList() throws SQLException {

        System.out.println("render");
        listCoursHolder.getChildren().clear();
        CoursService coursService=new CoursService();
        List<Cours> coursList =coursService.getBySubCategory(categoryId);
        try {
            for (Cours cours : coursList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPages/Components/Cours/SingleGrid.fxml"));

                VBox customView = loader.load();

                CoursGridController controller = loader.getController();
                controller.renderGrid(cours);


                listCoursHolder.getChildren().add(customView);
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
