package State;

import State.MainNavigations;
import entities.User;

public class UserSessionManager {
    private  User currentUser;
    private static   UserSessionManager instance;


    public static UserSessionManager getInstance() {
         if (instance==null)
             instance=new UserSessionManager();
         return instance;
    }

    public  void setCurrentUser(User user) {


        currentUser = user;
        MainNavigations.getInstance().manageHeaderAuth();
    }

    public  User getCurrentUser() {
        return currentUser;


    }

    public  void clearSession() {
        currentUser = null;
        MainNavigations.getInstance().manageHeaderAuth();
    }

    public  boolean isUserLoggedIn() {
        return currentUser != null;
    }
}