package services.quiz;

import entities.Notes;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotesService {
    private Connection connection;

    public NotesService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public void ajouterNote(Notes note) throws SQLException {
        String sql = "INSERT INTO notes (note, userId, quizId) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setFloat(1, note.getNote());
        ps.setInt(2, note.getUserId());
        ps.setInt(3, note.getQuizId());

        int affectedRows = ps.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    note.setId((id));
                }
            }
            catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }

    }
    public Notes getNoteById(int id) throws SQLException {
        String sql = "SELECT * FROM notes WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Notes note = null;
        if (rs.next()) {
            note = new Notes();
            note.setId(rs.getInt("id"));
            note.setNote(rs.getFloat("note"));
            note.setUserId(rs.getInt("userId"));
            note.setQuizId(rs.getInt("quizId"));
        }
        return note;
    }



    public List<Notes> getNotesByUserIdAndQuizId(int userId, int quizId) throws SQLException {
        String sql = "SELECT * FROM notes WHERE userId = ? AND quizId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, quizId);
        ResultSet rs = ps.executeQuery();

        List<Notes> notes = new ArrayList<>();
        while (rs.next()) {
            Notes note = new Notes();
            note.setId(rs.getInt("id"));
            note.setNote(rs.getFloat("note"));
            note.setUserId(rs.getInt("userId"));
            note.setQuizId(rs.getInt("quizId"));
            notes.add(note);
        }
        return notes;
    }

}
