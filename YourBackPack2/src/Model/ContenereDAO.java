package Model;

import Model.Bean.Contenere;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContenereDAO {

    public ArrayList<Contenere> doRetrieveByCart(String carrello){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT carrello,zaino,n_zaini FROM Contenere WHERE carrello = ?");
            ps.setString(1, carrello);
            ResultSet rs = ps.executeQuery();
            ArrayList<Contenere> lstcontenere = new ArrayList<>();
            while(rs.next()) {
                Contenere contenere = new Contenere();
                contenere.setCarrello(rs.getString(1));
                contenere.setZaino(rs.getString(2));
                contenere.setNumQuantita(rs.getInt(3));
                lstcontenere.add(contenere);
            }
            return lstcontenere;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(Contenere contenere){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Contenere (carrello,zaino,n_zaini) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contenere.getCarrello());
            ps.setString(2,contenere.getZaino());
            ps.setInt(3, contenere.getNumQuantita());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contenere> doRetrieveAll(){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM Contenere");
            ResultSet rs = ps.executeQuery();
            List<Contenere> contenuti = new ArrayList<>();
            while (rs.next()) {
                Contenere contenuto = new Contenere();
                contenuto.setCarrello(rs.getString(1));
                contenuto.setZaino(rs.getString(2));
                contenuto.setNumQuantita(rs.getInt(3));
                contenuti.add(contenuto);
            }
            return contenuti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void doUpdate(Contenere contenere){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Contenere SET n_zaini = ? WHERE zaino = ? AND carrello = ?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, contenere.getNumQuantita());
            ps.setString(2,contenere.getZaino());
            ps.setString(3,contenere.getCarrello());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doRemove(Contenere contenere, Connection connection){
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Contenere WHERE carrello = ? AND zaino = ?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contenere.getCarrello());
            ps.setString(2,contenere.getZaino());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}