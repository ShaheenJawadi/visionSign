package services.quiz;

import entities.Questions;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionsService implements IGestionQuiz<Questions> {
    private Connection connection;

    public QuestionsService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouterGestionQuiz(Questions question) throws SQLException {
        String sql = "INSERT INTO questions (question, quizId, userId, image) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, question.getQuestion());
        ps.setInt(2, question.getQuizId());
        ps.setInt(3, question.getUserId());
        ps.setString(4, question.getImage());

        int affectedRows = ps.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    question.setId(id);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void modifierGestionQuiz(Questions question) throws SQLException {
        String sql = "UPDATE questions SET question=?, quizId=? , userId=?, image=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, question.getQuestion());
        ps.setInt(2, question.getQuizId());
        ps.setInt(3, question.getUserId());
        ps.setString(4, question.getImage());
        ps.setInt(5, question.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimerGestionQuiz(int id) throws SQLException {
        String sql = "DELETE FROM questions WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Questions> getAllGestionQuiz() throws SQLException {
        List<Questions> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Questions question = new Questions();
            question.setId(rs.getInt("id"));
            question.setQuestion(rs.getString("question"));
            question.setUserId(rs.getInt("userId"));
            question.setQuizId(rs.getInt("quizId"));
            question.setImage(rs.getString("image"));
            questions.add(question);
        }
        return questions;
    }

    public List<Questions> getAllQuizQuestionsByQuizId(int quizId) throws SQLException {
        List<Questions> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions where quizId=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, quizId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Questions question = new Questions();
            SuggestionService suggestionService = new SuggestionService();

            question.setId(rs.getInt("id"));
            question.setQuestion(rs.getString("question"));
            question.setUserId(rs.getInt("userId"));
            question.setQuizId(rs.getInt("quizId"));
            question.setImage(rs.getString("image"));
            question.setSuggestionsQuestion(suggestionService.getAllSuggestionsByQuestionId(question.getId()));
            questions.add(question);
        }
        return questions;
    }

    public boolean isQuestionUniqueInQuiz(String questionText, int quizId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM questions WHERE question=? AND quizId=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, questionText);
        ps.setInt(2, quizId);
        ResultSet rs = ps.executeQuery();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt("count");
        }

        return count == 0;
    }
}
