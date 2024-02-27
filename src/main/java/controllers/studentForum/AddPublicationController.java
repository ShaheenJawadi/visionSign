package controllers.studentForum;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import entities.Publications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AddPublicationController extends BaseForumController {
    private static final String API_KEY = "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";

    private static final Set<String> BANNED_WORDS = new HashSet<>(Arrays.asList(
            "stupid", "idiot", "shut up"
    ));
    @FXML
    private TextField titreId;

    @FXML
    private TextField questionId;
    private static boolean containsBannedWords(String text) {
        String[] words = text.split("\\s+"); // Split the text into words
        for (String word : words) {
            if (BANNED_WORDS.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public static boolean containsViolence(String titre, String question) {
        boolean containsViolence = checkWithAiModel(titre, question);

        if (!containsViolence) {
            containsViolence = containsBannedWords(titre) || containsBannedWords(question);
        }

        return containsViolence;
    }
    private static boolean checkWithAiModel(String titre, String question) {
        try {

            String prompt = " Please check the following publication for any disrespectful language or inappropriate content for an application for e-learning , including words such as 'stupid,' 'idiot,' or any other derogatory terms. Return true if the publication contains any such language, and false otherwise. Here is the publication: this is the title of the publication ' " + titre + "' this is the publication : '"  + question + "' ";




            OpenAiService service = new OpenAiService("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");
            CompletionRequest completionRequest = CompletionRequest.builder()
                    .prompt(prompt)
                    .model("gpt-3.5-turbo-instruct")
                    .echo(true)
                    .maxTokens(500)
                    .build();

            String response = service.createCompletion(completionRequest).getChoices().get(0).getText();
            System.out.println(response.equalsIgnoreCase("true"));
            boolean x= response.equalsIgnoreCase("true");
            System.out.println(x);
            if(x){
                return true;}
            else return false;

        } catch (Exception e) {
            return false;
        }
    }
    @FXML
    public void handlePublish(ActionEvent event) {
        String titreText = titreId.getText();
        String questionText = questionId.getText();
        System.out.println("clicked");
        try {
            if (titreText != null && !titreText.isEmpty() && questionText != null && !questionText.isEmpty()) {
                if (!pubs.publicationExists(titreText, questionText,6)) {
                    boolean y =containsViolence(titreText,questionText) ;
                    if(y == false){
                        pubs.addPublicationOrCommentaire(new Publications(titreText, questionText, new Date(), 6));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Succès!");
                        alert.setContentText("Publication ajoutée!");
                        alert.showAndWait();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));
                        Parent root = loader.load();
                        forumBtn.getScene().setRoot(root);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Contenu inapproprié!");
                        alert.setContentText("La publication contient du contenu inapproprié et ne peut pas être ajoutée.");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur!");
                    alert.setContentText("Une publication avec le même titre et contenu existe déjà.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur!");
                alert.setContentText("Impossible d'ajouter une publication vide.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
