package mg.company.varotravam.models;

import mg.company.varotravam.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatUtilisateur {
    int mois;
    int annee;
    int nbUtilisateur;
    
    /**
     * Récupère un graphe du nombre d'utilisateur
     * @param id
     * @param connection
     * @return
     * @throws SQLException
     */
    public static List<StatUtilisateur> find(int annee, Connection connection) throws Exception {
        List<StatUtilisateur> models = new ArrayList<>();
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        try {
            String sql = "SELECT * FROM v_graphe_utilisateur_inscrit WHERE annee=? ORDER BY mois";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, annee);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    StatUtilisateur model = new StatUtilisateur();
                    model.setMois(rs.getInt("mois"));
                    model.setAnnee(rs.getInt("annee"));
                    model.setNbUtilisateur(rs.getInt("nb_inscrit"));
                    models.add(model);
                } 
            } 
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }

    /**
     * Récupère un graphe du nombre d'utilisateur
     * @param id
     * @param connection
     * @return
     * @throws SQLException
     */
    public static List<Integer> findData(int annee, Connection connection) throws Exception {
        List<Integer> models = new ArrayList<>();
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        try {
            String sql = "SELECT * FROM v_graphe_utilisateur_inscrit WHERE annee=? ORDER BY mois";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, annee);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    models.add(rs.getInt("nb_inscrit"));
                } 
            } 
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getNbUtilisateur() {
        return nbUtilisateur;
    }

    public void setNbUtilisateur(int nbUtilisateur) {
        this.nbUtilisateur = nbUtilisateur;
    }

    
}
