package services.Reclamations;

import entities.Reclamations;
import services.IService;
import utils.MyDatabase;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReclamationsServices implements IService<Reclamations> {

    private static Connection connection;

    public ReclamationsServices() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void add(Reclamations reclamations) throws SQLException {
        String sql="insert into reclamations (type,description,status,date,id_user)"
                +"values('"+reclamations.getType()+"','"+reclamations.getDescription()+"','"+reclamations.getStatus()+"','"+reclamations.getDate()+"','"+reclamations.getId_user()+"')";
        Statement st=connection.createStatement();
        st.executeUpdate(sql);
    }

    @Override
    public void update(Reclamations reclamations) throws SQLException {
        String sql="update reclamations set type=?,description=?,status=?,date=? where id_reclamation=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setString(1,reclamations.getType());
        ps.setString(2,reclamations.getDescription());
        ps.setString(3,reclamations.getStatus());
        ps.setDate(4,reclamations.getDate());
        ps.setInt(5,reclamations.getId_reclamation());

        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM reclamations WHERE id_reclamation= ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Reclamations> getAll() throws SQLException {
        String sql="select * from reclamations";
        Statement st=connection.createStatement();
        ResultSet rs=st.executeQuery(sql);
        List<Reclamations> reclamations=new ArrayList<>();
        while (rs.next()){
            Reclamations p=new Reclamations();
            p.setId_reclamation(rs.getInt("id_reclamation"));
            p.setType(rs.getString("type"));
            p.setDescription(rs.getString("description"));
            p.setStatus(rs.getString("status"));
            p.setDate(rs.getDate("date"));
            p.setRepondre(rs.getBoolean("repondre"));
            p.setId_user(rs.getInt("id_user"));
            reclamations.add(p);

        }
        return reclamations ;
    }

    @Override
    public Reclamations getById(int id) throws SQLException {
        String sql = "SELECT * FROM reclamations WHERE id_reclamation = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Reclamations reclamation = null;
        if (rs.next()) {
            reclamation = new Reclamations();
            reclamation.setId_reclamation(rs.getInt("id_reclamation"));
            reclamation.setType(rs.getString("type"));
            reclamation.setDescription(rs.getString("description"));
            reclamation.setStatus(rs.getString("status"));
            reclamation.setDate(rs.getDate("date"));
            reclamation.setRepondre(rs.getBoolean("repondre"));
            reclamation.setId_user(rs.getInt("id_user"));
        }

        return reclamation;
    }

}

