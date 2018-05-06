package edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexHandler implements IFHandler {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        String pagina = "index.jsp";
        ModeloMedicao medicoes;
        ModeloObservacao observacoes;

        if (request.getParameter("data") != null) {
            String data = request.getParameter("data");
            medicoes = ModeloMedicao.fromDB(data);
            observacoes = ModeloObservacao.fromDB(data);
        }
        else {
            switch (request.getParameter("q")) {
                case "next":
                    observacoes = ModeloObservacao.nextFromDB(request.getParameter("dataobs"));
                    medicoes = ModeloMedicao.nextFromDB(request.getParameter("datamed"));
                    break;
                case "prev":
                    observacoes = ModeloObservacao.prevFromDB(request.getParameter("dataobs"));
                    medicoes = ModeloMedicao.prevFromDB(request.getParameter("datamed"));
                    break;
                default:
                    medicoes = new ModeloMedicao();
                    observacoes = new ModeloObservacao();
                    break;
            }
        }
        
        request.setAttribute("MEDICAO", medicoes);
        request.setAttribute("OBSERVACAO", observacoes);
        
        return pagina;
    }
}
