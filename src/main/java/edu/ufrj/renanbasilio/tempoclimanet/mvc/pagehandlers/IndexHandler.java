package edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.PoolManager;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Tratador de página referente a consultas realizadas na página principal.
 * @author Renan Basilio
 */
public class IndexHandler implements IFHandler {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        // Seta a página a ser retornada.
        String pagina = "index.jsp";
        
        // Declara os modelos passíveis de serem retornados por este processo.
        ModeloMedicao medicoes;
        ModeloObservacao observacoes;

        Connection conn = PoolManager.getInstance().getPool("tempoclimanet").getConnection();

        // Se a consulta estiver sendo feita por data, inicializa ambos os
        // modelos com uma consulta pela mesma ao banco de dados.
        if (request.getParameter("data") != null) {
            String data = request.getParameter("data");
            medicoes = ModeloMedicao.fromDB(conn, data);
            observacoes = ModeloObservacao.fromDB(conn, data);
        }
        // Caso contrário, temos a ativação de um controle.
        else {
            switch (request.getParameter("q")) {
                // Controle que representa o botão "Posterior". Inicializa
                // os modelos com os dados relevantes a partir do banco de
                // dados. Aquí, dataobs e datamed são as datas do modelo
                // carregado no momento do pedido.
                case "next":
                    observacoes = ModeloObservacao.nextFromDB(conn, request.getParameter("dataobs"));
                    medicoes = ModeloMedicao.nextFromDB(conn, request.getParameter("datamed"));
                    break;
                // Controle que representa o botão "Anterior".
                case "prev":
                    observacoes = ModeloObservacao.prevFromDB(conn, request.getParameter("dataobs"));
                    medicoes = ModeloMedicao.prevFromDB(conn, request.getParameter("datamed"));
                    break;
                // Controle desconhecido; retorna um par de modelos vazios.
                default:
                    medicoes = new ModeloMedicao();
                    observacoes = new ModeloObservacao();
                    break;
            }
        }
        
        // Seta os atributos MEDICAO e OBSERVACAO do pedido com os modelos
        // inicializados.
        request.setAttribute("MEDICAO", medicoes);
        request.setAttribute("OBSERVACAO", observacoes);
        
        return pagina;
    }
}
