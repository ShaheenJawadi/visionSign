package test;

import apiUtils.FacebookOAuth2;
import apiUtils.MailerAPI;
import com.restfb.types.User;
import entities.UserRole;
import services.User.LevelService;
import services.User.UserService;

import javax.mail.MessagingException;
import java.awt.*;
import java.net.URI;
import java.sql.SQLException;

public class UserMain {
    public static void main(String[] args)  {
        try {
            // Start the local server to handle the OAuth callback
            FacebookOAuth2.startLocalServer();
            System.out.println("aaaaaaaaaaaaa");

            // Get the login dialog URL and open it in the user's browser
            String loginUrl = FacebookOAuth2.getLoginDialogUrl();
            System.out.println(loginUrl);
            System.out.println("bbbbbbbbbbbbbb");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(loginUrl));
            } else {
                System.err.println("Desktop is not supported. Please open the following URL in your browser:");
                System.out.println(loginUrl);
            }
            System.out.println("ccccccccccccc");

            // Wait for the authorization code
            String code = FacebookOAuth2.waitForCode();

            System.out.println(code);

            // Exchange the code for an access token
            String accessToken = FacebookOAuth2.exchangeCodeForAccessToken(code);
            System.out.println(accessToken);

            // Fetch the user's profile information
            User user = FacebookOAuth2.getUserFromAccessToken(accessToken);

            // Use the user's profile information in your application
            System.out.println("User ID: " + user.getId());
            System.out.println("User Name: " + user.getName());
            System.out.println("User Email: " + user.getEmail());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Stop the local server
            FacebookOAuth2.stopLocalServer();
        }
    }

}
