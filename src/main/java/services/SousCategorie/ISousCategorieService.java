package services.SousCategorie;

import entities.SousCategorie;

import java.sql.SQLException;
import java.util.List;

public interface ISousCategorieService<T> {

    void addSousCategorie(T t) throws SQLException;

    void updateSousCategorie(T t) throws SQLException;

    void deleteSousCategorie(int id) throws SQLException;
    void deleteSousCategoryByCategoryId (int id) throws SQLException;

    T getSousCategorieById(int id) throws SQLException;
    List<T> getSousCategorieListByCategoryId (int categorie_id) throws SQLException;

    List<T> searchLikeNameCategory(String name) throws SQLException;

    void updateSousCategorieStatus(String status, int id) throws SQLException;

    List<T> getSousCategoriesListByStatusAndCategoryId(String status, int categorieId) throws SQLException;

    List<T> getSousCategorieList() throws SQLException;
    List<T> sortSousCategoryListByAttribute(String attribute) throws SQLException;

}
