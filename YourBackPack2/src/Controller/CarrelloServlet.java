package Controller;

import Model.*;
import Model.Bean.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet (name = "CarrelloServlet", value = "/carrelloservlet")
public class CarrelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        synchronized (this) {
            HttpSession sessione = req.getSession(false);
            PrintWriter scrivi = resp.getWriter();
            int mode = (int) sessione.getAttribute("mode");
            resp.setContentType("text/html");
            Carrello cart = (Carrello) sessione.getAttribute("cart");
            String codice = req.getParameter("codice");
            ZainoDAO zainoDAO = new ZainoDAO();
            List<Zaino> lstZaini = zainoDAO.doRetrieveAll();
            List<Contenere> contenere = (List<Contenere>) sessione.getAttribute("contenere");
            boolean trovatoC = false;
            boolean outOfStock = false;
            int numCopie = 0;
            if(mode == 3) {
                if(!contenere.isEmpty()){
                    for (Zaino z : lstZaini) {
                        if (codice.equals(z.getCodice())) {
                            for(Contenere c: contenere){
                                if(z.getCodice().equals(c.getZaino())){
                                    if(z.getQuantitaDisp() > 0){
                                        if(c.getNumQuantita() + 1 > z.getQuantitaDisp()) {
                                            outOfStock = true;
                                            trovatoC = true;
                                            break;
                                        }
                                        c.setNumQuantita(c.getNumQuantita() + 1);
                                        numCopie = c.getNumQuantita();
                                        sessione.setAttribute("contenere",contenere);
                                        trovatoC = true;
                                        cart.setnZaini(cart.getnZaini() + 1);
                                        cart.setTotale(cart.getTotale() + z.getPrezzo());
                                        sessione.setAttribute("cart",cart);
                                        break;
                                    }
                                }
                            }
                            if(!trovatoC){
                                if(z.getQuantitaDisp() > 0) {
                                    Contenere nuovo = new Contenere();
                                    nuovo.setZaino(codice);
                                    nuovo.setNumQuantita(1);
                                    numCopie = 1;
                                    nuovo.setCarrello(cart.getUtente());
                                    contenere.add(nuovo);
                                    sessione.setAttribute("contenere",contenere);
                                    cart.setnZaini(cart.getnZaini() + 1);
                                    cart.setTotale(cart.getTotale() + z.getPrezzo());
                                    sessione.setAttribute("cart",cart);
                                    break;
                                }
                            }
                        }
                    }
                }else{
                    for(Zaino z : lstZaini){
                        if(codice.equals(z.getCodice())){
                            if(z.getQuantitaDisp() > 0) {
                                Contenere nuovo = new Contenere();
                                nuovo.setZaino(codice);
                                nuovo.setNumQuantita(1);
                                numCopie = 1;
                                nuovo.setCarrello(cart.getUtente());
                                contenere.add(nuovo);
                                sessione.setAttribute("contenere",contenere);
                                cart.setnZaini(cart.getnZaini() + 1);
                                cart.setTotale(cart.getTotale() + z.getPrezzo());
                                sessione.setAttribute("cart",cart);
                                break;
                            }
                        }
                    }
                }
                if (outOfStock) {
                    scrivi.print("-2");
                }else {
                    String risposta = cart.getnZaini() + "-" + numCopie + "-" + cart.getTotale();
                    scrivi.print(risposta);
                }
            }else {
                CarrelloDAO carrelloDAO = new CarrelloDAO();
                ContenereDAO contenereDAO = new ContenereDAO();
                if(contenere != null) {
                    for (Zaino z : lstZaini) {
                        if (codice.equals(z.getCodice())) {
                            for(Contenere c: contenere){
                                if(z.getCodice().equals(c.getZaino())){
                                    if(z.getQuantitaDisp() > 0){
                                        if(c.getNumQuantita() + 1 > z.getQuantitaDisp()) {
                                            outOfStock = true;
                                            trovatoC = true;
                                            break;
                                        }
                                        c.setNumQuantita(c.getNumQuantita() + 1);
                                        numCopie = c.getNumQuantita();
                                        trovatoC = true;
                                        contenereDAO.doUpdate(c);
                                        sessione.setAttribute("contenere",contenere);
                                        cart.setnZaini(cart.getnZaini() + 1);
                                        cart.setTotale(cart.getTotale() + z.getPrezzo());
                                        sessione.setAttribute("cart",cart);
                                        carrelloDAO.doUpdate(cart);
                                        break;
                                    }
                                }
                            }
                            if(!trovatoC){
                                if(z.getQuantitaDisp() > 0) {
                                    Contenere nuovo = new Contenere();
                                    nuovo.setZaino(codice);
                                    nuovo.setNumQuantita(1);
                                    numCopie = 1;
                                    nuovo.setCarrello(cart.getUtente());
                                    contenereDAO.doSave(nuovo);
                                    contenere.add(nuovo);
                                    sessione.setAttribute("contenere",contenere);
                                    cart.setnZaini(cart.getnZaini() + 1);
                                    cart.setTotale(cart.getTotale() + z.getPrezzo());
                                    sessione.setAttribute("cart",cart);
                                    carrelloDAO.doUpdate(cart);
                                    break;
                                }
                            }
                        }
                    }
                }
                else{
                    for(Zaino z : lstZaini){
                        if(codice.equals(z.getCodice())){
                            if(z.getQuantitaDisp() > 0) {
                                Contenere nuovo = new Contenere();
                                nuovo.setZaino(codice);
                                nuovo.setNumQuantita(1);
                                numCopie = 1;
                                nuovo.setCarrello(cart.getUtente());
                                contenereDAO.doSave(nuovo);
                                contenere.add(nuovo);
                                sessione.setAttribute("contenere",contenere);
                                cart.setnZaini(cart.getnZaini() + 1);
                                cart.setTotale(cart.getTotale() + z.getPrezzo());
                                sessione.setAttribute("cart",cart);
                                carrelloDAO.doUpdate(cart);
                                break;
                            }
                        }
                    }
                }
                if (outOfStock) {
                    scrivi.print("-2");
                }else {
                    String risposta = cart.getnZaini() + "-" + numCopie + "-" + cart.getTotale();
                    scrivi.print(risposta);
                }
            }
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
