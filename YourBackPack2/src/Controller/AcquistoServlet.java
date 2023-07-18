package Controller;

import Model.*;
import Model.Bean.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Savepoint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AcquistoServlet", value = "/AcquistoServlet")
public class AcquistoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized (this){
            HttpSession session = request.getSession();
            int mode = (int) session.getAttribute("mode");
            if(mode == 3){
                RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);
            }else {
                Utente utente = (Utente) session.getAttribute("utente");
                Carrello cart = (Carrello) session.getAttribute("cart");
                List<Contenere> contenereList;
                ContenereDAO contenereDAO = new ContenereDAO();
                contenereList = contenereDAO.doRetrieveByCart(utente.getEmail());
                List<Contenere> empty = new ArrayList<>();
                if (contenereList.isEmpty()) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Carrello.jsp");
                    dispatcher.forward(request, response);
                } else {
                    Ordine ordine = new Ordine();
                    ordine.setEmail(cart.getUtente());
                    ordine.setNumZaino(cart.getnZaini());
                    ordine.setTotale(cart.getTotale());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    ordine.setDataOrdine(sdf.format(new Date()));
                    try {
                        Connection connection = ConPool.getConnection();//creo una connessione unica
                        Savepoint savepoint = null; //dichiaro il punto a cui devo tornare indietro
                        try {
                            connection.setAutoCommit(false);//impedisco alle query di applicare subito le modifiche
                            savepoint = connection.setSavepoint();//imposto un checkpoint a cui tornare
                            OrdineDAO ordineDAO = new OrdineDAO();
                            int id = ordineDAO.doSave(ordine, connection);//salvo l'ordine e mi ritorno l'id
                            OrdineCDAO ordineCDAO = new OrdineCDAO();
                            CarrelloDAO carrelloDAO = new CarrelloDAO();
                            ZainoDAO zainoDAO = new ZainoDAO();
                            Zaino z;
                            for (Contenere c : contenereList) {
                                z = zainoDAO.doRetrieveByCode(c.getZaino());// mi salvo lo zaino che sta nel carrello
                                if (z.getQuantitaDisp() >= c.getNumQuantita()) {  //controllo se ho abbastanza quantità di quello zaino
                                    OrdineC ordineC = new OrdineC();
                                    ordineC.setId(id);
                                    ordineC.setOrdine(c.getCarrello());
                                    ordineC.setZaino(c.getZaino());
                                    ordineC.setNumZaini(c.getNumQuantita());
                                    ordineC.setPrezzo(zainoDAO.doRetrieveByCode(c.getZaino()).getPrezzo());
                                    ordineCDAO.doSave(ordineC, connection);
                                    contenereDAO.doRemove(c, connection);
                                    zainoDAO.doUpdateQuantitaDisp(z.getQuantitaDisp() - c.getNumQuantita(), z.getCodice(), connection);
                                } else {
                                    throw new Exception(c.getZaino());
                                }
                            }
                            connection.commit();//aggiorno le tabell ocn i risultati delle query
                            connection.setAutoCommit(true);//applica di nuovo le modifiche appena eseguo la query
                            cart.setnZaini(0);
                            cart.setTotale(0);
                            carrelloDAO.doUpdate(cart);
                            List<Ordine> ordini = (List<Ordine>) session.getAttribute("ordini");
                            ordini.add(ordine);
                            session.setAttribute("ordini", ordini);
                            session.setAttribute("cart", cart);
                            session.setAttribute("contenere", empty);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("profilo.jsp");
                            dispatcher.forward(request, response);
                        } catch (Exception e) {
                            if (savepoint != null) {
                                connection.rollback(savepoint);
                            } else {
                                connection.rollback();
                            }
                            connection.setAutoCommit(true);
                            request.setAttribute("errore", "Quantità non disponibile-" + e.getMessage());
                            RequestDispatcher dispatcher = request.getRequestDispatcher("MostraCarrelloServlet");
                            dispatcher.forward(request, response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
