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

import java.util.List;

@WebServlet (value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = null;
        if(request.getParameter("email") != null) {
            email = request.getParameter("email");
        }

        String password = null;
        if(request.getParameter("email") != null) {
            password = request.getParameter("password");
        }

        if((email.equals("")) || (password.equals(""))){
            request.setAttribute("error", "campo incompleto");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request,response);
        }

        UtenteDAO utenteDAO = new UtenteDAO();
        Utente utente = utenteDAO.doRetrieveByEmailPassword(email, password);

        if (utente == null){
            request.setAttribute("error","utente not found");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request,response);
        }
        else {
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            Carrello cart = carrelloDAO.doRetrieveByEmail(utente.getEmail());
            ContenereDAO contenereDAO = new ContenereDAO();
            List<Contenere> contenere = contenereDAO.doRetrieveByCart(utente.getEmail());
            OrdineDAO ordineDAO = new OrdineDAO();
            List<Ordine> ordini = ordineDAO.doRetrieveAllByMail(utente.getEmail());
            synchronized (this) {
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);
                session.setAttribute("cart", cart);
                session.setAttribute("contenereC",contenere);
                session.setAttribute("ordini",ordini);
                if (utente.isAdmin())
                    request.getSession().setAttribute("mode", 1);
                else
                    request.getSession().setAttribute("mode", 2);
                RequestDispatcher dispatcher = request.getRequestDispatcher("MostraZainiServlet");
                dispatcher.forward(request, response);
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
