/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.PoolManager;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author RenanBasilio
 */
public class ScrollHandler implements IFHandler{

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {

        // Seta a página a ser retornada.
        String pagina = "index.jsp";
        
        // Declara os modelos passíveis de serem retornados por este processo.
        ModeloMedicao medicoes;
        ModeloObservacao observacoes;
        
        Connection conn;
        try {
            conn = ((DataSource)PoolManager.getInstance().getPool("tempoclimanet")).getConnection();
        } catch (SQLException e1) {
            e1.printStackTrace();
            return pagina;
        }

        
        if (request.getParameter("med") != null){
            observacoes = ModeloObservacao.fromDB(conn, request.getParameter("dataobs"));
            switch (request.getParameter("med")) {
                case "next":
                    medicoes = ModeloMedicao.nextFromDB(conn, request.getParameter("datamed"));
                    if (!medicoes.isLoaded()) medicoes = ModeloMedicao.fromDB(conn, request.getParameter("datamed"));
                    break;
                case "prev":
                    medicoes = ModeloMedicao.prevFromDB(conn, request.getParameter("datamed"));
                    if (!medicoes.isLoaded()) medicoes = ModeloMedicao.fromDB(conn, request.getParameter("datamed"));
                    break;
                default:
                    medicoes = ModeloMedicao.fromDB(conn, request.getParameter("datamed"));
                    break;
            }
        }
        else if (request.getParameter("obs") != null){
            medicoes = ModeloMedicao.fromDB(conn, request.getParameter("datamed"));
            switch (request.getParameter("obs")) {
                case "next":
                    observacoes = ModeloObservacao.nextFromDB(conn, request.getParameter("dataobs"));
                    if (!observacoes.isLoaded()) observacoes = ModeloObservacao.fromDB(conn, request.getParameter("dataobs"));
                    break;
                case "prev":
                    observacoes = ModeloObservacao.prevFromDB(conn, request.getParameter("dataobs"));
                    if (!observacoes.isLoaded()) observacoes = ModeloObservacao.fromDB(conn, request.getParameter("dataobs"));
                    break;
                default:
                    observacoes = ModeloObservacao.fromDB(conn, request.getParameter("dataobs"));
                    break;
            }
        }
        else {
            medicoes = new ModeloMedicao();
            observacoes = new ModeloObservacao();
        }
        
        // Seta os atributos MEDICAO e OBSERVACAO do pedido com os modelos
        // inicializados.
        request.setAttribute("DATABUSCA", request.getParameter("databusca"));
        request.setAttribute("MEDICAO", medicoes);
        request.setAttribute("OBSERVACAO", observacoes);
        
        return pagina;
    }
    
}
