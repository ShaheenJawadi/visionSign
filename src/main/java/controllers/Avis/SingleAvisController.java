package controllers.Avis;

import entities.Avis;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SingleAvisController {

    @FXML
    private VBox rootId;

    @FXML
    private Text texteid;

    @FXML
    private Text userNameid;

    public VBox getRoot() {
        return this.rootId;
    }

    public void renderItem(Avis avis) {

        // Assurez-vous que l'objet avis n'est pas nul
        if (avis != null) {
            // Récupérez le nom d'utilisateur et le message de l'avis
            String userName = avis.getUserName();
            String message = avis.getMessage();

            // Définir le texte dans les éléments Text
            userNameid.setText(userName);
            texteid.setText(message);
        }
    }
}
