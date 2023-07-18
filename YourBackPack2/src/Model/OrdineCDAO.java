package Model;

import Model.Bean.Contenere;
import Model.Bean.Ordine;
import Model.Bean.OrdineC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineCDAO {
    public OrdineCDAO(){
    }
    public List<OrdineC> doRetrieveAllByOrdine(int numeroOrdine){
    	try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT id_ordine,utente_ordine, zaino, n_zaini, prezzo FROM Ordine_C WHERE id_ordine = ?");
            ps.setInt(1, numeroOrdine);
            ResultSet rs = ps.executeQuery();
            List<OrdineC> ordineC = new ArrayList<>();
            while(rs.next()) {
                OrdineC o = new OrdineC();
                o.setId(rs.getInt(1));
                o.setOrdine(rs.getString(2));
                o.setZaino(rs.getString(3));
                o.setNumZaini(rs.getInt(4));
                o.setPrezzo(rs.getInt(5));
                ordineC.add(o);
            }
            return ordineC;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(OrdineC ordineC, Connection connection){
        try{
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Ordine_C (id_ordine,utente_ordine,zaino,n_zaini,prezzo) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,ordineC.getId());
            ps.setString(2, ordineC.getOrdine());
            ps.setString(3,ordineC.getZaino());
            ps.setInt(4, ordineC.getNumZaini());
            ps.setInt(5, ordineC.getPrezzo());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
