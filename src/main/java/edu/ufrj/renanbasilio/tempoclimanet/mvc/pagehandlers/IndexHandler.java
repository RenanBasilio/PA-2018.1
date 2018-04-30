package edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexHandler implements IFHandler {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        String pagina = "index.jsp";

        ModeloMedicao medidas = new ModeloMedicao();
        ModeloObservacao observacoes = new ModeloObservacao();

        request.setAttribute("MEDICAO", medidas);
        request.setAttribute("OBSERVACAO", observacoes);
        return pagina;
    }
}
