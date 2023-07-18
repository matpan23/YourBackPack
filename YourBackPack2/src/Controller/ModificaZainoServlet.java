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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ModificaZainoServlet", value = "/ModificaZainoServlet")
public class ModificaZainoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        String code = request.getParameter("code");
        ZainoDAO zainoDAO = new ZainoDAO();
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        ContenereDAO contenereDAO = new ContenereDAO();
        List<Carrello> carrelloList = carrelloDAO.doRetrieveAll();
        List<Contenere> contenereList = contenereDAO.doRetrieveAll();
        int newStock;
        float newPrice;
        if(!operation.isEmpty()) {
            if (operation.equals("Edit") && !code.isEmpty()) {
                String newPriceS = request.getParameter("newPrice");
                String newStockS = request.getParameter("newStock");
                try {
                    if (!newPriceS.isEmpty() && !newStockS.isEmpty()) {
                        newPrice = Float.parseFloat(newPriceS);
                        newStock = Integer.parseInt(newStockS);
                        if (newPrice > 0 && newStock >= 0) {
                            if(!carrelloList.isEmpty()){
                                for(Carrello c: carrelloList){
                                    if(!contenereList.isEmpty()){
                                        for(Contenere con: contenereList){
                                            if(con.getCarrello().equals(c.getUtente())){
                                                if(con.getZaino().equals(code)){
                                                    float oldPrice = zainoDAO.doRetrieveByCode(code).getPrezzo();
                                                    if(oldPrice > newPrice) {
                                                        c.setTotale(c.getTotale() - ((oldPrice - newPrice) * con.getNumQuantita()));
                                                    }else{
                                                        c.setTotale(c.getTotale() + ((newPrice - oldPrice) * con.getNumQuantita()));
                                                    }
                                                    request.getSession().setAttribute("cart",c);
                                                    carrelloDAO.doUpdate(c);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            zainoDAO.doUpdatePrezzo(newPrice, code);
                            try{
                                Connection connection = ConPool.getConnection();
                                zainoDAO.doUpdateQuantitaDisp(newStock, code, connection);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            request.setAttribute("errore", "Valori negativi");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                            dispatcher.forward(request, response);
                        }
                    } else if (newPriceS.isEmpty() && !newStockS.isEmpty()) {
                        newStock = Integer.parseInt(newStockS);
                        if (newStock >= 0) {
                            try{
                                Connection connection = ConPool.getConnection();
                                zainoDAO.doUpdateQuantitaDisp(newStock, code, connection);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            request.setAttribute("errore", "Valori negativi");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                            dispatcher.forward(request, response);
                        }
                    } else if (!newPriceS.isEmpty()) {
                        newPrice = Float.parseFloat(newPriceS);
                        if (newPrice > 0) {
                            if(!carrelloList.isEmpty()){
                                for(Carrello c: carrelloList){
                                    if(!contenereList.isEmpty()){
                                        for(Contenere con: contenereList){
                                            if(con.getCarrello().equals(c.getUtente())){
                                                if(con.getZaino().equals(code)){
                                                    float oldPrice = zainoDAO.doRetrieveByCode(code).getPrezzo();
                                                    if(oldPrice > newPrice) {
                                                        c.setTotale(c.getTotale() - ((oldPrice - newPrice) * con.getNumQuantita()));
                                                    }else{
                                                        c.setTotale(c.getTotale() + ((newPrice - oldPrice) * con.getNumQuantita()));
                                                    }
                                                    request.getSession().setAttribute("cart",c);
                                                    carrelloDAO.doUpdate(c);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            zainoDAO.doUpdatePrezzo(newPrice, code);
                        } else {
                            request.setAttribute("errore", "Valori negativi");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                            dispatcher.forward(request, response);
                        }
                    } else {
                        request.setAttribute("errore", "Nessun valore scritto");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                        dispatcher.forward(request, response);
                    }
                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
                    request.setAttribute("errore", "Hai inserito lettere, ci vogliono i numeri");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                    dispatcher.forward(request, response);
                }
                request.setAttribute("errore", "No-error");
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                dispatcher.forward(request, response);
            } else if (operation.equals("Delete")){
                try {
                    if(!carrelloList.isEmpty()){
                        for(Carrello c: carrelloList){
                            if(!contenereList.isEmpty()){
                                for(Contenere con: contenereList){
                                    if(con.getCarrello().equals(c.getUtente())){
                                        if(con.getZaino().equals(code)){
                                            c.setTotale(c.getTotale() - (zainoDAO.doRetrieveByCode(code).getPrezzo() * con.getNumQuantita()));
                                            c.setnZaini(c.getnZaini() - con.getNumQuantita());
                                            request.getSession().setAttribute("cart",c);
                                            carrelloDAO.doUpdate(c);
                                            contenereList.remove(con);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    zainoDAO.doDelete(code);
                }catch (Exception exception){
                    exception.printStackTrace();
                    request.setAttribute("errore", "Codice non presente");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                    dispatcher.forward(request, response);
                }
                request.setAttribute("errore", "No-error");
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                dispatcher.forward(request, response);
            }else{
                request.setAttribute("errore", "Nessun codice inserito");
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                dispatcher.forward(request, response);
            }
        }else{
            RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
