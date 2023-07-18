package Model;

import Model.Bean.Zaino;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZainoDAO {

    public Zaino doRetrieveByCode(String code){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT id,name,price,quantity,description FROM zaino WHERE id = ?");
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Zaino zaino = new Zaino();
                zaino.setCodice(rs.getString(1));
                zaino.setTitolo(rs.getString(2));
                zaino.setPrezzo(rs.getInt(3));
                zaino.setQuantitaDisp(rs.getInt(4));
                zaino.setDescrizione(rs.getString(5));
                return zaino;
            }else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Zaino doRetrieveByTitle(String titolo){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT id,name,price,quantity,description FROM zaino WHERE name = ?");
            ps.setString(1,titolo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Zaino zaino = new Zaino();
                zaino.setCodice(rs.getString(1));
                zaino.setTitolo(rs.getString(2));
                zaino.setPrezzo(rs.getInt(3));
                zaino.setQuantitaDisp(rs.getInt(4));
                zaino.setDescrizione(rs.getString(5));
                return zaino;
            }else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int doSave(Zaino zaino){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO zaino (id,name,price,quantity,description) VALUES(?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, zaino.getCodice());
            ps.setString(2,zaino.getTitolo());
            ps.setDouble(3, zaino.getPrezzo());
            ps.setInt(4, zaino.getQuantitaDisp());
            ps.setString(5,zaino.getDescrizione());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                return keys.getInt(1);
            }
            else
                return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Zaino> doRetrieveAll(){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM zaino");
            ResultSet rs = ps.executeQuery();
            List<Zaino> lstZaini = new ArrayList<>();
            while (rs.next()) {
                Zaino zaino = new Zaino();
                zaino.setCodice(rs.getString(1));
                zaino.setTitolo(rs.getString(2));
                zaino.setPrezzo(rs.getInt(3));
                zaino.setQuantitaDisp(rs.getInt(4));
                zaino.setDescrizione(rs.getString(5));
                lstZaini.add(zaino);
            }
            return lstZaini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void doUpdateQuantitaDisp(int disponibili, String codice, Connection connection){
        try{
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE Zaino SET quantity = ? WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,disponibili);
            ps.setString(2,codice);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("Update error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void doUpdatePrezzo(double prezzo, String codice){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Zaino SET price = ? WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1,prezzo);
            ps.setString(2,codice);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("Update error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void doDelete(String codice){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM Zaino WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,codice);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("Delete error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}