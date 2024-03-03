package controllers.studentForum;

import com.google.gson.Gson;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatBotController {

    @FXML
    private Button btnChat,forumBtn;
    @FXML
    private HBox imagesHbox;
    @FXML
    private Text textHelp;
    @FXML
    private TextField MessageTf;
    @FXML
    private VBox ChatbotContainer;
    @FXML
    private ScrollPane chatScrollPane;
    private void scrollToBottom() {
        Platform.runLater(() -> {
            chatScrollPane.layout();
            chatScrollPane.setVvalue(1.0);
        });
    }

    @FXML
    public void handleSend(ActionEvent event) {
        String userMessage = MessageTf.getText();

        if (userMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Message cannot be empty!");
            alert.showAndWait();
        } else {
            // Proceed with sending the message
            ChatbotContainer.getChildren().remove(imagesHbox);
            ChatbotContainer.getChildren().remove(textHelp);
            sendMessage(userMessage);
            MessageTf.clear();
        }
    }
    @FXML
    public void handleChat(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ChatBot.fxml"));
            Parent root = loader.load();
            btnChat.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void handleForum(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/ForumGetAllPublications.fxml"));
            Parent root = loader.load();
            forumBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addUserMessage(String message) {
        Text userText = new Text(message);
        userText.setWrappingWidth(700); // Adjust to fit your content area
        userText.setFont(new javafx.scene.text.Font(14.0));

        Circle userIconCircle = new Circle(21.0, Color.web("#2447f9"));
        FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        userIcon.setSize("20px");
        userIcon.setFill(Color.WHITE);

        StackPane iconPane = new StackPane();
        iconPane.getChildren().addAll(userIconCircle, userIcon);

        HBox hbox = new HBox(10, iconPane, userText);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(15, 0, 10, 20)); // Adjust as needed

        ChatbotContainer.getChildren().add(hbox);
        scrollToBottom();
    }

    public void addBotResponse(String response) {
        Text botText = new Text(response);
        botText.setWrappingWidth(700); // Adjust to fit your content area
        botText.setFont(new javafx.scene.text.Font(14.0));

        ImageView botImage = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/Assets/botImage.png")));
        botImage.setFitHeight(44.0);
        botImage.setFitWidth(47.0);

        HBox hbox = new HBox(10, botImage, botText);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(10, 0, 10, 20)); // Adjust as needed

        ChatbotContainer.getChildren().add(hbox);
        scrollToBottom();
    }
    private List<Map<String, String>> conversationHistory = new ArrayList<>();

    public void sendMessage(String message) {
        addUserMessage(message);

        // Add user message to history
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", message);
        conversationHistory.add(userMessage);

        String response = getChatBotResponse(message);

        // Add assistant response to history
        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "assistant");
        assistantMessage.put("content", response);
        conversationHistory.add(assistantMessage);

        addBotResponse(response);
    }




    private String getChatBotResponse(String message) {
        try {
            Map<String, String> systemPrompt = new HashMap<>();
            systemPrompt.put("role", "system");
            systemPrompt.put("content", "You are an intelligent assistant embedded in an e-learning app designed to help parents of deaf individuals learn Tunisian Sign Language. Your primary function is to translate user queries into a series of sign language gestures and do not use icons only text answers.Your answers should be in the same language as the user for example if he says 'bonjou' repond en francais 'hello' en anglais ... . The responses should be provided in a format that is easily understandable by the user, utilizing their preferred language. For instance, if a user inquires about greetings, you should respond with a list of sign language gestures for greetings. It's important to note that your role is strictly limited to teaching Tunisian Sign Language, so if a user asks about unrelated topics, such as the weather only if he asks how to say it in sign language not about the weather, you should politely inform them that you are unable to assist with that request.");
            conversationHistory.add(systemPrompt);
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");

            // Construct the JSON body with the conversation history
            String jsonBody = "{\"model\":\"gpt-3.5-turbo\",\"messages\":" + new Gson().toJson(conversationHistory) + "}";

            RequestBody body = RequestBody.create(mediaType, jsonBody);
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer  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")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            System.out.println("Response body: " + responseBody);

            // Assuming the response format is correct and contains the expected fields
            String chatResponse = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            return chatResponse.trim();
        } catch (Exception e) {
            System.out.println(e);
            return "Sorry, I couldn't process your request.";
        }
    }


}
