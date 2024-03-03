package apiUtils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;
import entities.User;
public class PdfGenerator {

    public static void generatePdf(List<User> userList, String outputPath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);

                for (User user : userList) {
                    contentStream.showText("User ID: " + user.getId());
                    contentStream.newLineAtOffset(0, -20);

                    contentStream.showText("Name: " + user.getNom() + " " + user.getPrenom());
                    contentStream.newLineAtOffset(0, -20);

                    contentStream.showText("Username: " + user.getUsername());
                    contentStream.newLineAtOffset(0, -20);

                    contentStream.showText("Email: " + user.getEmail());
                    contentStream.newLineAtOffset(0, -20);

                    // Add other user details as needed

                    contentStream.newLineAtOffset(0, -20); // Move to the next line for the next user
                }

                contentStream.endText();
            }

            document.save(outputPath);
            System.out.println("PDF generated successfully at: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
