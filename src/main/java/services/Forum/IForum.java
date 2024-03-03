package services.Forum;


import java.sql.SQLException;
import java.util.List;

public interface IForum<T>{
    void addPublicationOrCommentaire(T t)throws SQLException;
    void updatePublicationOrCommentaire(T t)throws SQLException;
    void deletePublicationOrCommentaire(int id)throws SQLException;
    T getByIdPublicationOrCommentaire(int id)throws SQLException;
}

