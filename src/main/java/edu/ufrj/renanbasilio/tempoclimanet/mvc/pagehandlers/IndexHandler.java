package edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.PoolManager;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.postgresql.ds.PGConnectionPoolDataSource;

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
        String dataBusca;

        Connection conn;
        try {
            conn = ((PGConnectionPoolDataSource)PoolManager.getInstance().getPool("tempoclimanet")).getConnection();
        } catch (SQLException e1) {
            e1.printStackTrace();
            return pagina;
        }

        // Se a consulta estiver sendo feita por data, inicializa ambos os
        // modelos com uma consulta pela mesma ao banco de dados.
        if (request.getParameter("data") != null) {
            dataBusca = request.getParameter("data");
            medicoes = ModeloMedicao.fromDB(conn, dataBusca);
            observacoes = ModeloObservacao.fromDB(conn, dataBusca);
        }
        // Se chamado sem nenhum parametro, faz uma busca com a data atual.
        else {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            // Inicializa uma nova data com a data e hora atuais e
            // formata a mesma em uma string.
            Date date = new Date();
            dataBusca = dateFormat.format(date);

            // Carrega as medidas mais recentes da base de dados.
            medicoes = ModeloMedicao.fromDB(conn, dataBusca);
            observacoes = ModeloObservacao.fromDB(conn, dataBusca);
        }
        
        
        // Seta os atributos MEDICAO e OBSERVACAO do pedido com os modelos
        // inicializados.
        request.setAttribute("DATABUSCA", dataBusca);
        request.setAttribute("MEDICAO", medicoes);
        request.setAttribute("OBSERVACAO", observacoes);
        
        return pagina;
    }
}
