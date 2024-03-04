package apiUtils;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FacebookOAuth2 {

    private static final String APP_ID = "2723155767849688";
    private static final String APP_SECRET = "adc1008786aa3aabc19254788782b706";
    private static final String REDIRECT_URL = "http://localhost:8080";
    private static final BlockingQueue<String> codeQueue = new ArrayBlockingQueue<>(1);
    private  static HttpServer localServer;

    public static String getLoginDialogUrl() {
        ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.addPermission(FacebookPermissions.EMAIL);
        scopeBuilder.addPermission(FacebookPermissions.PUBLIC_PROFILE);


        FacebookClient client = new DefaultFacebookClient(Version.LATEST);
        return client.getLoginDialogUrl(APP_ID, REDIRECT_URL, scopeBuilder);
    }

    public static User getUserFromAccessToken(String accessToken) {
        FacebookClient client = new DefaultFacebookClient(accessToken, APP_SECRET, Version.LATEST);
        return client.fetchObject("me", User.class);
    }

    public static void startLocalServer() throws IOException {
        int startingPort = 8080;
        int maxAttempts = 10;
        int currentPort = -1;

        for (int i = 0; i < maxAttempts; i++) {
            currentPort = startingPort + i;
            try {
                localServer = HttpServer.create(new InetSocketAddress(currentPort), 0);
                localServer.createContext("/", exchange -> {
                    try {
                        String query = exchange.getRequestURI().getQuery();
                        String code = query.substring(query.indexOf("code=") + 5);
                        System.out.println("Received code: " + code);
                        System.out.println("Before offering code to queue. Queue size: " + codeQueue.size());

                        // Check the result of the offer operation
                        if (!codeQueue.offer(code)) {
                            System.out.println("Failed to offer code to the queue. Queue may be full.");
                        }

                        System.out.println("After offering code to queue. Queue size: " + codeQueue.size());

                        String response = "You can close this window now.";
                        exchange.sendResponseHeaders(200, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                localServer.setExecutor(null); // Use the default executor for simplicity
                localServer.start();
                System.out.println("Server started on port " + currentPort);
                break; // Exit the loop if the server is successfully started
            } catch (BindException e) {
                System.out.println("Port " + currentPort + " is already in use. Trying another port...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public static void stopLocalServer() {
        if (localServer != null) {
            localServer.stop(0);
            System.out.println("Local server stopped.");
        }
    }
    public static String exchangeCodeForAccessToken(String code) {
        String appIdAndSecret = APP_ID + "|" + APP_SECRET;

        FacebookClient.AccessToken accessToken = new DefaultFacebookClient(Version.LATEST)
                .obtainUserAccessToken(APP_ID, appIdAndSecret, REDIRECT_URL, code);

        return accessToken.getAccessToken();
    }

    public static String waitForCode() throws InterruptedException {
        try {
            System.out.println("Queue size before taking code: " + codeQueue.size());
            String code = codeQueue.take();
            System.out.println("Queue size after taking code: " + codeQueue.size());
            return code  ;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception after logging
        }
    }
}
