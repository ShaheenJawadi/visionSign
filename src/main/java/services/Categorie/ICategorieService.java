package services.Categorie;

import java.sql.SQLException;
import java.util.List;

public interface ICategorieService<T, S> {
    void addCategorie(T t) throws SQLException;

    void updateCategorie(T t) throws SQLException;

    void deleteCategorie(int id) throws SQLException;

    T getCategorieById(int id) throws SQLException;
    List<S> getSousCategorieListByCategoryId(int id) throws SQLException;

    List<T> searchLikeNameCategory(String name) throws SQLException;

    T updateCategoryImage(String image) throws SQLException;

    List<T> sortCategoryListByAttribute(String attribute) throws SQLException;


}
