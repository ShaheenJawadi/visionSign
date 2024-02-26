package test;

import entities.User;
import entities.UserRole;
import services.User.LevelService;
import services.User.UserService;

import java.sql.SQLException;

public class UserMain {
    public static void main(String[] args) {
        UserService us=new UserService();
        LevelService ls=new LevelService();
        try {
            us.ajouter(new User("guezmir","iyed","houssi","houssi@eprit.tn","aokaok", UserRole.ADMIN));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
