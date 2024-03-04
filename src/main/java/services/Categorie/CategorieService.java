package services.Categorie;

import dtos.CategorieDto;
import entities.Categorie;
import entities.SousCategorie;
import services.SousCategorie.SousCategorieService;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements ICategorieService<Categorie, SousCategorie> {
    private Connection connection;

    private String tableName = "categorie";

    private CategorieDto categorieDto;

    private SousCategorieService sousCategorieService;

    public CategorieService() {
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public void addCategorie(Categorie categorie) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (nom, description, last_updated, image, nbSousCategorie) VALUES(?,?,?,?,?)";

        PreparedStatement ps = prepareStatementWithGeneratedKeys(connection, sql);
        ps.setString(1, categorie.getNom());
        ps.setString(2, categorie.getDescription());
        ps.setDate(3, Date.valueOf(LocalDate.now()));
        ps.setString(4, categorie.getImage());
        ps.setInt(4, 0);
        ps.executeUpdate();
    }

    @Override
    public void updateCategorie(Categorie categorie) throws SQLException {
        String sql = "update " + tableName + " set  nom=?, description=?, last_updated=?, image=? where id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, categorie.getNom());
        ps.setString(2, categorie.getDescription());
        ps.setDate(3, Date.valueOf(LocalDate.now()));
        ps.setString(4, categorie.getImage());
        ps.setInt(5, categorie.getId());
        ps.executeUpdate();
    }

    //deleteing a category will delete all its children subcategories
    @Override
    public void deleteCategorie(int id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();
        sousCategorieService.deleteSousCategoryByCategoryId(id);

    }


    @Override
    public Categorie getCategorieById(int id) throws SQLException {
        String sql = "select * from " + tableName + " where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return categorieDto.getCategorieWithSousCategories(rs);
        } else {
            return null;
        }
    }

    @Override
    public List<Categorie> getAllCategories() throws SQLException {
        String sql = "select * from " + tableName;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return categorieDto.list(rs);
    }

    @Override
    public List<SousCategorie> getSousCategorieListByCategoryId(int id) throws SQLException {
        String sql = "select * from " + tableName + " where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Categorie c = categorieDto.getCategorieWithSousCategories(rs);
            //getList of sous category per category
            return c.getSousCategorieListe();

        } else {
            return null;
        }
    }

    @Override
    public List<Categorie> searchLikeNameCategory(String name) throws SQLException {
        List<Categorie> categories = new ArrayList<>();
        //LIKE operator with % wildcard characters to match any part of the category name.
        // For example, if nom is "book", the query will match categories with names like "Bookstore",
        // "eBook", "Bookshelf"
        String sql = "select * from " + tableName + "  WHERE nom LIKE ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + name + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categorie categorie = categorieDto.getCategorie(rs);
            categories.add(categorie);
        }
        return categories;
    }


    @Override
    public void updateCategoryImage(String image, int id) throws SQLException {
        String sql = "update " + tableName + " set image=? where id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, image);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    @Override
    public void updateLastUpdatedTime(int id) throws SQLException {
        String sql = "update " + tableName + " set last_updated=? where id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(LocalDate.now()));
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    @Override
    public List<Categorie> sortCategoryListByAttribute(String attribute) throws SQLException {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " ORDER BY " + attribute + " ASC;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categorie categorie = categorieDto.getCategorie(rs);
            categories.add(categorie);
        }
        return categories;
    }

    public PreparedStatement prepareStatementWithGeneratedKeys(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

}
