package services.Reclamations;

import entities.Avis;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvisServices implements IServices<Avis>{
    private Connection connection;
    public AvisServices()
    {
        connection= MyDatabase.getInstance().getConnection();
    }



    @Override
    public void ajouter(Avis avis) throws SQLException {
        // Vérifier si la note est entre 1 et
        if (avis.getNote() < 1 || avis.getNote() > 5) {
            throw new IllegalArgumentException("La note doit être un nombre entre 1 et 5.");
        }

        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlCurrentDate = java.sql.Date.valueOf(currentDate);
        String sql = "insert into avis (note,message,date,id_user,coursId) values(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,avis.getNote());
            ps.setString(2, avis.getMessage());
            ps.setDate(3, sqlCurrentDate);
            ps.setInt(4, avis.getId_user());
            ps.setInt(5, avis.getCoursid());

            // Récupérer le nom du cours et l'associer à l'avis
            String nomCours = getNomCoursById(avis.getCoursid());
            avis.setCoursNom(nomCours);

            ps.executeUpdate();
        }
    }



    @Override
    public void modifier(Avis avis) throws SQLException {
        String sql="update avis set note=?,message=?,date=?,id_user=?,coursId=? where id_avi=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,avis.getNote());
        ps.setString(2,avis.getMessage());
        ps.setDate(3,avis.getDate());
        ps.setInt(4,avis.getId_user());
        ps.setInt(5,avis.getCoursid());
        ps.setInt(6,avis.getId_avis());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM avis WHERE id_avi = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Avis> recuperer() throws SQLException {
        String sql="select * from avis";
        Statement st=connection.createStatement();
        ResultSet rs=st.executeQuery(sql);
        List<Avis> aviss=new ArrayList<>();
        while (rs.next()){
            Avis p=new Avis();
            p.setId_avis(rs.getInt("id_avi"));
            p.setNote(rs.getInt("note"));
            p.setMessage(rs.getString("message"));
            p.setDate(rs.getDate("date"));
            p.setId_user(rs.getInt("id_user"));
            p.setCoursid(rs.getInt("coursId"));
            aviss.add(p);
        }
        return aviss;

    }
    public List<Avis> recupererParCours(int coursId) throws SQLException {
        String sql = "SELECT * FROM avis WHERE coursId = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, coursId);
        ResultSet rs = st.executeQuery();
        List<Avis> aviss = new ArrayList<>();
        while (rs.next()) {
            Avis p = new Avis();
            p.setId_avis(rs.getInt("id_avi")); // Assurez-vous que le nom de colonne est correct
            p.setNote(rs.getInt("note"));
            p.setMessage(rs.getString("message"));
            p.setDate(rs.getDate("date"));
            p.setId_user(rs.getInt("id_user"));
            p.setCoursid(rs.getInt("coursId")); // S'assurer que ce champ existe dans votre table et est correctement nommé
            aviss.add(p);
        }
        return aviss;
    }
    public int getIdCoursByNom(String nomCours) throws SQLException {
        String query = "SELECT id FROM cours WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nomCours);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        throw new SQLException("Aucun cours trouvé avec le nom : " + nomCours);
    }

    public String getNomCoursById(int idCours) throws SQLException {
        String query = "SELECT nom FROM cours WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCours);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nom");
            }
        }
        return null;
    }
    public List<String> recupererNomsCours() {
        List<String> nomsCours = new ArrayList<>();
        String query = "SELECT nom FROM cours";

        try (PreparedStatement statement = MyDatabase.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nomCours = resultSet.getString("nom");
                nomsCours.add(nomCours);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nomsCours;
    }

    public Avis getAvisById(int id) throws SQLException {
        Connection connection = MyDatabase.getInstance().getConnection();
        String query = "SELECT * FROM avis WHERE id_avi = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Avis p = new Avis(); // Initialise reclamations à null
        if (rs.next()) {
            p=new Avis();
            p.setId_avis(rs.getInt("id_avi"));
            p.setNote(rs.getInt("note"));
            p.setMessage(rs.getString("message"));
            p.setDate(rs.getDate("date"));
            p.setId_user(rs.getInt("id_user"));
            p.setCoursid(rs.getInt("coursId"));
        }
        connection.close();
        return p;
    }
    public List<Avis> gatAvisById(int id) throws SQLException {
        Connection connection = MyDatabase.getInstance().getConnection();
        String query = "SELECT * FROM avis WHERE id_avi = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Avis p= new Avis(); // Initialise reclamations à null
        if (rs.next()) {
            p.setId_avis(rs.getInt("id_avi"));
            p.setNote(rs.getInt("note"));
            p.setMessage(rs.getString("message"));
            p.setDate(rs.getDate("date"));
            p.setId_user(rs.getInt("id_user"));
            p.setCoursid(rs.getInt("coursId"));
        }
        connection.close();
        return (List<Avis>) p;
    }
    public List<Avis> searchavisbyNoteType(String note, int userId) throws SQLException {

        String sql = "SELECT * FROM avis WHERE note LIKE ? AND id_user=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + note + "%");
        ps.setInt(2, userId);
        ResultSet rs = ps.executeQuery();

        List<Avis> matchingPublications = new ArrayList<>();
        while (rs.next()) {
            Avis p = new Avis();
            p.setId_avis(rs.getInt("id_avi"));
            p.setNote(rs.getInt("note"));
            p.setMessage(rs.getString("message"));
            p.setDate(rs.getDate("date"));
            p.setId_user(rs.getInt("id_user"));
            p.setCoursid(rs.getInt("coursId"));

            matchingPublications.add(p);
        }

        return matchingPublications;
    }
    public int getUserNameById(String username) throws SQLException {
        String query = "SELECT id FROM user WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        throw new SQLException("Utilisateur introuvable pour le nom d'utilisateur spécifié : " + username);
    }
    public List<Integer> recupererIdsUtilisateurs() {
        List<Integer> idsUtilisateurs = new ArrayList<>();
        String query = "SELECT id FROM user"; // Supposons que votre table d'utilisateurs s'appelle 'utilisateurs'

        try (PreparedStatement statement = MyDatabase.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idUtilisateur = resultSet.getInt("id");
                idsUtilisateurs.add(idUtilisateur);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idsUtilisateurs;
    }
    public List<Integer> recupererIdsCours() {
        List<Integer> idsCours = new ArrayList<>();
        String query = "SELECT id FROM cours";

        try (PreparedStatement statement = MyDatabase.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idCours = resultSet.getInt("id");
                idsCours.add(idCours);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idsCours;
    }

}
