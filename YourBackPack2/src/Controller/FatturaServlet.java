package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import Model.Bean.OrdineC;
import Model.ZainoDAO;
import Model.OrdineCDAO;
import Model.Bean.Zaino;
import Model.OrdineDAO;
import Model.Bean.Ordine;

/**
 * Servlet implementation class FatturaServlet
 */
@WebServlet("/FatturaServlet")
public class FatturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdineCDAO ordineCDAO = new OrdineCDAO();
    private ZainoDAO zainoDAO = new ZainoDAO();
    private OrdineDAO ordineDAO = new OrdineDAO();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numeroOrdine = Integer.parseInt(request.getParameter("numeroOrdine"));
        List<OrdineC> ordine = ordineCDAO.doRetrieveAllByOrdine(numeroOrdine);
		Ordine testOrd = ordineDAO.doRetrieveAllByCode(numeroOrdine);
        List<Zaino> lstZaini = new ArrayList<>();
		Zaino z = new Zaino();
		for (OrdineC c : ordine) {
			z = zainoDAO.doRetrieveByCode(c.getZaino());
			lstZaini.add(z);
		}
		if (ordine != null && lstZaini != null) {
			HttpSession session = request.getSession();
			session.setAttribute("OrdineC", ordine);
			session.setAttribute("lstZaini", lstZaini);
			session.setAttribute("testOrd", testOrd);
			response.sendRedirect("Ricevuta.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
