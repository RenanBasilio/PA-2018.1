package edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexHandler implements IFHandler {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        String pagina = "index.jsp";

        String data = request.getParameter("data");
        ModeloMedicao medidas = ModeloMedicao.fromDB(data);
        ModeloObservacao observacoes = ModeloObservacao.fromDB(data);

        request.setAttribute("MEDICAO", medidas);
        request.setAttribute("OBSERVACAO", observacoes);
        return pagina;
    }
}
