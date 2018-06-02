/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.postgresql.ds.PGConnectionPoolDataSource;

/**
 * Servlet que lida com pedidos realizados através do Ajax.
 * @author RenanBasilio
 */
public class AjaxController extends HttpServlet {

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
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        Connection conn;
        try {
            conn = ((PGConnectionPoolDataSource)PoolManager.getInstance().getPool("tempoclimanet")).getConnection();
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new ServletException("Failed to get connection to database.");
        }
        
        String requestType = request.getParameter("type");
        String date = request.getParameter("date");
        
        ModeloMedicao medicoes;
        ModeloObservacao observacoes;
        
        switch (requestType) {
            // A lookup request is a basic search request.
            case "lookup":
                medicoes = ModeloMedicao.fromDB(conn, date);
                observacoes = ModeloObservacao.fromDB(conn, date);
                JsonObject compositeJson = Json.createObjectBuilder()
                        .add("medicoes", medicoes.toJSON())
                        .add("observacoes", observacoes.toJSON())
                        .add("datahora", date)
                        .build();
                out.print(compositeJson.toString());
                break;
            // A scrollleft request returns the object immediately before the date requested.
            case "scrollleftMed":
                medicoes = ModeloMedicao.prevFromDB(conn, date);
                out.print(medicoes.toJSON().toString());
                break;
            case "scrollleftObs":
                observacoes = ModeloObservacao.prevFromDB(conn, date);
                out.print(observacoes.toJSON().toString());
                break;
            // A scrollright request returns the object immediately after the date requested.
            case "scrollrightMed":
                medicoes = ModeloMedicao.nextFromDB(conn, date);
                out.print(medicoes.toJSON().toString());
                break;
            case "scrollrightObs":
                observacoes = ModeloObservacao.nextFromDB(conn, date);
                out.print(observacoes.toJSON().toString());
                break;
            default:
                break;
        }
        
        out.flush();
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
