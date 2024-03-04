package controllers.MainPages.Cours.Filter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class singleCategoryFilterController {


    @FXML
    private Button catBtn ;

    public    int categoryId ;

    public  Button getCatBtn(){
        return  this.catBtn ;
    }

    public void  setCategoryId (int categoryId ){
        this.categoryId= categoryId ;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
