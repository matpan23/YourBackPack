package Controller;

import Model.Bean.*;
import Model.ZainoDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MostraCarrelloServlet", value = "/MostraCarrelloServlet")
public class MostraCarrelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized (this) {
            HttpSession session = request.getSession();
            List<Contenere> contenere = (List<Contenere>) session.getAttribute("contenere");
            Carrello carrello = (Carrello) session.getAttribute("cart");
            ZainoDAO zainoDAO = new ZainoDAO();
            List<Zaino> zainos = new ArrayList<>();
            if(!contenere.isEmpty()) {
                for (Contenere c : contenere) {
                    Zaino z = zainoDAO.doRetrieveByCode(c.getZaino());
                    zainos.add(z);
                }
            }
            request.setAttribute("Zaini", zainos);

            RequestDispatcher rd = request.getRequestDispatcher("Carrello.jsp");
            rd.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
