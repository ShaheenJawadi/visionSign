package services.cours;

import java.sql.SQLException;
import java.util.List;

public interface IServiceCours<T,R ,L>  {


    List<T> search(String term) throws SQLException;

    List<T> getBySubCategory(int id) throws SQLException;

    List<T> getByNiveau(int id) throws SQLException;

    List<T> getAllWithPagination(int page , int rowsNum) throws SQLException;

    List<R> getRessouces(int coursId) throws SQLException;

    List<L> getLessons(int coursId) throws SQLException;


}