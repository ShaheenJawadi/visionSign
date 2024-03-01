package controllers.MainPages;

import Navigation.MainNavigations;
import javafx.event.ActionEvent;

public class MainPageController {



    public void OpenListCoursPage(ActionEvent actionEvent) {
        System.out.println("jdhfjsjdk");
        MainNavigations mainNavigations = MainNavigations.getInstance() ;
         mainNavigations.openCoursFilterPage();
    }
}
