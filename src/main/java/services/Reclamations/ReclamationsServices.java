package services.Reclamations;

import entities.Reclamations;
import utils.MyDatabase;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReclamationsServices implements IServices<Reclamations>{

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
    //    public Map<String, Integer> compterReclamationsParReponse() throws SQLException {
//        Map<String, Integer> stats = new HashMap<>();
//        String query = "SELECT repondre COUNT(*) as count FROM Reclamations GROUP BY repondre";
//
//        try (Connection connection = (Connection) MyDatabase.getInstance().getConnection().prepareStatement(query)){
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    while (resultSet.next()) {
//                        Boolean reponse = Boolean.valueOf(resultSet.getString("repondre"));
//                        int count = resultSet.getInt("count");
//                        stats.put(String.valueOf(reponse), count);
//                    }
//                }
//            }
//        }
//
//        return stats;
//    }
    public Map<Boolean, Integer> compterReclamationsParReponse() throws SQLException {
        Map<Boolean, Integer> stats = new HashMap<>();
        String query = "SELECT repondre, COUNT(*) as count FROM Reclamations GROUP BY repondre";

        try (Connection connection = MyDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                boolean reponse = resultSet.getBoolean("repondre");
                int count = resultSet.getInt("count");
                stats.put(reponse, count);
            }
        }

        return stats;
    }



    public List<Reclamations> getReclamationsById(int id) throws SQLException {
        String sql = "SELECT * FROM reclamations WHERE id_user = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id); // Set the userId in the query
        ResultSet rs = ps.executeQuery();
        List<Reclamations> reclamations = new ArrayList<>();
        while (rs.next()) {
            Reclamations p = new Reclamations();
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
    public Reclamations getReclamationsById2(int id) throws SQLException {
        String sql = "SELECT * FROM reclamations WHERE id_reclamation = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id); // Set the userId in the query
        ResultSet rs = ps.executeQuery();
        Reclamations p =null;
        while (rs.next()) {
            p= new Reclamations();
            p.setId_reclamation(rs.getInt("id_reclamation"));
            p.setType(rs.getString("type"));
            p.setDescription(rs.getString("description"));
            p.setStatus(rs.getString("status"));
            p.setDate(rs.getDate("date"));
            p.setRepondre(rs.getBoolean("repondre"));
            p.setId_user(rs.getInt("id_user"));
        }
        return p;

    }
    public static String getUserEmailById(int userId) {
        String query = "SELECT email FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void marquerCommeRepondu(int idReclamation) throws SQLException {
        String sql = "UPDATE reclamations SET repondre = 1 WHERE id_reclamation = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idReclamation);
            ps.executeUpdate();
        }
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
            //reclamations.setType(rs.getString("type"));
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
    public Map<String, Integer> compterReclamationsParType() throws SQLException {
        Map<String, Integer> statistiques = new HashMap<>();
        String sql = "SELECT type, COUNT(*) AS count FROM reclamations GROUP BY type";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                statistiques.put(rs.getString("type"), rs.getInt("count"));
            }
        }
        return statistiques;
    }
    public int getUserIdByUsername(String username) throws SQLException {
        String query = "SELECT id FROM user WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        throw new SQLException("Utilisateur introuvable pour le nom d'utilisateur spécifié : " + username);
    }
    public Map<String, Integer> calculerStatistiques() throws SQLException {
        Map<String, Integer> statistiques = new HashMap<>();

        List<Reclamations> reclamations = recuperer(); // Récupérer toutes les réclamations

        for (Reclamations reclamation : reclamations) {
            String type = reclamation.getType();
            statistiques.put(type, statistiques.getOrDefault(type, 0) + 1);
        }

        return statistiques;
    }



}
