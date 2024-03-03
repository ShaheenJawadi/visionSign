package controllers.Student;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
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
    int note;
    int size;
    @FXML
    private Text notess;
    private String sugg;

    public void setNoteQuiz(int note) {
        this.note=note;
    }

    public void setNombreQuestions(int size) {
        this.size=size;
        notess.setText(note + "/" + size);
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

                Image image = Image.getInstance(question.getImage());
                float maxWidth = 500f;
                float maxHeight = 500f;
                float widthRatio = image.getWidth() / maxWidth;
                float heightRatio = image.getHeight() / maxHeight;
                float ratio = Math.max(widthRatio, heightRatio);

                if (ratio > 1) {
                    image.scaleToFit(maxWidth / ratio, maxHeight / ratio);
                }
                doc.add(image);

                doc.add(new Paragraph(" "));

                for (int j = 0; j < suggestionLists.size(); j++) {
                    Suggestion suggestion = suggestionLists.get(j);
                    doc.add(new com.itextpdf.text.Paragraph(suggestion.getSuggestion()));
                    if (suggestion.isStatus()){
                        sugg= suggestion.getSuggestion();
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
}
