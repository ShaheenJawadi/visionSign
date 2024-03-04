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

    public void addLike(int user_id,int pub_id,int jaime,int dislike) throws SQLException {
        String query = "SELECT * FROM reactions WHERE pub_id = ? AND user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, pub_id);
        preparedStatement.setInt(2, user_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    updateLike(pub_id, user_id, jaime, dislike);
                } else {
                    insertLike(pub_id, user_id, jaime, dislike);
                }
            }
    }


    private void updateLike(int pub_id, int user_id, int jaime, int dislike) throws SQLException {
        String query = "UPDATE reactions SET jaime = ?, dislike = ? WHERE pub_id = ? AND user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, jaime);
            preparedStatement.setInt(2, dislike);
            preparedStatement.setInt(3, pub_id);
            preparedStatement.setInt(4, user_id);
            preparedStatement.executeUpdate();

    }

    private void insertLike(int pub_id, int user_id, int jaime, int dislike) throws SQLException {
        String query = "INSERT INTO reactions (pub_id, user_id, jaime, dislike) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pub_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, jaime);
            preparedStatement.setInt(4, dislike);
            preparedStatement.executeUpdate();
        }
    }
    public boolean isLikedByUser(int user_id, int pub_id) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM reactions WHERE pub_id = ? AND user_id = ? AND jaime = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pub_id);
            preparedStatement.setInt(2, user_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        }
        return false;
    }
    public int getLikesCount(int pub_id) throws SQLException {
        String query = "SELECT SUM(jaime) as totalLikes FROM reactions WHERE pub_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pub_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("totalLikes");
                }
            }
        }
        return 0;
    }
    public int getDislikesCounts(int pub_id) throws SQLException {
        String query = "SELECT SUM(dislike) as totalDislikes FROM reactions WHERE pub_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pub_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("totalDislikes");
                }
            }
        }
        return 0;
    }

    public boolean isDislikedByUser(int user_id, int pub_id) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM reactions WHERE pub_id = ? AND user_id = ? AND dislike = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pub_id);
            preparedStatement.setInt(2, user_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        }
        return false;
    }

}
