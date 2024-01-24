package mg.company.varotravam.models;

import java.sql.SQLException;
import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static mg.company.varotravam.models.utils.Util.*;
import mg.company.varotravam.utils.DBConnection;



public class Marque {
    int id;
    String nom;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupère le nombre de page necessaire pour afficher toutes le modèle
     * @param connection
     * @return
     * @throws SQLException
     */
    public int getNbPage(Connection connection) throws SQLException{
        int nb = 0;
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT count(*) / ? nb FROM marque WHERE etat = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, PAGINATION);
            stmt.setInt(2, DISPONIBLE);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nb = rs.getInt("nb");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return nb;
    }

    public Vector<Marque> getAll(Connection connection, int start) throws SQLException{
        Vector<Marque> vitesses = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT * FROM marque WHERE etat=? ORDER BY nom LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, DISPONIBLE);
            stmt.setInt(2, PAGINATION);
            stmt.setInt(3, start * PAGINATION);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Marque vitesse = new Marque();
                vitesse.setId(resultSet.getInt("id"));
                vitesse.setNom(resultSet.getString("nom"));
                vitesses.add(vitesse);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return vitesses;
    }

    public Vector<Marque> getAll(Connection connection) throws SQLException{
        Vector<Marque> vitesses = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT * FROM marque WHERE etat=? ORDER BY nom";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, DISPONIBLE);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Marque vitesse = new Marque();
                vitesse.setId(resultSet.getInt("id"));
                vitesse.setNom(resultSet.getString("nom"));
                vitesses.add(vitesse);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return vitesses;
    }

    public Marque findById (Connection connection, int id) throws  SQLException,Exception{
        Marque model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM marque WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new Marque();
                    model.setId(rs.getInt("id"));
                    model.setNom(rs.getString("nom"));
                    return model;
                } 
            }
            throw new Exception("Error");
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO marque(id, nom) VALUES (default, ?) ON CONFLICT(nom) DO UPDATE SET etat=? RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, this.getNom());
            stmt.setInt(2, DISPONIBLE);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                this.setId(rs.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    public void update(Connection connection) throws SQLException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE marque SET nom=? WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, this.getNom());
            stmt.setInt(2, this.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    
    public void delete(int id, Connection connection) throws SQLException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE marque SET etat=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, INDISPONIBLE);
            stmt.setInt(2, this.getId());
            System.out.println(stmt);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }
}