package services.categorie;

import dtos.CategorieDto;
import entities.Categorie;
import services.GServices;
import services.IService;
import utils.MyDatabase;

import java.sql.*;
import java.util.List;

public class CategorieService extends GServices implements IService<Categorie>, IServiceCategorie<Categorie> {

    private Connection connection;
    private String tableName = "categorie";

    private CategorieDto categorieDto;


    public CategorieService(Connection connection, CategorieDto categorieDto) {
        connection = MyDatabase.getInstance().getConnection();
        categorieDto = categorieDto;

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
        String sql = "select * from " + tableName + " where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();


        if (rs.next()) {
            return categorieDto.single(rs);
        } else {
            return null;
        }
    }

    @Override
    public Categorie searchByName(String nom) throws SQLException {
        String sql = "select * from " + tableName + " where nom = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();

        return categorieDto.single(rs);

    }
}
