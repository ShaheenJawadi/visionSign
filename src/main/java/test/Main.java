package test;

import entities.Personne;
import services.PersonneService;
import utils.MyDatabase;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        PersonneService ps =new PersonneService();

        try {
//            ps.ajouter(new Personne(23,"Ben Foulen","Foulen"));
//            ps.modifier(new Personne(1,27,"Ben","Foulen"));
            System.out.println(ps.recuperer());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
