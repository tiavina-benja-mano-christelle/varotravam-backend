package mg.company.varotravam.models;

import mg.company.varotravam.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatVente {
    int mois;
    int annee;
    int nbVente;
    

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
            String sql = "SELECT * FROM v_vente WHERE annee=? ORDER BY mois";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, annee);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    models.add(rs.getInt("commission"));
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
    public static Integer findTotal(int annee, Connection connection) throws Exception {
        Integer models = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        try {
            String sql = "SELECT sum(commission) commission FROM v_vente";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, annee);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    models = rs.getInt("commission");
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

    public int getNbVente() {
        return nbVente;
    }

    public void setNbVente(int nbVente) {
        this.nbVente = nbVente;
    }

    
}
