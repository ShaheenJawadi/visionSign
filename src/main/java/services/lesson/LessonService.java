package services.lesson;

import dtos.LessonDto;
import entities.Lesson;
import services.IService;
import utils.DbOps;
import utils.MyDatabase;

import java.sql.*;
import java.util.List;

public class LessonService  implements IService<Lesson> {


    private  String tableName ="lessons" ;
    private Connection connection ;
    private LessonDto lessonDto ;
    private DbOps dbOps;

    public LessonService() {
        connection = MyDatabase.getInstance().getConnection();
        lessonDto = new LessonDto() ;
        dbOps= new DbOps();
    }
    @Override
    public void add(Lesson lesson) throws SQLException {




        String sql = "INSERT INTO "+tableName+" (coursId,titre,content,duree,image,video,classement) VALUES(? ,? ,?,?,? ,? ,?)";

        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,lesson.getCoursId());
        ps.setString(2,lesson.getTitre());
        ps.setString(3,lesson.getContent());
        ps.setInt(4 , lesson.getDuree());
        ps.setString(5,lesson.getImage());
        ps.setString(6,lesson.getVideo());
        ps.setInt(7 , lesson.getClassement());

        ps.executeUpdate();

    }

    @Override
    public void update(Lesson lesson) throws SQLException {

        String sql="update "+tableName+" set coursId=?, titre=?, content=?, duree=?, image=?, video=?, classement=? where id=?";


        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,lesson.getCoursId());
        ps.setString(2,lesson.getTitre());
        ps.setString(3,lesson.getContent());
        ps.setInt(4 , lesson.getDuree());
        ps.setString(5,lesson.getImage());
        ps.setString(6,lesson.getVideo());
        ps.setInt(7 , lesson.getClassement());
        ps.setInt(8,lesson.getId());
        ps.executeUpdate();

    }

    @Override
    public void delete(int id) throws SQLException {
        // String sql="DELETE FROM "+tableName+" WHERE id=?";
        String sql=dbOps.delete(tableName , "id");
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);

        ps.executeUpdate();
    }

    @Override
    public List<Lesson> getAll() throws SQLException {
        //String sql="select * from "+tableName;
        String sql= dbOps.select(tableName , "" , "");
        Statement st= connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        return lessonDto.list(rs);
    }


    @Override
    public Lesson getById(int id) throws SQLException {

        //String sql="select * from "+tableName+" where id = ?";
        String sql= dbOps.select(tableName , "id" , "");
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);


        ResultSet rs = ps.executeQuery();

        if (rs.next()) {


            return lessonDto.single(rs);
        } else {
            return null;
        }
    }


}
