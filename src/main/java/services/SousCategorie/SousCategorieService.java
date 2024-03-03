package services.SousCategorie;


import dtos.SousCategorieDto;
import entities.SousCategorie;
import services.GServices;
import services.IService;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SousCategorieService extends GServices implements IService<SousCategorie>, IServiceSousCategorie<SousCategorie> {
    private Connection connection;
    private String tableName = "sous_categorie";
    private SousCategorieDto sousCategorie;

    public SousCategorieService(Connection connection, SousCategorie sousCcategorieDto) {
        connection = MyDatabase.getInstance().getConnection();
        sousCategorie = new SousCategorieDto();

    }

    @Override
    public int add(SousCategorie sousCategorie) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (nom, description, date_creation, status, category_id) VALUES(?,?,?,?,?)";

        PreparedStatement ps = prepareStatementWithGeneratedKeys(connection, sql);
        ps.setString(1, sousCategorie.getNom());
        ps.setString(2, sousCategorie.getDescription());
        ps.setDate(3, Date.valueOf(LocalDate.now()));
        ps.setString(4, sousCategorie.getStatus());
        ps.setInt(5, sousCategorie.getCategorieParente().getId());
        ps.executeUpdate();
        return getCurrentId(connection, ps);
    }

    @Override
    public void update(SousCategorie sousCategorie) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();
    }

    //retrieve all subcategories along with their parent categories.
    @Override
    public List<SousCategorie> getAll() throws SQLException {

        List<SousCategorie> sousCatList = new ArrayList<>();
        String query = "SELECT sc.id, sc.nom, sc.description, sc.status, sc.dateCreation, c.id, c.nom\n" +
                "FROM sous_categories sc\n" +
                "JOIN categories c ON sc.category_id = c.id\n";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            sousCatList.add(sousCategorie.single(resultSet));
        }
        return sousCatList;
    }

    @Override
    public SousCategorie getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<SousCategorie> getSousCategorieListForCategorie(int categorieId) throws SQLException {

        List<SousCategorie> subCategories = new ArrayList<>();

        // SQL query to select subcategories for the given category ID
        String query = "SELECT * FROM" + tableName + " WHERE categorie_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, categorieId);
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

}
