package services.SousCategorie;

import dtos.SousCategorieDto;
import entities.SousCategorie;
import services.Categorie.CategorieService;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SousCategorieService implements ISousCategorieService<SousCategorie> {
    private Connection connection;
    private String tableName = "sous_categorie";

    private SousCategorieDto sousCategorieDto;
    private CategorieService categorieService;


    public SousCategorieService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void addSousCategorie(SousCategorie sousCategorie) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (nom, description, date_creation, status, category_id) VALUES(?,?,?,?,?)";

        PreparedStatement ps = prepareStatementWithGeneratedKeys(connection, sql);
        ps.setString(1, sousCategorie.getNom());
        ps.setString(2, sousCategorie.getDescription());
        ps.setDate(3, Date.valueOf(LocalDate.now()));
        ps.setString(4, sousCategorie.getStatus());
        ps.setInt(5, sousCategorie.getcategorieId());
        ps.executeUpdate();
    }

    //everytime a subcategory is updated, the lastUpdated attribute of the parentCategory is updated
    @Override
    public void updateSousCategorie(SousCategorie sousCategorie) throws SQLException {


        CategorieService catSer = new CategorieService();
        catSer.updateLastUpdatedTime(sousCategorie.getcategorieId());
    }

    @Override
    public void deleteSousCategorie(int id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();
    }

    @Override
    public void deleteSousCategoryByCategoryId(int id) throws SQLException {

    }

    @Override
    public SousCategorie getSousCategorieById(int id) throws SQLException {
        String sql = "select * from " + tableName + " where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return sousCategorieDto.getSousCategorie(rs);
        } else {
            return null;
        }
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

    public PreparedStatement prepareStatementWithGeneratedKeys(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}
