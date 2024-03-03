package controllers.Student;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Questions;
import entities.Suggestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class NoteQuizController {
    public static List<Questions> questionsLists;
    public static List<Suggestion> suggestionLists;
    public static List<String> useranswers;
    float noteSur20;
    int size;
    @FXML
    private Text notess;
    private String sugg;

    public void setNoteQuiz(float note) {
        this.noteSur20=note;
    }

    public void setNombreQuestions(int size) {
        this.size=size;
        notess.setText(noteSur20 + "/20");

    }
    @FXML
    void initialize() {

    }
    @FXML
    void generatePDF(ActionEvent actionEvent) {
        try {
            String userHome = System.getProperty("user.home");
            String downloadsPath = userHome + "\\Downloads\\quizz.pdf";
            OutputStream outputStream = new FileOutputStream(downloadsPath);
            Document doc = new Document();
            PdfWriter.getInstance(doc, outputStream);
            doc.open();

            for (int i = 0; i < questionsLists.size(); i++) {
                Questions question = questionsLists.get(i);
                doc.addTitle("Question " + (i + 1));
                doc.add(new com.itextpdf.text.Paragraph(question.getQuestion()));
                doc.add(new Paragraph(" "));

                String imageUrl = question.getImage();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Image image = Image.getInstance(imageUrl);
                    float maxWidth = 500f;
                    float maxHeight = 500f;
                    float widthRatio = image.getWidth() / maxWidth;
                    float heightRatio = image.getHeight() / maxHeight;
                    float ratio = Math.max(widthRatio, heightRatio);

                    if (ratio > 1) {
                        image.scaleToFit(maxWidth / ratio, maxHeight / ratio);
                    }
                    doc.add(image);
                }

                doc.add(new Paragraph(" "));

                for (int j = 0; j < suggestionLists.size(); j++) {
                    Suggestion suggestion = suggestionLists.get(j);
                    String userAnswer = useranswers.get(i);
                    String[] parts = userAnswer.split(" ");
                    String selectedAnswer = parts[2];

                    if (suggestion.isStatus()){
                        sugg= suggestion.getSuggestion();
                        Chunk chunk =new Chunk(suggestion.getSuggestion());
                        chunk.setBackground(BaseColor.GREEN);
                        doc.add(new com.itextpdf.text.Paragraph(chunk));

                    }else if (!suggestion.isStatus() && suggestion.getSuggestion().equals(selectedAnswer)){
                        Chunk chunk =new Chunk(suggestion.getSuggestion());
                        chunk.setBackground(BaseColor.RED);
                        doc.add(new com.itextpdf.text.Paragraph(chunk));
                    }
                    else {
                        doc.add(new com.itextpdf.text.Paragraph(suggestion.getSuggestion()));
                    }



                }
                doc.add(new com.itextpdf.text.Paragraph(useranswers.get(i)));
                if (useranswers.get(i).contains("fausse")) {
                    doc.add(new com.itextpdf.text.Paragraph("La bonne réponse est: "+sugg));
                }
                if (i < questionsLists.size() - 1) {
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph("________________________________________________"));
                    doc.add(new Paragraph(" "));
                }
            }

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Votre note finale est: "+notess.getText()));
            doc.close();
            outputStream.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF a été téléchargé avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la génération du PDF");
            alert.setContentText("Une erreur est survenue lors de la création du PDF : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void setDownload(List<Questions> questionsList, List<Suggestion> suggestionList,List <String> answers) {
        questionsLists=questionsList;
        suggestionLists=suggestionList;
        useranswers=answers;

    }

    @FXML
    public void retourAccueil(){
        // à changer apres pour etre dynamique integration
    }
}
