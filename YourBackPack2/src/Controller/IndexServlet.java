package Controller;

import Model.Bean.Contenere;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Model.Bean.Carrello;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "IndexServlet", value = "/indexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        synchronized (this){
            Integer mode = (Integer) session.getAttribute("mode");
            System.out.println(mode);
            if(mode == null) {
                session.setAttribute("mode", 3);
                Carrello cart = new Carrello();
                List<Contenere> contenere = new ArrayList<>();
                session.setAttribute("contenere", contenere);
                cart.setUtente("NULL");
                System.out.println(cart.getUtente());
                session.setAttribute("cart", cart);
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("MostraZainiServlet");
        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
