package services.SousCategorie;

import entities.SousCategorie;

import java.sql.SQLException;
import java.util.List;

public interface IServiceSousCategorie<SousCategorie> {
    List<SousCategorie> getSousCategorieListForCategorie(int categorieID) throws SQLException;
}
