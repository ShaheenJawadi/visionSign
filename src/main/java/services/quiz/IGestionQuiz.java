package services.quiz;

import entities.Quiz;

import java.sql.SQLException;
import java.util.List;

public interface IGestionQuiz<T> {
    void ajouterGestionQuiz(T t) throws SQLException;

    void modifierGestionQuiz(T t) throws SQLException;
    void supprimerGestionQuiz(int id) throws SQLException;

    List<T> getAllGestionQuiz() throws SQLException;


}
