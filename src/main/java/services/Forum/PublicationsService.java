package services.Forum;

import entities.Publications;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationsService implements IForum<Publications> {
    private Connection connection;

    public PublicationsService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void addPublicationOrCommentaire(Publications publications) throws SQLException {
        String sql = "INSERT INTO publications (titre, contenu,images, date_creation,user_id) VALUES (?, ?,?, ?,?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, publications.getTitre());
        ps.setString(2, publications.getContenu());
        ps.setString(3, publications.getImages());
        ps.setTimestamp(4, new Timestamp(publications.getDate_creation().getTime()));
        ps.setInt(5, publications.getUserId());
        int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating publication failed, no rows affected.");
        }

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {

                publications.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating publication failed, no ID obtained.");
            }
        }
    }

    @Override
    public void updatePublicationOrCommentaire(Publications publications) throws SQLException {
        String sql = "UPDATE publications SET titre=?, contenu=?,images=?, date_creation=? , user_id=?  WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, publications.getTitre());
        ps.setString(2, publications.getContenu());
        ps.setString(3, publications.getImages());
        ps.setTimestamp(4, new Timestamp(publications.getDate_creation().getTime()));
        ps.setInt(5, publications.getUserId());
        ps.setInt(6, publications.getId());
        ps.executeUpdate();
    }

    @Override
    public void deletePublicationOrCommentaire(int id) throws SQLException {
        String sql = "DELETE FROM publications WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Publications getByIdPublicationOrCommentaire(int id) throws SQLException {
        String sql = "SELECT p.*, u.username FROM publications p JOIN user u ON p.user_id = u.id WHERE p.id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Publications publication = null;
        if (rs.next()) {
            publication = new Publications();
            CommentairesService cs = new CommentairesService();
            publication.setId(rs.getInt("id"));
            publication.setTitre(rs.getString("titre"));
            publication.setContenu(rs.getString("contenu"));
            publication.setImages(rs.getString("images"));
            publication.setDate_creation(rs.getTimestamp("date_creation"));
            publication.setUserId(rs.getInt("user_id"));
            publication.setUserName(rs.getString("username"));
            publication.setPubCommentaires(cs.getCommentairesByPublicationId(publication.getId()));
        }
        return publication;
    }


    public List<Publications> getAllPublications() throws SQLException {
        String sql = "SELECT p.*, u.username, " +
                "(SELECT COUNT(*) FROM reactions WHERE pub_id = p.id AND `jaime` = 1) AS jaime_count, " +
                "(SELECT COUNT(*) FROM reactions WHERE pub_id = p.id AND dislike = 1) AS dislike_count " +
                "FROM publications p JOIN user u ON p.user_id = u.id";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Publications> publications = new ArrayList<>();

        while (rs.next()) {
            Publications publication = new Publications();
            CommentairesService cs = new CommentairesService();
            publication.setId(rs.getInt("id"));
            publication.setTitre(rs.getString("titre"));
            publication.setContenu(rs.getString("contenu"));
            publication.setImages(rs.getString("images"));
            publication.setDate_creation(rs.getTimestamp("date_creation"));
            publication.setUserId(rs.getInt("user_id"));
            publication.setUserName(rs.getString("username"));
            publication.setJaime(rs.getInt("jaime_count"));
            publication.setDislike(rs.getInt("dislike_count"));
            publication.setPubCommentaires(cs.getCommentairesByPublicationId(publication.getId()));
            publications.add(publication);
        }
        return publications;
    }

    public List<Publications> searchPublicationsByTitle(String titre, int userId) throws SQLException {

        String sql = "SELECT * FROM publications WHERE titre LIKE ? AND user_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + titre + "%");
        ps.setInt(2, userId);
        ResultSet rs = ps.executeQuery();

        List<Publications> matchingPublications = new ArrayList<>();
        while (rs.next()) {
            Publications publication = new Publications();
            CommentairesService cs = new CommentairesService();
            publication.setId(rs.getInt("id"));
            publication.setTitre(rs.getString("titre"));
            publication.setContenu(rs.getString("contenu"));
            publication.setImages(rs.getString("images"));
            publication.setDate_creation(rs.getTimestamp("date_creation"));
            publication.setUserId(rs.getInt("user_id"));
            publication.setPubCommentaires(cs.getCommentairesByPublicationId(publication.getId()));

            matchingPublications.add(publication);
        }

        return matchingPublications;
    }
    public List<Publications> getPublicationsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM publications WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        List<Publications> userPublications = new ArrayList<>();
        while (rs.next()) {
            Publications publication = new Publications();
            CommentairesService cs = new CommentairesService();
            publication.setId(rs.getInt("id"));
            publication.setTitre(rs.getString("titre"));
            publication.setContenu(rs.getString("contenu"));
            publication.setImages(rs.getString("images"));
            publication.setDate_creation(rs.getTimestamp("date_creation"));
            publication.setUserId(rs.getInt("user_id"));
            publication.setPubCommentaires(cs.getCommentairesByPublicationId(publication.getId()));

            userPublications.add(publication);
        }

        return userPublications;
    }
    public boolean publicationExists(String titre, String contenu,int user_id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM publications WHERE titre = ? AND contenu = ? AND user_id=?";
        PreparedStatement ps= connection.prepareStatement(sql);
        ps.setString(1, titre);
        ps.setString(2, contenu);
        ps.setInt(3,user_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

}
