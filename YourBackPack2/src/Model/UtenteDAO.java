package Model;

import Model.Bean.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {
    public Utente doRetrieveByEmailPassword(String email, String password){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT email,username,nome,cognome,pswrd,telefono,via,citta,CAP,isAdmin FROM Utente WHERE email = ? AND pswrd =SHA1(?)");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Utente user = new Utente();
                user.setEmail(rs.getString(1));
                user.setUsername(rs.getString(2));
                user.setNome(rs.getString(3));
                user.setCognome(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setTelefono(rs.getString(6));
                user.setVia(rs.getString(7));
                user.setCitta(rs.getString(8));
                user.setCAP(rs.getString(9));
                user.setAdmin(rs.getBoolean(10));
                return user;
            }else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(Utente utente){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Utente (email,username,nome,cognome,pswrd,telefono,via,citta,CAP,isAdmin) VALUES(?,?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getUsername());
            ps.setString(3,utente.getNome());
            ps.setString(4,utente.getCognome());
            ps.setString(5, utente.getPassword());
            ps.setString(6,utente.getTelefono());
            ps.setString(7, utente.getVia());
            ps.setString(8, utente.getCitta());
            ps.setString(9, utente.getCAP());
            ps.setBoolean(10, utente.isAdmin());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Utente> doRetrieveAll(){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM Utente");
            ResultSet rs = ps.executeQuery();
            List<Utente> utenti = new ArrayList<>();
            while (rs.next()) {
                Utente user = new Utente();
                user.setEmail(rs.getString(1));
                user.setUsername(rs.getString(2));
                user.setNome(rs.getString(3));
                user.setCognome(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setTelefono(rs.getString(6));
                user.setVia(rs.getString(7));
                user.setCitta(rs.getString(8));
                user.setCAP(rs.getString(9));
                user.setAdmin(rs.getBoolean(10));
                utenti.add(user);
            }
            return utenti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}