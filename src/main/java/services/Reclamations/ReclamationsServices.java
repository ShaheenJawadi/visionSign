package services.Reclamations;

import entities.Reclamations;
import services.IService;
import utils.MyDatabase;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReclamationsServices implements IServices<Reclamations> {

    private static Connection connection;

    public ReclamationsServices() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public static final String[] TYPES = {"cours", "note", "certificat","autre"}; // Ajoutez vos différents types

    public void ajouterRec(Reclamations reclamations, String selectedType) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlCurrentDate = java.sql.Date.valueOf(currentDate);

        String sql = "insert into reclamations (type,description,status,date,id_user) values(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, selectedType); // Utilisez le type sélectionné
            ps.setString(2, reclamations.getDescription());
            ps.setString(3, reclamations.getStatus());
            ps.setDate(4, sqlCurrentDate);
            ps.setInt(5, reclamations.getId_user());
            ps.executeUpdate();
        }
    }
    @Override
    public  void ajouter(Reclamations reclamations) throws SQLException {
        LocalDate currentDate = LocalDate.now();

        // Convertissez la date actuelle en java.sql.Date
        java.sql.Date sqlCurrentDate = java.sql.Date.valueOf(currentDate);

        // Utilisez la date actuelle pour l'insertion dans la base de données
        String sql = "insert into reclamations (type,description,status,date,id_user) values(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reclamations.getType());
            ps.setString(2, reclamations.getDescription());
            ps.setString(3, reclamations.getStatus());
            ps.setDate(4, sqlCurrentDate); // Utilisez la date actuelle
            ps.setInt(5, reclamations.getId_user());
            ps.executeUpdate();
        }
    }


    @Override
    public void modifier(Reclamations reclamations) throws SQLException {
        String sql="update reclamations set type=?,description=?,status=?,date=? where id_reclamation=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setString(1,reclamations.getType());
        ps.setString(2,reclamations.getDescription());
        ps.setString(3,reclamations.getStatus());
        ps.setDate(4,reclamations.getDate());
        ps.setInt(5,reclamations.getId_reclamation());

        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM reclamations WHERE id_reclamation= ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Reclamations> recuperer() throws SQLException {
        String sql="select * from reclamations";
        Statement st=connection.createStatement();
        ResultSet rs=st.executeQuery(sql);
        List<Reclamations> reclamations=new ArrayList<>();
        while (rs.next()){
            Reclamations p=new Reclamations();
            p.setId_reclamation(rs.getInt("id_reclamation"));
            p.setType(rs.getString("type"));
            p.setDescription(rs.getString("description"));
            p.setStatus(rs.getString("status"));
            p.setDate(rs.getDate("date"));
            p.setRepondre(rs.getBoolean("repondre"));
            p.setId_user(rs.getInt("id_user"));
            reclamations.add(p);

        }
        return reclamations;
    }
    public List<Reclamations> searchreclamationbyType(String type, int userId) throws SQLException {

        String sql = "SELECT * FROM reclamations WHERE type LIKE ? AND id_user=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + type + "%");
        ps.setInt(2, userId);
        ResultSet rs = ps.executeQuery();

        List<Reclamations> matchingPublications = new ArrayList<>();
        while (rs.next()) {
            Reclamations publication = new Reclamations();
            publication.setId_reclamation(rs.getInt("id_reclamation"));
            publication.setType(rs.getString("type"));
            publication.setDescription(rs.getString("description"));
            publication.setStatus(rs.getString("status"));
            publication.setDate(rs.getDate("date"));
            publication.setId_user(rs.getInt("id_user"));

            matchingPublications.add(publication);
        }

        return matchingPublications;
    }

    public List<Reclamations> getReclamationsById(int id) throws SQLException {
        String sql = "SELECT * FROM reclamations WHERE id_reclamation=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Reclamations reclamations =new Reclamations();
        if (rs.next()) {
            reclamations.setId_reclamation(rs.getInt("id_reclamation"));
            reclamations.setType(rs.getString("type"));
            reclamations.setDescription(rs.getString("description"));
            reclamations.setStatus(rs.getString("status"));
            reclamations.setDate(rs.getDate("date"));
            reclamations.setRepondre(rs.getBoolean("repondre"));
            reclamations.setId_user(rs.getInt("id_user"));
        }
        return (List<Reclamations>) reclamations;
    }
    public Reclamations getReclmationsById(int id) throws SQLException {
        Connection connection = MyDatabase.getInstance().getConnection();
        String query = "SELECT * FROM reclamations WHERE id_reclamation = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Reclamations reclamations = null; // Initialise reclamations à null
        if (rs.next()) {
            reclamations = new Reclamations();
            reclamations.setId_reclamation(rs.getInt("id_reclamation"));
            reclamations.setType(rs.getString("type"));
            reclamations.setDescription(rs.getString("description"));
            reclamations.setStatus(rs.getString("status"));
            reclamations.setDate(rs.getDate("date"));
            reclamations.setRepondre(rs.getBoolean("repondre"));
            reclamations.setId_user(rs.getInt("id_user"));
            // set other properties
        }
        connection.close();
        return reclamations;
    }
    public List<Integer> recupererIdsUtilisateurs() throws SQLException {
        List<Integer> idsUtilisateurs = new ArrayList<>();
        String query = "SELECT id FROM user"; // Supposons que votre table d'utilisateurs s'appelle 'utilisateurs'

        try (PreparedStatement statement = MyDatabase.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idUtilisateur = resultSet.getInt("id");
                idsUtilisateurs.add(idUtilisateur);
            }
        }
        return idsUtilisateurs;
    }
}
