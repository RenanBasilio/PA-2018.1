/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers.IFHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet que lida com consultas ao banco de dados na página principal.
 * @author Renan Basilio
 */
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Inicializa a DiverPool no PoolManager com a base de dados a ser utilizada
     * por este Servlet.
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException {
        if(PoolManager.getInstance().getPool("tempoclimanet") == null){
            try {
                Class.forName("org.postgresql.Driver");
                PoolManager.getInstance().addPool("tempoclimanet", (DataSource) InitialContext.doLookup("java:comp/env/jdbc/tempoclimanet"));
            } catch (ClassNotFoundException | NamingException ex) {
                Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF8");
        response.setCharacterEncoding("UTF8");
        IFHandler handler;
        try {
            String handlerClass = "edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers.IndexHandler";
            
            handler = (IFHandler) Class.forName(handlerClass).newInstance();
            String returnPage = handler.process(request, response);
            //throw new ClassNotFoundException();
            request.getRequestDispatcher(returnPage).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("EXCESSAO_CONTROLLER", e);
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
            e.printStackTrace();
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
