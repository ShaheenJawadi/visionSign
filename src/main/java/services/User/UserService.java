package services.User;

import entities.User;
import entities.UserRole;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserServices<User>{
    private Connection connection;
    public UserService(){connection= MyDatabase.getInstance().getConnection();}
    private LevelService ls;
    public static User current_user;
    public static void setCurrent_User(User user) {
        current_user = user;
    }

    public static User getCurrent_User() {
        return current_user;
    }
    @Override
    public void ajouter(User user) throws SQLException {

        String sql = "INSERT INTO user (nom, prenom, username,email, password, role,token) VALUES (?, ?, ?, ?, ?, ?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, user.getNom());
        ps.setString(2, user.getPrenom());
        ps.setString(3, user.getUsername());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPassword());
        ps.setString(6, user.getRole().name().toUpperCase());
        ps.setString(7,user.getToken());
        ps.executeUpdate();
    }





    @Override
    public void modifier(User user) throws SQLException {
        String sql="update user set nom= ?, prenom=?,username=?, date_de_naissance=?, email=?, password=?,role=?,status=?,level_id=? where id=?";
        PreparedStatement ps =connection.prepareStatement(sql);

        ps.setString(1,user.getNom());
        ps.setString(2, user.getPrenom());
        ps.setString(3,user.getUsername());
        ps.setDate(4,user.getDateNaissance());
        ps.setString(5,user.getEmail());
        ps.setString(6, user.getPassword());
        ps.setString(7,user.getRole().name());
        ps.setString(8,user.getStatus());
        ps.setInt(9,user.getLevelId());
        ps.setInt(10,user.getId());
        ps.executeUpdate();

    }
    public void updatePassword(User user) throws SQLException{
        String sql="update user set password=? where id=?";
        PreparedStatement ps= connection.prepareStatement(sql);
        ps.setString(1, user.getPassword());
        ps.setInt(2,user.getId());
        ps.executeUpdate();
    }
    public void updateEmail(User user) throws SQLException{
        String sql="update user set email=? where id=?";
        PreparedStatement ps= connection.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setInt(2,user.getId());
        ps.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

    }



    @Override
    public List<User> recuperer() throws SQLException {

        String sql="select * from user";
        Statement st= connection.createStatement();
        ResultSet rs= st.executeQuery(sql);
        List<User> users=new ArrayList<>();
        while(rs.next()){
            User u =new User();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setUsername(rs.getString("username"));
            u.setDateNaissance(rs.getDate("date_de_naissance"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setRole(UserRole.valueOf(rs.getString("role")));
            u.setStatus(rs.getString("status"));
            u.setToken(rs.getString("token"));
            u.setLevelId(rs.getInt("level_id"));


            users.add(u);
        }
        return users;
    }

    @Override
    public User getCurrent() {
        return current_user;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        System.out.println("rs.getString(\"nom\") "+rs.getString("nom"));
        // Extract data from the ResultSet and create a User object
        int id=rs.getInt("id");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String username=rs.getString("username");
        Date dateNaissance=rs.getDate("date_de_naissance");
        String email=rs.getString("email");
        String password=rs.getString("password");
        String status=rs.getString("status");
        String token=rs.getString("token");
        UserRole role=UserRole.valueOf(rs.getString("role"));
        int level_id=rs.getInt("level_id");

        User user =new User(id,nom, prenom,username,dateNaissance,email,password,role,status,level_id);
        return user;
    }

    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            System.out.println(username+" "+ password );

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User loggedInUser = createUserFromResultSet(rs);
                    setCurrent_User(loggedInUser);
                    System.out.println("xxxxxxxxxx"+loggedInUser);
                    return loggedInUser;
                }  else{
                    System.err.println("Bad credential");
                }

            }
        }
        return null; // Return null if login fails
    }




    /* public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setUsername(rs.getString("username"));
            user.setDateNaissance(rs.getDate("date_de_naissance"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(UserRole.valueOf(rs.getString("role")));
            user.setStatus(rs.getString("status"));
            user.setToken(rs.getString("token"));
            user.setLevelId(rs.getInt("level_id"));
        }
        return user;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        // Extract data from the ResultSet and create a User object
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String username=rs.getString("user");
        Date dateNaissance=rs.getDate("date_de_naissance");
        String email=rs.getString("email");
        String password=rs.getString("password");
        UserRole role=UserRole.valueOf(rs.getString("role"));
        String status=rs.getString("status");
        String token=rs.getString("token");
        int levelId=rs.getInt("level_id");

        return new User(id, nom, prenom,username,dateNaissance,email,password,role,status,token,levelId);
    }
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ?";
         PreparedStatement ps = connection.prepareStatement(sql) ;
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return createUserFromResultSet(rs);
                }
        return null;

        } */
}
