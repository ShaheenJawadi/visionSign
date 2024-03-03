package services.Forum;

import entities.Commentaires;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentairesService implements IForum<Commentaires> {
    private Connection connection;

    public CommentairesService() {
        this.connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void addPublicationOrCommentaire(Commentaires commentaires) throws SQLException {
        String sql = "INSERT INTO commentaires (commentaire, date, id_pub, user_id) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, commentaires.getCommentaire());
        ps.setTimestamp(2, new Timestamp(commentaires.getDate().getTime()));
        ps.setInt(3, commentaires.getId_pub());
        ps.setInt(4, commentaires.getUserId());
        int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating comment failed, no rows affected.");
        }

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                commentaires.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating comment failed, no ID obtained.");
            }
        }    }

    @Override
    public void updatePublicationOrCommentaire(Commentaires commentaires) throws SQLException {
        String sql = "UPDATE commentaires SET commentaire=?, date=?, id_pub=?, user_id=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, commentaires.getCommentaire());
        ps.setTimestamp(2, new Timestamp(commentaires.getDate().getTime()));
        ps.setInt(3, commentaires.getId_pub());
        ps.setInt(4, commentaires.getUserId());
        ps.setInt(5, commentaires.getId());
        ps.executeUpdate();
    }

    @Override
    public void deletePublicationOrCommentaire(int id) throws SQLException {
        String sql = "DELETE FROM commentaires WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Commentaires getByIdPublicationOrCommentaire(int id) throws SQLException {
        String sql = "SELECT * FROM commentaires WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Commentaires commentaire = null;
        if (rs.next()) {
            commentaire = new Commentaires();
            commentaire.setId(rs.getInt("id"));
            commentaire.setCommentaire(rs.getString("commentaire"));
            commentaire.setDate(rs.getTimestamp("date"));
            commentaire.setId_pub(rs.getInt("id_pub"));
            commentaire.setUserId(rs.getInt("user_id"));
        }
        return commentaire;
    }

    public List<Commentaires> getCommentairesByPublicationId(int id_pub) throws SQLException {

        String sql = "SELECT c.*, u.username FROM commentaires c JOIN user u ON c.user_id = u.id WHERE c.id_pub = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id_pub);
        ResultSet rs = ps.executeQuery();

        List<Commentaires> commentaires = new ArrayList<>();
        while (rs.next()) {
            Commentaires commentaire = new Commentaires();
            commentaire.setId(rs.getInt("id"));
            commentaire.setCommentaire(rs.getString("commentaire"));
            commentaire.setDate(rs.getTimestamp("date"));
            commentaire.setId_pub(rs.getInt("id_pub"));
            commentaire.setUserId(rs.getInt("user_id"));
            commentaire.setUserName(rs.getString("username"));
            commentaires.add(commentaire);
        }
        return commentaires;
    }
    public boolean commentaireExists(String commentaire, int id_pub,int user_id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM commentaires WHERE commentaire = ? AND id_pub = ? AND user_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, commentaire);
        ps.setInt(2, id_pub);
        ps.setInt(3,user_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

}
