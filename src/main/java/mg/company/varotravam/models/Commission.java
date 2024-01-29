package mg.company.varotravam.models;

import mg.company.varotravam.utils.DBConnection;
import java.sql.*;
import java.util.Vector;

public class Commission {
    int id;
    Date commission_date;
    double valeur;
    //--GETTERS && SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Date getCommission_date() {
        return commission_date;
    }
    public void setCommission_date(Date commission_date) {
        this.commission_date = commission_date;
    }
    public double getValeur() {
        return valeur;
    }
    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    //--CONSTRUCTORS
    public Commission() {}
    public Commission(Date commission_date, double valeur) {
        this.setCommission_date(commission_date);
        this.setValeur(valeur);
    }
    public Commission(int id, Date commission_date, double valeur) {
        this.setId(id);
        this.setCommission_date(commission_date);
        this.setValeur(valeur);
    }
    
    /**
     * Sauvegarde la commission pour une date futur
     * @param connection
     * @param commission_date
     * @param valeur
     * @throws Exception
     */
    public void save(Connection connection, Date commission_date,double valeur) throws Exception {
        boolean wasConnected = true;

        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "insert into commission (id,commission_date,valeur) values(default, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, commission_date);
            statement.setDouble(2, valeur);
            statement.executeUpdate();
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
    }

    /**
     * Récupère toutes les commissions
     * @param connection
     * @return
     * @throws Exception
     */
    public Vector<Commission> getAll(Connection connection) throws Exception {
        Vector<Commission> commission = new Vector<>();
        boolean wasConnected = true;

        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "select * from commission";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Commission c = new Commission();
                c.setId(resultSet.getInt("id"));
                c.setCommission_date(resultSet.getDate("commiission_date"));
                c.setValeur(resultSet.getDouble("valeur"));
                commission.add(c);
            }
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
        return commission;
    }

    /**
     * Récupère la commission par ID 
     * @param connection
     * @param id
     * @return
     * @throws Exception
     */
    public Commission findById(Connection connection, int id) throws Exception {
        Commission c = null;
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM commission WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                c = new Commission();
                c.setId(rs.getInt("id"));
                c.setCommission_date(rs.getDate("commission_date "));
                c.setValeur(rs.getDouble("valeur"));
            }
            return c;
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
        return c;
    }

    /**
     * Met à jour la commission
     * @param connection
     * @param id
     * @param commission_date
     * @param valeur
     * @throws Exception
     */
    public  void update(Connection connection,  int id,Date commission_date,double valeur)throws Exception{
        boolean wasConnected = true;
        try{
            if (connection == null) {
                wasConnected = false;
                connection = DBConnection.getConnection();
            }
            Statement stmt = connection.createStatement();
            String sql = "UPDATE commission set  commission_date ='"+commission_date+"' and valeur ="+valeur+"  where  id ="+id;
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
    }
    
    /**
     * Récupère la commission actuelle
     * @param connection la connection
     * @return la commission actuelle
     * @throws Exception
     */
    public static Commission getCurrentCommission(Connection connection) throws Exception {
            Commission c = null;
            boolean wasConnected = true;
            if (connection == null) {
                wasConnected = false;
                connection = DBConnection.getConnection();
            }
            String sql = "SELECT * FROM v_actual_commission";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    c = new Commission();
                    c.setId(rs.getInt("id"));
                    c.setCommission_date(rs.getDate("commission_date "));
                    c.setValeur(rs.getDouble("valeur"));
                }
                return c;
            } catch (Exception e) {e.printStackTrace();
            } finally {if (!wasConnected) {connection.close();}}
            return c;
    }

    /**
     * récupéré les commissions par ordre décroissant
     * @param connection
     * @return
     * @throws Exception
     */
    public Vector<Commission> getCommissionByDesc(Connection connection) throws Exception {
        Vector<Commission> commission = new Vector<>();
        boolean wasConnected = true;

        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM public.commission\n" +
                "ORDER BY commission_date DESC ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Commission c = new Commission();
                c.setId(resultSet.getInt("id"));
                c.setCommission_date(resultSet.getDate("commiission_date"));
                c.setValeur(resultSet.getDouble("valeur"));
                commission.add(c);
            }
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
        return commission;
    }

    /**
     * Récupère la commission à une date données
     * @param connection
     * @param t
     * @return
     * @throws Exception
     */
    public Commission findByDate(Connection connection, Date t) throws Exception {
        Commission c = null;
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM commission WHERE commission_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, t);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                c = new Commission();
                c.setId(rs.getInt("id"));
                c.setCommission_date(rs.getDate("commission_date "));
                c.setValeur(rs.getDouble("valeur"));
            }
            return c;
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
        return c;
    }
}
