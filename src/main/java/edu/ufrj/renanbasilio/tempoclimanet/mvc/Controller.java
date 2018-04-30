package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers.IFHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF8");
        response.setCharacterEncoding("UTF8");
        IFHandler handler;
        System.out.println("Received a request.");
        try {
            String handlerClass = "edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers.";
            if(request.getParameter("search") != null) handlerClass += "IndexHandler";
            else handlerClass += request.getParameter("PageHandler");
            
            System.out.println("Handler class is " + handlerClass);
            
            handler = (IFHandler) Class.forName(handlerClass).newInstance();
            String returnPage = handler.process(request, response);
            //throw new ClassNotFoundException();
            request.getRequestDispatcher(returnPage).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("EXCESSAO_CONTROLLER", e);
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
