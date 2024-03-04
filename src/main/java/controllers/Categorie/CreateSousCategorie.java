package controllers.Categorie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateSousCategorie implements Initializable {
    @FXML
    private Text nom;
    @FXML
    private TextArea description;
    @FXML
    private ChoiceBox<String> status;
    private @FXML VBox vbRoot;
    private StackPane spSubScene;

    @FXML
    private VBox sousCategorieBox;

    public VBox getSousCategorieBox() {
        return sousCategorieBox;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void submitSousCategorie() {

    }
}
