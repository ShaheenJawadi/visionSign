package entities;

//import services.UserService;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String username;
    private Date dateNaissance;
    private String email;
    private String password;
    private UserRole role;
    private String status;
    private String token;
    private int levelId; //foreign key to level id
    public User(){}
    public User(int id, String nom, String prenom, String username, Date dateNaissance, String email, String password, UserRole role, String status,int levelId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username=username;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.token = UUID.randomUUID().toString();
        this.levelId=levelId;


    }
    public User(int id, String nom, String prenom, String username, Date dateNaissance, String email, String password, UserRole role, String status) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username=username;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.token = UUID.randomUUID().toString();


    }

    public User(String nom, String prenom, String username, Date dateNaissance, String email, String password, UserRole role, String status,int levelId) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.token = UUID.randomUUID().toString();
        this.levelId=levelId;
    }

    public User(String nom, String prenom, String username, String email, String password, UserRole role) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.token=UUID.randomUUID().toString();
    }

    public User(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getUsername() {
        return username;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getLevelId() {
        return levelId;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", username='" + username + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status='" + status + '\'' +
                ", token='" + token + '\'' +
                ", levelId=" + levelId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(token, user.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, token);
    }
}

