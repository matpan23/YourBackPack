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
import java.util.List;

@WebServlet(name = "MostraZainiServlet", value = "/MostraZainiServlet")
public class MostraZainiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZainoDAO zainoDAO = new ZainoDAO();
        List<Zaino> lstZaini = zainoDAO.doRetrieveAll();
        request.setAttribute("Zaini", lstZaini);

        RequestDispatcher rd = request.getRequestDispatcher("MostraZaino.jsp");
        rd.forward(request,response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
