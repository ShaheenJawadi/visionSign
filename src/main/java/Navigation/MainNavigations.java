package Navigation;
 
public class MainNavigations {
    private static MainNavigations instance;
    private boolean userLoggedIn = false;
    private String currentUser;
    private String currentUserRole;

    private MainNavigations() {
        // Private constructor to prevent instantiation from outside
    }

    public static MainNavigations getInstance() {
        if (instance == null) {
            instance = new MainNavigations();
        }
        return instance;
    }

    public void loginUser(String username, String role) {
        userLoggedIn = true;
        currentUser = username;
        currentUserRole = role;
    }

    public void logoutUser() {
        userLoggedIn = false;
        currentUser = null;
        currentUserRole = null;
    }

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public void setAuthenticatedUser(String username, String role) {
        loginUser(username, role);
    }
}