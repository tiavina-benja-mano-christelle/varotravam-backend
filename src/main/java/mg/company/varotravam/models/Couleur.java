package mg.company.varotravam.models;

import java.sql.SQLException;
import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static mg.company.varotravam.models.utils.Util.*;
import mg.company.varotravam.utils.DBConnection;



public class Couleur {
    int id;
    String nom;
    String valeur;

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

        String sql = "SELECT count(*) / ? nb FROM couleur WHERE etat = ?";

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

    public Vector<Couleur> getAll(Connection connection, int start) throws SQLException{
        Vector<Couleur> models = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT * FROM couleur WHERE etat=? ORDER BY nom LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, DISPONIBLE);
            stmt.setInt(2, PAGINATION);
            stmt.setInt(3, start * PAGINATION);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Couleur model = new Couleur();
                model.setId(resultSet.getInt("id"));
                model.setNom(resultSet.getString("nom"));
                model.setValeur(resultSet.getString("valeur"));
                models.add(model);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }

    public Vector<Couleur> getAll(Connection connection) throws SQLException{
        Vector<Couleur> models = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT * FROM couleur WHERE etat=? ORDER BY nom";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, DISPONIBLE);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Couleur model = new Couleur();
                model.setId(resultSet.getInt("id"));
                model.setNom(resultSet.getString("nom"));
                model.setValeur(resultSet.getString("valeur"));
                models.add(model);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }

    public Couleur findById (Connection connection, int id) throws  SQLException,Exception{
        Couleur model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM couleur WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new Couleur();
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
        String sql = "INSERT INTO couleur(id, nom) VALUES (default, ?) ON CONFLICT(nom) DO UPDATE SET etat=? RETURNING id";

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
        String sql = "UPDATE couleur SET nom=?,valeur=? WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, this.getNom());
            stmt.setString(2, this.getValeur());
            stmt.setInt(3, this.getId());
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
        String sql = "UPDATE couleur SET etat=? WHERE id=?";
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
    public String getValeur() {
        return valeur;
    }
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}