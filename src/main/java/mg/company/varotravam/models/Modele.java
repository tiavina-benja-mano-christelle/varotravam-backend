package mg.company.varotravam.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static mg.company.varotravam.models.utils.Util.*;
import mg.company.varotravam.utils.DBConnection;



public class Modele {
    int id;
    String nom;
    int marqueId;
    int nbVente;
    String marque;

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
     * Récupère la liste des modeles les plus vendues
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Modele> getByMarques(Connection connection, Integer[] idMarques) throws SQLException {
        List<Modele> models = new ArrayList<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        if (idMarques.length == 0) throw new SQLException("Aucune Marque");
        String sql = "SELECT * FROM v_modele WHERE etat=? AND marque_id IN (%s)";
        String idMarquesValues = "";
        for (int i = 0; i < idMarques.length; i++) {
            idMarquesValues += "?";
            if (idMarques.length - 1 != i) idMarquesValues += ",";
        }
        sql = String.format(sql, idMarquesValues);
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            int index = 2;
            stmt.setInt(1, DISPONIBLE);
            for (int i = 0; i < idMarques.length; i++) {
                stmt.setInt(index, idMarques[i]);
                index++;
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Modele model = new Modele();
                model.setId(rs.getInt("id"));
                model.setNom(rs.getString("nom"));
                model.setMarque(rs.getString("marque"));
                model.setMarqueId(rs.getInt("marque_id"));
                models.add(model);
            }
        } catch (SQLException throwables) {
            throw throwables;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }

    /**
     * Récupère la liste des modeles d'une marque
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Modele> getByMarque(Connection connection, int idMarque) throws SQLException {
        List<Modele> models = new ArrayList<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM v_modele WHERE etat=? AND marque_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, DISPONIBLE);
            stmt.setInt(2, idMarque);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Modele model = new Modele();
                model.setId(rs.getInt("id"));
                model.setNom(rs.getString("nom"));
                model.setMarque(rs.getString("marque"));
                model.setMarqueId(rs.getInt("marque_id"));
                models.add(model);
            }
        } catch (SQLException throwables) {
            throw throwables;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }
    

    /**
     * Récupère la liste des modeles les plus vendues
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Modele> getMostSelled(Connection connection) throws SQLException {
        List<Modele> models = new ArrayList<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT modele_id, modele, count(*) nb_vente, marque FROM v_annonce_vendu GROUP BY modele_id, modele, marque";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Modele model = new Modele();
                model.setId(rs.getInt("modele_id"));
                model.setNom(rs.getString("modele"));
                model.setNbVente(rs.getInt("nb_vente"));
                model.setMarque(rs.getString("marque"));
                models.add(model);
            }
        } catch (SQLException throwables) {
            throw throwables;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }


    /**
     * Récupère la liste des modeles les plus vendues
     * @param connection
     * @return
     * @throws SQLException
     */
    public Modele getMostSelledOne(Connection connection) throws SQLException {
        Modele model = null;
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT modele_id, modele, count(*) nb_vente, marque FROM v_annonce_vendu GROUP BY modele_id, modele, marque ORDER BY count(*) DESC LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model = new Modele();
                model.setId(rs.getInt("modele_id"));
                model.setNom(rs.getString("modele"));
                model.setNbVente(rs.getInt("nb_vente"));
                model.setMarque(rs.getString("marque"));
            }
        } catch (SQLException throwables) {
            throw throwables;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return model;
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

        String sql = "SELECT count(*) / ? nb FROM modele WHERE etat = ?";

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

    /**
     * récupère une partie modele 
     * @param connection
     * @param start
     * @return
     * @throws SQLException
     */
    public Vector<Modele> getAll(Connection connection, int start) throws SQLException{
        Vector<Modele> models = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT * FROM v_modele WHERE etat=? ORDER BY nom LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, DISPONIBLE);
            stmt.setInt(2, PAGINATION);
            stmt.setInt(3, start * PAGINATION);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Modele model = new Modele();
                model.setId(rs.getInt("id"));
                model.setNom(rs.getString("nom"));
                model.setMarque(rs.getString("marque"));
                model.setMarqueId(rs.getInt("marque_id"));
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

    /**
     * Récupère tous les modèles existant
     * @param connection
     * @return
     * @throws SQLException
     */
    public Vector<Modele> getAll(Connection connection) throws SQLException{
        Vector<Modele> models = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT * FROM v_modele WHERE etat=? ORDER BY nom";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, DISPONIBLE);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Modele model = new Modele();
                model.setId(rs.getInt("id"));
                model.setNom(rs.getString("nom"));
                model.setMarque(rs.getString("marque"));
                model.setMarqueId(rs.getInt("marque_id"));
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

    /**
     * récupère le modele mar id
     * @param connection
     * @param id
     * @return
     * @throws SQLException
     */
    public Modele findById (Connection connection, int id) throws  SQLException{
        Modele model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM v_modele WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new Modele();
                    model.setId(rs.getInt("id"));
                    model.setMarqueId(rs.getInt("marque_id"));
                    model.setMarque(rs.getString("marque"));
                    model.setNom(rs.getString("nom"));
                    return model;
                } 
            }
            throw new SQLException("Modele not found");
        } catch(SQLException ex) {
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
        String sql = "INSERT INTO modele(id, nom, marque_id) VALUES (default, ?,?) ON CONFLICT(nom, marque_id) DO UPDATE SET etat=? RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, this.getNom());
            stmt.setInt(2, this.getMarqueId());
            stmt.setInt(3, DISPONIBLE);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                this.setId(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            throw ex;
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
        String sql = "UPDATE modele SET nom=? WHERE id=?";

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
        String sql = "UPDATE modele SET etat=? WHERE id=?";
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
    public int getNbVente() {
        return nbVente;
    }
    public void setNbVente(int nbVente) {
        this.nbVente = nbVente;
    }
    public int getMarqueId() {
        return marqueId;
    }
    public void setMarqueId(int marqueId) {
        this.marqueId = marqueId;
    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }
}