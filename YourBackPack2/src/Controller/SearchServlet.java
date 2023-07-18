package Controller;

import Model.Bean.Zaino;
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

@WebServlet(name = "SearchServlet", value = "/search-servlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("search");
        ZainoDAO zainoDAO = new ZainoDAO();
        List<Zaino> lstZaini = zainoDAO.doRetrieveAll();
        List<Zaino> lstZainiR = new ArrayList<>();
        if(nome != null) {
            for (Zaino z : lstZaini) {
                if (nome.equalsIgnoreCase(z.getTitolo()) || z.getTitolo().toLowerCase().contains(nome.toLowerCase())) {
                    lstZainiR.add(z);
                } else if(nome.equals(z.getCodice())){
                    lstZainiR.add(z);
                }
            }
        }
        request.setAttribute("zaini", lstZainiR);
        RequestDispatcher rd = request.getRequestDispatcher("MostraZaino.jsp");
        rd.forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
