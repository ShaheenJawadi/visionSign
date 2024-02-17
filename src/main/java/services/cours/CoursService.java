package services.cours;

import dtos.CoursDto;
import dtos.LessonDto;
import dtos.RessourceDto;
import entities.Cours;
import entities.Lesson;
import entities.Ressource;
import services.IService;
import utils.DbOps;
import utils.MyDatabase;

import java.sql.*;
import java.util.List;

public class CoursService  implements IService<Cours>, IServiceCours<Cours, Ressource, Lesson> {


    private Connection connection ;
    private String tableName = "cours" , ressourcesTableName ="ressources", lessosnsTableName ="lessons";

    private CoursDto coursDto ;
    private RessourceDto ressourceDto ;
    private LessonDto lessonDto;

    private DbOps dbOps ;


    public CoursService() {
        connection = MyDatabase.getInstance().getConnection();
        coursDto = new CoursDto() ;
        ressourceDto = new RessourceDto();
        lessonDto = new LessonDto();
        dbOps = new DbOps();


    }

    @Override
    public void add(Cours cours) throws SQLException {

        String sql = "INSERT INTO "+tableName+" (enseignantId,subCategoryId,niveauId, nom, description, tags, image, isValidated) VALUES(? ,?,? ,? ,?,?,?,?)";

        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,cours.getEnseignantId());
        ps.setInt(2,cours.getSubCategoryId());
        ps.setInt(3,cours.getNiveauId());
        ps.setString(4,cours.getNom());
        ps.setString(5,cours.getDescription());
        ps.setString(6 , cours.getTags());
        ps.setString(7 , cours.getImage());
        ps.setBoolean(8,cours.isValidated());

        ps.executeUpdate();

    }

    @Override
    public void update(Cours cours) throws SQLException {

        String sql="update "+tableName+" set enseignantId=?,subCategoryId=?, niveauId=?,  nom=?, description=?,tags=?,image=? , isValidated=? where id=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,cours.getEnseignantId());
        ps.setInt(2,cours.getSubCategoryId());
        ps.setInt(3,cours.getNiveauId());
        ps.setString(4,cours.getNom());
        ps.setString(5,cours.getDescription());
        ps.setString(6,cours.getTags());
        ps.setString(7,cours.getImage());
        ps.setBoolean(8,cours.isValidated());
        ps.setInt(9,cours.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {

        //  String sql="DELETE FROM "+tableName+" WHERE id=?";
        String sql=dbOps.delete(tableName , "id");


        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);

        ps.executeUpdate();
    }

    @Override
    public List<Cours> getAll() throws SQLException {
        //String sql="select * from "+tableName;
        String sql= dbOps.select(tableName , "" , "");
        Statement st= connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return coursDto.list(rs);
    }

    @Override
    public Cours getById(int id) throws SQLException {

        //String sql="select * from "+tableName+" where id = ?";
        String sql= dbOps.select(tableName , "id" , "");
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();


        if (rs.next()) {
            return coursDto.single(rs);
        } else {
            return null;
        }
    }




    @Override
    public List<Cours> search(String term) throws SQLException {
        //   String sql="SELECT * FROM " + tableName +   " WHERE description LIKE '%"+term.toLowerCase()+"%' OR nom LIKE '%\"+term.toLowerCase()+\"%' OR tags LIKE '%"+term.toLowerCase()+"%'";
        String sql=dbOps.select(tableName , "" , "")+
                " WHERE description LIKE '%"+term.toLowerCase()+"%' OR nom LIKE '%\"+term.toLowerCase()+\"%' OR tags LIKE '%"+term.toLowerCase()+"%'";

        Statement st= connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        return coursDto.list(rs);
    }

    @Override
    public List<Cours> getBySubCategory(int id) throws SQLException {
        // String sql="SELECT * FROM " + tableName +" where subCategoryId = ?";
        String sql= dbOps.select(tableName , "subCategoryId" , "");
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        return coursDto.list(rs);
    }

    @Override
    public List<Cours> getByNiveau(int id) throws SQLException {
        //String sql="SELECT * FROM " + tableName +" where niveauId = ?";
        String sql= dbOps.select(tableName , "niveauId" , "");
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        return coursDto.list(rs);
    }

    @Override
    public List<Cours> getAllWithPagination(int page, int rowsNum) throws SQLException {

        //String sql="SELECT * FROM " + tableName +" ORDER BY id LIMIT ?,?";
        String sql=dbOps.select(tableName , "" , "id") +" LIMIT ?,?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,page*rowsNum);
        ps.setInt(2,rowsNum);
        ResultSet rs = ps.executeQuery();
        return coursDto.list(rs);

    }

    @Override
    public List<Ressource> getRessouces(int coursId) throws SQLException {
        // String sql="SELECT * FROM " + ressourcesTableName +" where coursId = ?";
        String sql=dbOps.select(ressourcesTableName , "coursId" , "");
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,coursId);
        ResultSet rs = ps.executeQuery();

        return ressourceDto.list(rs);
    }

    @Override
    public List<Lesson> getLessons(int coursId) throws SQLException {
        // String sql="SELECT * FROM " + lessosnsTableName +" where coursId = ? ORDER BY classement";
        String sql=dbOps.select(lessosnsTableName , "coursId" , "classement");
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,coursId);
        ResultSet rs = ps.executeQuery();

        return lessonDto.list(rs);
    }


}
