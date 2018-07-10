/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author renan
 */
public class PortalController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("portal/formObservacao.jsp").forward(request, response);
    }

    protected void processSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                ModeloObservacao observacao = new ModeloObservacao();
        
        if(request.getContentType().contains("application/json")) {
            JsonReader rd = Json.createReader(new InputStreamReader(request.getInputStream()));
            JsonObject requestObject = rd.readObject();
            observacao.setAltondas(
                    Float.valueOf(requestObject.getString("altOndas")));
            observacao.setTempagua(
                    Float.valueOf(requestObject.getString("tempAgua")));
            observacao.setBandeira(
                    ModeloObservacao.Bandeira.valueOf(
                            requestObject.getString("bandeira")));
            System.out.println(observacao.toJSON().toString());
        }
        else {
            response.setStatus(400);
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
        processSubmit(request, response);
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
