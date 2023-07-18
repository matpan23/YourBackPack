package Controller;

import Model.*;
import Model.Bean.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RimuoviCarrelloServlet", value = "/RimuoviCarrelloServlet")
public class RimuoviCarrelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized (this) {
            HttpSession sessione = request.getSession(false);
            PrintWriter scrivi = response.getWriter();
            int mode = (int) sessione.getAttribute("mode");
            response.setContentType("text/html");
            Carrello cart = (Carrello) sessione.getAttribute("cart");
            String codice = request.getParameter("codice");
            ZainoDAO zainoDAO = new ZainoDAO();
            List<Zaino> zainos = zainoDAO.doRetrieveAll();
            List<Contenere> contenere = (List<Contenere>) sessione.getAttribute("contenere");
            boolean outOfStock = false;
            int numCopie = 0;
            if(mode == 3) {
                if(!contenere.isEmpty()){
                    for (Zaino z : zainos) {
                        if (codice.equals(z.getCodice())) {
                            for(Contenere c: contenere){
                                if(z.getCodice().equals(c.getZaino())){
                                    if(c.getNumQuantita() > 0){
                                        if(c.getNumQuantita() - 1 == 0) {
                                            outOfStock = true;
                                            contenere.remove(c);
                                            sessione.setAttribute("contenere",contenere);
                                            cart.setnZaini(cart.getnZaini() - 1);
                                            cart.setTotale(cart.getTotale() - z.getPrezzo());
                                            sessione.setAttribute("cart",cart);
                                            break;
                                        }
                                        c.setNumQuantita(c.getNumQuantita() - 1);
                                        numCopie = c.getNumQuantita();
                                        sessione.setAttribute("contenere",contenere);
                                        cart.setnZaini(cart.getnZaini() - 1);
                                        cart.setTotale(cart.getTotale() - z.getPrezzo());
                                        sessione.setAttribute("cart",cart);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (outOfStock) {
                    scrivi.print(cart.getnZaini() + "-" + "0" + "-" + cart.getTotale());
                }else {
                    String risposta = cart.getnZaini() + "-" + numCopie + "-" + cart.getTotale();
                    scrivi.print(risposta);
                }
            }else {
                CarrelloDAO carrelloDAO = new CarrelloDAO();
                ContenereDAO contenereDAO = new ContenereDAO();
                if(contenere != null) {
                    for (Zaino z : zainos) {
                        if (codice.equals(z.getCodice())) {
                            for (Contenere c : contenere) {
                                if (z.getCodice().equals(c.getZaino())) {
                                    if (c.getNumQuantita() > 0) {
                                        if (c.getNumQuantita() - 1 == 0) {
                                            outOfStock = true;
                                            contenere.remove(c);
                                            try{
                                                Connection con = ConPool.getConnection();
                                                contenereDAO.doRemove(c,con);
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                            sessione.setAttribute("contenere", contenere);
                                            cart.setnZaini(cart.getnZaini() - 1);
                                            cart.setTotale(cart.getTotale() - z.getPrezzo());
                                            carrelloDAO.doUpdate(cart);
                                            sessione.setAttribute("cart", cart);
                                            break;
                                        }
                                        c.setNumQuantita(c.getNumQuantita() - 1);
                                        numCopie = c.getNumQuantita();
                                        contenereDAO.doUpdate(c);
                                        sessione.setAttribute("contenere", contenere);
                                        cart.setnZaini(cart.getnZaini() - 1);
                                        cart.setTotale(cart.getTotale() - z.getPrezzo());
                                        carrelloDAO.doUpdate(cart);
                                        sessione.setAttribute("cart", cart);

                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (outOfStock) {
                    scrivi.print(cart.getnZaini() + "-" + "0" + "-" + cart.getTotale());

                }else {
                    String risposta = cart.getnZaini() + "-" + numCopie + "-" + cart.getTotale();
                    scrivi.print(risposta);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
