package services.User;

import entities.User;
import entities.UserRole;
import State.UserSessionManager;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService implements IUserServices<User>{
    private Connection connection;
    public UserService(){connection= MyDatabase.getInstance().getConnection();}
    private LevelService ls;
    public static User current_user;
    public static void setCurrent_User(User user) {
      //  current_user = user;
        UserSessionManager userSessionManager=UserSessionManager.getInstance();
        userSessionManager.setCurrentUser(user);
    }
    @Override
    public User getCurrent() {
        UserSessionManager userSessionManager=UserSessionManager.getInstance();
        return userSessionManager.getCurrentUser();
        //  return current_user;
    }

    @Override
    public void ajouter(User user) throws SQLException {

        String sql = "INSERT INTO user (nom, prenom, username,email, password, role,token) VALUES (?, ?, ?, ?, ?, ?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, user.getNom());
        ps.setString(2, user.getPrenom());
        ps.setString(3, user.getUsername());
        ps.setString(4, user.getEmail());
        ps.setString(5, PasswordHashing.hashPassword(user.getPassword()));
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
        System.out.println("eeeeeeeeeee");
        System.out.println(user.getPassword());
        String sql="update user set password=? where id=?";
        PreparedStatement ps= connection.prepareStatement(sql);
        ps.setString(1, PasswordHashing.hashPassword(user.getPassword()));
        ps.setInt(2,user.getId());
        ps.executeUpdate();
        setCurrent_User(user);
    }
    public void updateEmail(User user) throws SQLException{
        String sql="update user set email=? where id=?";
        PreparedStatement ps= connection.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setInt(2,user.getId());
        ps.executeUpdate();
    }
    public void updateImage(User user) throws SQLException{
        String sql="update user set image=? where id=?";
        PreparedStatement ps= connection.prepareStatement(sql);
        ps.setString(1, user.getImage());
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
            u.setImage(rs.getString("image"));
            u.setLevelId(rs.getInt("level_id"));


            users.add(u);
        }
        return users;
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
        String image=rs.getString("image");
        UserRole role=UserRole.valueOf(rs.getString("role"));
        int level_id=rs.getInt("level_id");

        User user =new User(id,nom, prenom,username,dateNaissance,email,password,role,status,image,level_id);
        return user;
    }

    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND BINARY password = ?";
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

    public User loginWithEmail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);

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
        return null; // Return null if login failsdaada
    }
    public List <User> searchById(int userId) throws SQLException {
        String query = "SELECT * FROM user WHERE id = ?";
        List<User> searchResults = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = createUserFromResultSet(resultSet);
                    searchResults.add(user);
                    System.out.println("searcheddd userr"+user);
                }
            }
        }

        return searchResults;

    }

    public List<User> searchByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        List<User> searchResults = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = createUserFromResultSet(resultSet);
                    searchResults.add(user);
                }
            }
        }

        return searchResults;
    }
    public Map<UserRole, Long> getRoleStatistics() {
        try {
            // Fetch user statistics from the database or wherever you store them
            // You may need to adapt this based on your actual data structure
            List<User> userList = recuperer(); // Replace with your actual method
            return userList.stream()
                    .collect(Collectors.groupingBy(User::getRole, Collectors.counting()));
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
    public Map<String, Long> getAgeStatistics() {
        try {
            // Fetch user statistics from the database or wherever you store them
            // You may need to adapt this based on your actual data structure
            List<User> userList = recuperer(); // Replace with your actual method

            // Calculate age and group users into age ranges
            Map<String, Long> ageStatistics = userList.stream()
                    .collect(Collectors.groupingBy(user -> calculateAgeRange(user.getDateNaissance()), Collectors.counting()));

            return ageStatistics;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private String calculateAgeRange(Date dateNaissance) {
        // Implement logic to calculate age range based on the given dateNaissance
        // For example, group users into ranges like "0-10", "11-20", etc.
        // You can use Java 8's java.time.LocalDate for better date manipulation

        // Sample logic (replace with your own):
        int age = calculateAge(dateNaissance);
        if (age >= 0 && age <= 10) {
            return "0-10";
        } else if (age >= 11 && age <= 20) {
            return "11-20";
        } else if (age >= 21 && age <= 30) {
            return "21-30";
        } // Add more ranges as needed

        return "Unknown";
    }

    private int calculateAge(Date dateNaissance) {
        // Implement logic to calculate age based on the given dateNaissance
        // You can use Java 8's java.time.LocalDate for better date manipulation

        // Sample logic (replace with your own):
        LocalDate birthdate = dateNaissance.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthdate, currentDate).getYears();
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
