package services.categorie;

import java.sql.SQLException;
import java.util.List;

public interface IServiceCategorie<Categorie> {

    Categorie getCategorieById(int id) throws SQLException;

    List<Categorie> searchByName(String nom) throws SQLException;

    void deleteCategorieWithSousCategories(int categoryId) throws SQLException;
}
