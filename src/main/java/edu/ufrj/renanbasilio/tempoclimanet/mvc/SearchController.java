/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers.IFHandler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author renan
 */
public class SearchController extends HttpServlet {
    
    /**
     * Initializes a DriverPool in the PoolManager with the database utilized by
     * this Servlet.
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException {
        DriverPool pool = PoolManager.getInstance().addPool("tempoclimanet");
        pool.setServer("localhost");
        pool.setPort(5432);
        pool.setDatabase("tempoclimanet");
        pool.setCredentials("postgres", "admin");
        pool.initialize();
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
