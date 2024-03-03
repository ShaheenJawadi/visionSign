package controllers.Reclamations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NavbarController {

    @FXML
    private AnchorPane navbar;
    @FXML
    private Button ReclamtionBtn, Avis;
    public void rec(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamations/AfficherRecU.fxml"));
            Parent root = loader.load();
            ReclamtionBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void av(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Avis/getAllAvis.fxml"));
            Parent root = loader.load();
            Avis.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
