package services.SousCategorie;

import dtos.CategorieDto;
import dtos.SousCategorieDto;
import entities.SousCategorie;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SousCategorieService implements ISousCategorieService<SousCategorie> {
    private Connection connection;
    private String tableName = "sous_categorie";

    private SousCategorieDto sousCategorieDto;


    public SousCategorieService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void addSousCategorie(SousCategorie sousCategorie) throws SQLException {

    }

    @Override
    public void updateSousCategorie(SousCategorie sousCategorie) throws SQLException {

    }

    @Override
    public void deleteSousCategorie(int id) throws SQLException {

    }

    @Override
    public void deleteSousCategoryByCategoryId(int id) throws SQLException {

    }

    @Override
    public SousCategorie getSousCategorieById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<SousCategorie> getSousCategorieListByCategoryId(int categorie_id) throws SQLException {
        List<SousCategorie> subCategories = new ArrayList<>();

        // SQL query to select subcategories for the given category ID
        String query = "SELECT * FROM" + tableName + " WHERE categorie_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, categorie_id);
        ResultSet resultSet = statement.executeQuery();

        // Iterate through the ResultSet and create SubCategorie objects
        while (resultSet.next()) {
            SousCategorie subCategory = new SousCategorie();
            subCategory.setId(resultSet.getInt("id"));
            subCategory.setNom(resultSet.getString("nom"));
            subCategory.setDescription(resultSet.getString("description"));
            subCategory.setDateCreation(resultSet.getDate("date_creation"));
            subCategory.setStatus(resultSet.getString("status"));
            subCategories.add(subCategory);
        }

        return subCategories;

    }


    @Override
    public List<SousCategorie> searchLikeNameCategory(String name) throws SQLException {
        return null;
    }

    @Override
    public void updateSousCategorieStatus(String status) throws SQLException {

    }

    @Override
    public List<SousCategorie> getSousCategoriesListByStatusAndCategoryId(String status, int categorieId) throws SQLException {
        return null;
    }

    @Override
    public List<SousCategorie> sortSousCategoryListByAttribute(String attribute) throws SQLException {
        return null;
    }
}
