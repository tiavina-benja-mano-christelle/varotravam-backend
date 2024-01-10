package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mg.company.varotravam.utils.DBConnection;

public class User {
    int id;
    String email;
    String username;
    boolean admin;
    String password;
    String tokens;

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", username=" + username + ", admin=" + admin + ", password="
                + password + ", tokens=" + tokens + "]";
    }

    public static User check(String email, String password) throws Exception {
        User user = findByEmailOrUsername(email, null);
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new Exception("Mot de passe érronée");
        }
    }
    
    public static User findByEmailOrUsername(String email, Connection connection) throws Exception{
        User model = null;
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM users WHERE email=? OR username=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, email);
                System.out.println(stmt);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new User();
                    model.setId(rs.getInt("id"));
                    model.setEmail(rs.getString("email"));
                    model.setUsername(rs.getString("username"));
                    model.setPassword(rs.getString("password"));
                    model.setAdmin(rs.getBoolean("admin"));
                    return model;
                } 
            } 
            throw new Exception("Email inconnue");
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return model;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getTokens() {
        return tokens;
    }
    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    
    
    
}
