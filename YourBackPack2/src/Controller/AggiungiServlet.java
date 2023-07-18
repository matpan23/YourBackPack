package Controller;

import Model.Bean.Zaino;
import Model.ZainoDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@MultipartConfig
@WebServlet(name = "AggiungiServlet", value = "/AggiungiServlet")
public class AggiungiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = "No-error";
        Zaino newZ = new Zaino();
        String code = request.getParameter("code");
        newZ.setCodice(code);
        String titolo = request.getParameter("titolo");
        newZ.setTitolo(titolo);
        String descrizione = request.getParameter("descrizione");
        newZ.setDescrizione(descrizione);
        int prezzo = -1;
        int quantita = -1;
        try {
            prezzo = Integer.parseInt(request.getParameter("prezzo"));
            newZ.setPrezzo(prezzo);
            quantita = Integer.parseInt(request.getParameter("quantDisp"));
            newZ.setQuantitaDisp(quantita);

            Part filePart = request.getPart("img");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String destinazione = "ZainoIMG" + File.separator + fileName;
            Path pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));
            InputStream fileInputStream = filePart.getInputStream();
            Files.copy(fileInputStream, pathDestinazione);
        }catch(Exception exception){
            error = "Errore inserimento dati nel form";
            request.setAttribute("errore",error);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
            dispatcher.forward(request, response);
        }

        if(code.isEmpty() || titolo.isEmpty() || prezzo < 0 || quantita < 0){
            error = "Errore inserimento dati nel form";
            request.setAttribute("errore",error);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
            dispatcher.forward(request, response);
        }else {
            ZainoDAO zainoDAO = new ZainoDAO();
            if(zainoDAO.doRetrieveByCode(code) != null) {
                error = "Codice già esistente";
            }else {
                if (error.equals("No-error")) {
                    zainoDAO.doSave(newZ);
                    request.setAttribute("errore", error);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("errore", error);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                    dispatcher.forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
