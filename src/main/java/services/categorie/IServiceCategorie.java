package services.categorie;

import java.sql.SQLException;
import java.util.List;

public interface IServiceCategorie<Categorie> {

    Categorie getCategorieById(int id) throws SQLException;

    Categorie searchByName(String nom) throws SQLException;
}
