package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mg.company.varotravam.utils.DBConnection;

public class Favori {
    int id;
    int utilisateurId;
    int annonceId;
    int etat;

    public Favori(int utilisateurId, int annonceId) {
        this.setUtilisateurId(utilisateurId);
        this.setAnnonceId(annonceId);
    }

    public static void delete(int id, Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE favori SET etat=1 WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    /**
     * Met l'etat en 1 pour dire que l'annonce n'est plus en favori
     * @param id
     * @param connection
     * @throws SQLException
     */
    public void delete( Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE favori SET etat=1 WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, this.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    public Favori find(Connection connection) throws SQLException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM favori WHERE utilisateur_id=? AND annonce_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, this.getUtilisateurId());
            stmt.setInt(2, this.getAnnonceId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.setId(rs.getInt("id"));
                this.setEtat(rs.getInt("etat"));
            }
            return this;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    /**
     * Sauvegarder l'annonce en favori de l'utilisateur
     * @param connection
     * @throws SQLException
     */
    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO public.favori (id, utilisateur_id, annonce_id, etat)" +
        "VALUES (DEFAULT, ?, ?, DEFAULT)" +
        "ON CONFLICT (utilisateur_id, annonce_id) DO UPDATE SET etat = 5 " +
        "RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, this.getUtilisateurId());
            stmt.setInt(2, this.getAnnonceId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) this.setId(rs.getInt("id"));
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }
    


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUtilisateurId() {
        return utilisateurId;
    }
    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    public int getAnnonceId() {
        return annonceId;
    }
    public void setAnnonceId(int annonceId) {
        this.annonceId = annonceId;
    }
    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }

    
}
