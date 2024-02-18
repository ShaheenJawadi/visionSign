package services.quiz;

import entities.Questions;
import entities.Quiz;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizService implements IGestionQuiz<Quiz> {
    private Connection connection;

    public QuizService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouterGestionQuiz(Quiz quiz) throws SQLException {
        String sql = "INSERT INTO quiz (nom, duree, coursId, userId) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // specify to return generated keys
        ps.setString(1, quiz.getNom());
        ps.setString(2, quiz.getDuree());
        ps.setInt(3, quiz.getCoursId());
        ps.setInt(4, quiz.getUserId());

        int affectedRows = ps.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    quiz.setId((id));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void modifierGestionQuiz(Quiz quiz) throws SQLException {
        String sql = "UPDATE quiz SET nom=?, duree=?, coursId=? , userId=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, quiz.getNom());
        ps.setString(2, quiz.getDuree());
        ps.setInt(3, quiz.getCoursId());
        ps.setInt(4, quiz.getUserId());
        ps.setInt(5, quiz.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimerGestionQuiz(int id) throws SQLException {
        String sql = "DELETE FROM quiz WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Quiz> getAllGestionQuiz() throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();

        String sql = "SELECT * FROM quiz";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Quiz quiz = new Quiz();
            QuestionsService questionService = new QuestionsService();

            quiz.setId(rs.getInt("id"));
            quiz.setNom(rs.getString("nom"));
            quiz.setDuree(rs.getString("duree"));
            quiz.setUserId(rs.getInt("userId"));
            quiz.setCoursId(rs.getInt("coursId"));
            quiz.setQuizQuestions(questionService.getAllQuizQuestionsByQuizId(quiz.getId()));
            quizzes.add(quiz);
        }
        return quizzes;
    }

    public Quiz getQuizById(int id) throws SQLException {

        String sql = "select * from quiz where id=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Quiz quiz = new Quiz();
        while (rs.next()) {
            QuestionsService questionService = new QuestionsService();
        quiz.setId(rs.getInt("id"));
        quiz.setNom(rs.getString("nom"));
        quiz.setDuree(rs.getString("duree"));
        quiz.setUserId(rs.getInt("userId"));
        quiz.setCoursId(rs.getInt("coursId"));

        quiz.setQuizQuestions(questionService.getAllQuizQuestionsByQuizId(id));
        }
        return quiz;
    }

}
