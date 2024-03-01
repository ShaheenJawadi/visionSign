package apiUtils;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GoogleOAuth2 {

    private static final String APPLICATION_NAME = "visionSignAcademy";
    private static final String CLIENT_SECRETS_FILE = "/clientSecret.json";
    private static final String CREDENTIALS_DIRECTORY = "C:\\Users\\Iyed\\Downloads";
    private static final List<String> SCOPES = Arrays.asList("openid", "profile", "email");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static AuthorizationCodeFlow initializeFlow() throws Exception {
        InputStreamReader clientSecretReader = new InputStreamReader(GoogleOAuth2.class.getResourceAsStream(CLIENT_SECRETS_FILE));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

        return new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                SCOPES)
                //.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_DIRECTORY)))
                .setAccessType("offline")
                .build();
    }

    public static String buildAuthorizationUrl() throws Exception {
        AuthorizationCodeFlow flow = initializeFlow();
        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
        return authorizationUrl.setRedirectUri("urn:ietf:wg:oauth:2.0:oob").build();
    }

    public static Credential exchangeCodeForToken(String authorizationCode) throws Exception {
        AuthorizationCodeFlow flow = initializeFlow();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");
    }


}
