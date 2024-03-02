package state;

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
    }

    public  User getCurrentUser() {
        return currentUser;
    }

    public  void clearSession() {
        currentUser = null;
    }

    public  boolean isUserLoggedIn() {
        return currentUser != null;
    }
}