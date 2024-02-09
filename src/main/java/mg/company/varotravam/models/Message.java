package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import mg.company.varotravam.utils.DBConnection;

public class Message {
    int id;
    int id_vendeur;
    int id_cient;
    int id_envoyeur;
    int id_annonce;
    Date date_envoye;
    String message;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId_vendeur() {
        return id_vendeur;
    }
    public void setId_vendeur(int id_vendeur) {
        this.id_vendeur = id_vendeur;
    }
    public int getId_cient() {
        return id_cient;
    }
    public void setId_cient(int id_cient) {
        this.id_cient = id_cient;
    }
    public int getId_envoyeur() {
        return id_envoyeur;
    }
    public void setId_envoyeur(int id_envoyeur) {
        this.id_envoyeur = id_envoyeur;
    }
    public int getId_annonce() {
        return id_annonce;
    }
    public void setId_annonce(int id_annonce) {
        this.id_annonce = id_annonce;
    }
    public Date getDate_envoye() {
        return date_envoye;
    }
    public void setDate_envoye(Date date_envoye) {
        this.date_envoye = date_envoye;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Message(int id, int id_vendeur, int id_cient, int id_envoyeur, int id_annonce, Date date_envoye,
            String message) {
        this.id = id;
        this.id_vendeur = id_vendeur;
        this.id_cient = id_cient;
        this.id_envoyeur = id_envoyeur;
        this.id_annonce = id_annonce;
        this.date_envoye = date_envoye;
        this.message = message;
    }
    public Message() {
    }
    
    public Vector<Message> getMessageByUtilisateur(int id, Connection connection) throws ClassNotFoundException, SQLException{
        Vector<Message> messages = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "select * from v_message where id_client = ? OR id_vendeur = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setId_vendeur(resultSet.getInt("id_vendeur"));
                    message.setId_cient(resultSet.getInt("id_cient"));
                    message.setId_envoyeur(resultSet.getInt("id_envoyeur"));
                    message.setId_annonce(resultSet.getInt("id_annonce"));
                    message.setDate_envoye(resultSet.getDate("date_envoye"));
                    message.setMessage(resultSet.getString("message"));
                    messages.add(message);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return messages;
    }

    public Vector<Message> getMessageByIdClient(int id_client , int id_vendeur , Connection connection) throws ClassNotFoundException, SQLException{
        Vector<Message> messages = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "select * from message where id_vendeur = ? and id_client= ? or id_client = ? and id_vendeur= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id_client);
            preparedStatement.setInt(2, id_vendeur);
            preparedStatement.setInt(3, id_client);
            preparedStatement.setInt(4, id_vendeur);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setId_vendeur(resultSet.getInt("id_vendeur"));
                    message.setId_cient(resultSet.getInt("id_cient"));
                    message.setId_envoyeur(resultSet.getInt("id_envoyeur"));
                    message.setId_annonce(resultSet.getInt("id_annonce"));
                    message.setDate_envoye(resultSet.getDate("date_envoye"));
                    message.setMessage(resultSet.getString("message"));
                    messages.add(message);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return messages;
    }

    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;
    
        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
    
        String sql = "INSERT INTO message(id_vendeur, id_client, id_envoyeur, id_annonce, message) VALUES (?, ?, ?, ?, ?)";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.getId_vendeur());
            stmt.setInt(2, this.getId_cient());
            stmt.setInt(3, this.getId_envoyeur());
            stmt.setInt(4, this.getId_annonce());
            stmt.setString(5, this.getMessage());
    
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }
    
}