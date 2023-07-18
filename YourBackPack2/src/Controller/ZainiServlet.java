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

@WebServlet(name = "ZainiServlet", value = "/ZainiServlet")
public class ZainiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codice = request.getParameter("codice");
        ZainoDAO zainoDAO = new ZainoDAO();
        if (codice != null) {
            Zaino z = zainoDAO.doRetrieveByCode(codice);
            if(z != null){
                request.setAttribute("zaini",z);
            }
            RequestDispatcher rd = request.getRequestDispatcher("zaino.jsp");
            rd.forward(request,response);
        }else{
            RequestDispatcher rd = request.getRequestDispatcher("MostraZaino.jsp");
            rd.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
