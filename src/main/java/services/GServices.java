package services;

import java.sql.*;

public class GServices {


    public int getCurrentId(Connection connection, PreparedStatement ps) {
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                return id;
            } else {
                throw new SQLException("Inserting data failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement prepareStatementWithGeneratedKeys(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

}
