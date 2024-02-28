package services.ressource;

import dtos.RessourceDto;
import entities.Ressource;
import services.GServices;
import services.IService;
import utils.DbOps;
import utils.MyDatabase;

import java.sql.*;
import java.util.List;

public class RessourceService  extends GServices implements IService<Ressource> {



    private  String tableName ="ressources" ;
    private Connection connection ;
    private RessourceDto ressourceDto ;

    private DbOps dbOps ;
    public RessourceService() {
        connection = MyDatabase.getInstance().getConnection();
        ressourceDto = new RessourceDto() ;
        dbOps = new DbOps() ;
    }
    @Override
    public int add(Ressource ressource) throws SQLException {




        String sql = "INSERT INTO "+tableName+" (coursId , lien, type) VALUES(? ,? ,?)";

        PreparedStatement ps=prepareStatementWithGeneratedKeys(connection,sql);;
        ps.setInt(1,ressource.getCoursId());
        ps.setString(2,ressource.getLien());
        ps.setString(3,ressource.getType());

        ps.executeUpdate();
        return getCurrentId( connection,ps);
    }

    @Override
    public void update(Ressource ressource) throws SQLException {

        String sql="update "+tableName+" set coursId=?, lien=?, type=? where id=?";


        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,ressource.getCoursId());
        ps.setString(2,ressource.getLien());
        ps.setString(3,ressource.getType());
        ps.setInt(4,ressource.getId());
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
    public List<Ressource> getAll() throws SQLException {
        // String sql="select * from "+tableName;
        String sql= dbOps.select(tableName , "" , "");
        Statement st= connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        return ressourceDto.list(rs);
    }


    @Override
    public Ressource getById(int id) throws SQLException {

        //String sql="select * from "+tableName+" where id = ?";
        String sql= dbOps.select(tableName , "id" , "");

        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);


        ResultSet rs = ps.executeQuery();


        if (rs.next()) {


            return ressourceDto.single(rs);
        } else {
            return null;
        }
    }





    public Ressource getByCours(int id) throws SQLException {

        //String sql="select * from "+tableName+" where id = ?";
        String sql= dbOps.select(tableName , "coursId" , "");

        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);


        ResultSet rs = ps.executeQuery();


        if (rs.next()) {


            return ressourceDto.single(rs);
        } else {
            return null;
        }
    }





}
