package services;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    int add(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> getAll() throws SQLException;

    T getById(int id) throws SQLException ;





}
