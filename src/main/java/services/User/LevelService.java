package services.User;

import entities.Level;
import entities.User;
import entities.UserLevel;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LevelService implements IUserServices<Level>{
    private Connection connection;

    public LevelService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Level level) throws SQLException {
        String sql = "INSERT INTO level (niveau) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, level.getNiveau().name().toUpperCase());
        ps.executeUpdate();
    }


    @Override
    public void modifier(Level level) throws SQLException {
        String sql = "update level set niveau= ? where id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, level.getNiveau().name().toUpperCase());
        ps.setInt(2, level.getId());
        ps.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM level WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

    }


    @Override
    public List<Level> recuperer() throws SQLException {
        String sql = "select * from level";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Level> levels = new ArrayList<>();
        while (rs.next()) {
            Level l = new Level();
            l.setId(rs.getInt("id"));
            l.setNiveau(UserLevel.valueOf(rs.getString("niveau")));


            levels.add(l);
        }
        return levels;
    }

    @Override
    public User getCurrent() {
        return null;
    }

    public int getLevelIdByName(String levelName) throws SQLException {
        int levelId = -1; // Default value if not found

        String sql = "SELECT id FROM level WHERE niveau = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, levelName.toUpperCase());

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    levelId = resultSet.getInt("id");
                }
            }
        }

        return levelId;
    }
    public UserLevel getNiveauById(int levelId) throws SQLException {

        String sql = "SELECT niveau FROM level WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, levelId);

        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return UserLevel.valueOf(resultSet.getString("niveau"));
        }
        return  null;


    }
}

