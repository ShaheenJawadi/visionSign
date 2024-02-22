package services.User;

import entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserServices<T> {
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> recuperer() throws SQLException;
    User getCurrent();
}
