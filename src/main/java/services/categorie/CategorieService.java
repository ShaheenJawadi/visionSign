package services.categorie;

import dtos.CategorieDto;
import entities.Categorie;
import entities.SousCategorie;
import services.GServices;
import services.IService;
import services.SousCategorie.SousCategorieService;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService extends GServices implements IService<Categorie>, IServiceCategorie<Categorie> {

    private Connection connection;
    private String tableName = "categorie";

    private CategorieDto categorieDto;
    private SousCategorieService sousCatService;


    public CategorieService(Connection connection, CategorieDto categorieDto) {
        connection = MyDatabase.getInstance().getConnection();
        categorieDto = new CategorieDto();

    }

    @Override
    public int add(Categorie categorie) throws SQLException {

        String sql = "INSERT INTO " + tableName + " (nom, description) VALUES(?,?)";

        PreparedStatement ps = prepareStatementWithGeneratedKeys(connection, sql);
        ps.setString(1, categorie.getNom());
        ps.setString(2, categorie.getDescription());
        ps.executeUpdate();
        return getCurrentId(connection, ps);

    }

    @Override
    public void update(Categorie categorie) throws SQLException {
        String sql = "update " + tableName + " set  nom=?, description=? where id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, categorie.getNom());
        ps.setString(2, categorie.getDescription());
        ps.setInt(3, categorie.getId());
        ps.executeUpdate();
    }

    @Override
    public void deleteCategorieWithSousCategories(int categoryId) throws SQLException {
        // Delete subcategories
        deleteSousCategories(categoryId);

        // Delete category
        delete(categoryId);
    }

    public void deleteSousCategories(int categoryId) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE category_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, categoryId);
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM " + tableName + " WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();
    }

    @Override
    public List<Categorie> getAll() throws SQLException {

        String sql = "select * from " + tableName;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return categorieDto.list(rs);
    }

    @Override
    public Categorie getById(int id) throws SQLException {
        return null;
    }

    @Override
    public Categorie getCategorieById(int id) throws SQLException {
        Categorie c = new Categorie();
        String sql = "select * from " + tableName + " where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            c = categorieDto.single(rs);
            //getList of sous category per category
            List<SousCategorie> sousCategories = sousCatService.getSousCategorieListForCategorie(c.getId());
            c.setSousCategorieListe(sousCategories);
            return c;

        } else {
            return null;
        }
    }

    @Override
    public List<Categorie> searchByName(String nom) throws SQLException {
        List<Categorie> categories = new ArrayList<>();
        //LIKE operator with % wildcard characters to match any part of the category name.
        // For example, if nom is "book", the query will match categories with names like "Bookstore",
        // "eBook", "Bookshelf"
        String sql = "select * from " + tableName + "  WHERE nom LIKE ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + nom + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categorie categorie = categorieDto.single(rs);
            categories.add(categorie);
        }
        return categories;
    }
}
