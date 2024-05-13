package services.Forum;

import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReactionsService {
    private Connection connection;

    public ReactionsService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public void react(int user_id, int pub_id, boolean jaime) throws SQLException {
        boolean existingReaction = checkExistingReaction(user_id, pub_id);

        if (existingReaction) {
            updateReaction(user_id, pub_id, jaime);
        } else {
            insertReaction(user_id, pub_id, jaime);
        }
    }

    public boolean checkExistingReaction(int user_id, int pub_id) throws SQLException {
        String query = "SELECT * FROM reactions WHERE pub_id = ? AND user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, pub_id);
        preparedStatement.setInt(2, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public void updateReaction(int user_id, int pub_id, boolean jaime) throws SQLException {
        String query = "UPDATE reactions SET jaime = ?, dislike = ? WHERE pub_id = ? AND user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, jaime ? 1 : 0);
        preparedStatement.setInt(2, jaime ? 0 : 1);
        preparedStatement.setInt(3, pub_id);
        preparedStatement.setInt(4, user_id);
        preparedStatement.executeUpdate();
    }

    public void insertReaction(int user_id, int pub_id, boolean jaime) throws SQLException {
        String query = "INSERT INTO reactions (pub_id, user_id, jaime, dislike) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, pub_id);
        preparedStatement.setInt(2, user_id);
        preparedStatement.setInt(3, jaime ? 1 : 0);
        preparedStatement.setInt(4, jaime ? 0 : 1);
        preparedStatement.executeUpdate();
    }

    public void reactLike(int user_id, int pub_id) throws SQLException {
        react(user_id, pub_id, true);
    }

    public void reactDislike(int user_id, int pub_id) throws SQLException {
        react(user_id, pub_id, false);
    }

    public void removeReaction(int user_id, int pub_id) throws SQLException {
        String query = "DELETE FROM reactions WHERE pub_id = ? AND user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, pub_id);
        preparedStatement.setInt(2, user_id);
        preparedStatement.executeUpdate();
    }

    public int getLikesCount(int pub_id) throws SQLException {
        String query = "SELECT COUNT(*) as totalLikes FROM reactions WHERE pub_id = ? AND jaime = 1";
        return getCount(query, pub_id);
    }

    public int getDislikesCount(int pub_id) throws SQLException {
        String query = "SELECT COUNT(*) as totalDislikes FROM reactions WHERE pub_id = ? AND dislike = 1";
        return getCount(query, pub_id);
    }

    private int getCount(String query, int pub_id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, pub_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public boolean isLikedByUser(int user_id, int pub_id) throws SQLException {
        String query = "SELECT jaime FROM reactions WHERE pub_id = ? AND user_id = ?";
        return checkReaction(query, user_id, pub_id);
    }

    public boolean isDislikedByUser(int user_id, int pub_id) throws SQLException {
        String query = "SELECT dislike FROM reactions WHERE pub_id = ? AND user_id = ?";
        return checkReaction(query, user_id, pub_id);
    }

    private boolean checkReaction(String query, int user_id, int pub_id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, pub_id);
        preparedStatement.setInt(2, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() && resultSet.getInt(1) == 1;
    }
}
