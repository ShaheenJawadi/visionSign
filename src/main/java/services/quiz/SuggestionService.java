package services.quiz;

import entities.Suggestion;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuggestionService implements IGestionQuiz<Suggestion> {
    private Connection connection;

    public SuggestionService() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouterGestionQuiz(Suggestion suggestion) throws SQLException {
        String sql = "INSERT INTO suggestion (suggestion, status, questionId) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, suggestion.getSuggestion());
        ps.setBoolean(2, suggestion.isStatus());
        ps.setInt(3, suggestion.getQuestionId());
        int affectedRows = ps.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    suggestion.setId((id));
                }
            }
            catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void modifierGestionQuiz(Suggestion suggestion) throws SQLException {
        String sql = "UPDATE suggestion SET suggestion=?, status=?, questionId=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, suggestion.getSuggestion());
        ps.setBoolean(2, suggestion.isStatus());
        ps.setInt(3, suggestion.getQuestionId());
        ps.setInt(4, suggestion.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimerGestionQuiz(int id) throws SQLException {
        String sql = "DELETE FROM suggestion WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Suggestion> getAllGestionQuiz() throws SQLException {
        List<Suggestion> suggestions = new ArrayList<>();
        String sql = "SELECT * FROM suggestion";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Suggestion suggestion = new Suggestion();
            suggestion.setId(rs.getInt("id"));
            suggestion.setSuggestion(rs.getString("suggestion"));
            suggestion.setStatus(rs.getBoolean("status"));
            suggestion.setQuestionId(rs.getInt("questionId"));
            suggestions.add(suggestion);
        }
        return suggestions;
    }

    public List<Suggestion> getAllSuggestionsByQuestionId(int questionId) throws SQLException {
        List<Suggestion> suggestions = new ArrayList<>();
        String sql = "SELECT * FROM suggestion WHERE questionId=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, questionId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Suggestion suggestion = new Suggestion();
            suggestion.setId(rs.getInt("id"));
            suggestion.setSuggestion(rs.getString("suggestion"));
            suggestion.setStatus(rs.getBoolean("status"));
            suggestion.setQuestionId(rs.getInt("questionId"));
            suggestions.add(suggestion);
        }
        return suggestions;
    }
}
