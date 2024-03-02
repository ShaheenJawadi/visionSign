package services.cours;

import dtos.UserCoursDto;
import entities.UserCours;
import services.IService;
import utils.DbOps;
import utils.MyDatabase;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class UserCoursServices implements IService<UserCours> {


    private  String tableName ="usercours" ;
    private Connection connection ;
    private UserCoursDto userCoursDto ;
    private DbOps dbOps;

    public UserCoursServices() {
        connection = MyDatabase.getInstance().getConnection();
        userCoursDto = new UserCoursDto() ;
        dbOps= new DbOps();
    }
    @Override
    public int add(UserCours userCours) throws SQLException {

        String sql = "INSERT INTO "+tableName+" (userId,coursId) VALUES(? ,?)";

        PreparedStatement ps=connection.prepareStatement(sql);;
        ps.setInt(1,userCours.getUserId());
        ps.setInt(2 , userCours.getCoursId());
        ps.executeUpdate();
        return 1 ;


    }

    @Override
    public void update(UserCours userCours) throws SQLException {
//TODO CHANGE THIS
      /*  String sql="update "+tableName+" set userId=?, coursId=?, isCorrectQuizz=?, state=? where id=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,userCours.getUserId());
         ps.setInt(2 , userCours.getCoursId());
         ps.setBoolean(3 , userCours.isCorrectQuizz());
         ps.setInt(4 , userCours.getState());
         ps.setInt(5 , userCours.getId());
        ps.executeUpdate();*/

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
    public List<UserCours> getAll() throws SQLException {
        //String sql="select * from "+tableName;
        String sql= dbOps.select(tableName , "" , "");
        Statement st= connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        return userCoursDto.list(rs);
    }


    @Override
    public UserCours getById(int id) throws SQLException {

        //String sql="select * from "+tableName+" where id = ?";
        String sql= dbOps.select(tableName , "id" , "");
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);


        ResultSet rs = ps.executeQuery();

        if (rs.next()) {


            return userCoursDto.single(rs);
        } else {
            return null;
        }
    }



    public List<UserCours>  getByUser(int id) throws SQLException {

        System.out.println("getbyuser "+ id);
        //String sql="select * from "+tableName+" where id = ?";
        String sql= dbOps.select(tableName , "userId" , "");

        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);


        ResultSet rs = ps.executeQuery();


        return userCoursDto.list(rs);
    }


    public  boolean checkUserCours(int coursId , int userId) throws SQLException {

        String[] s = new String[2] ;
        s= new String[]{"userId", "coursId"};


        String sql="SELECT * FROM " + tableName +" where userId=? AND coursId=?" ;


        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,userId);
        ps.setInt(2,coursId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {



            return true ;
        } else {
            return false;
        }
    }





}
