<%@page import="edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao"%>
<%@page import="edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="import java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% URL contexto = new URL(
            "http",
            request.getServerName(),
            request.getServerPort(),
            request.getContextPath());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <title>TempoClima</title>
        
        <link rel="stylesheet" type="text/css" href="<%= contexto%>/Resources/css/index.css"/>

    </head>
    
    <body background="<%= contexto%>/Resources/Images/praia.jpg">
        <div id="idHeader">
            <div id="idNomeSite">TempoClimaNET</div>
            <div id="idDELPOLI">EEL418 / DEL / POLI / UFRJ</div>
        </div>
        <div id="idlista">&nbsp;</div>
        
        
        <div id="idDivLocal">
            <br>
            Local: <span class="classTexto1">Rio de Janeiro, RJ, Brasil - Posto 9</span>
        </div>
        <div id="idDivDatahora">
            <br> 
            <form method="GET" action="consulta">
                Data-hora: 
                <input type="text" size="16" name="data" 
                       value=<% 
                                // Inicializa o formato da data da caixa de busca.
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                String dateStr = "";
                                
                                // Se o tamanho dos parâmetros no request for zero, trata-se
                                // do primeiro carregamento.
                                if (request.getParameterMap().size() == 0) {
                                    // Inicializa uma nova data com a data e hora atuais e
                                    // formata a mesma em uma string.
                                    Date date = new Date();
                                    dateStr = dateFormat.format(date);

                                    Connection conn = PoolManager.getInstance().getPool("tempoclimanet").getConnection();

                                    // Carrega as medidas mais recentes da base de dados.
                                    ModeloMedicao medidas = ModeloMedicao.fromDB(conn, dateStr);
                                    ModeloObservacao observacoes = ModeloObservacao.fromDB(conn, dateStr);
                                    
                                    // Seta os atributos referentes às mesmas no pedido.
                                    request.setAttribute("MEDICAO", medidas);
                                    request.setAttribute("OBSERVACAO", observacoes);

                                }
                                // Se o request não possui parâmetro data, este provém da ativação
                                // de um controle.
                                else if (request.getParameter("data") == null) {
                                    // Cria e formata a data atual para preencher a caixa de busca.
                                    Date date = new Date();
                                    dateStr = dateFormat.format(date);
                                }
                                // Caso contrário, o request provém de uma busca.
                                else {
                                    // Então seta o texto da caixa de busca como a própria data e hora
                                    // da busca.
                                    dateStr = request.getParameter("data");
                                }
                                
                                // Preenche a caixa de busca.
                                out.println("\""+dateStr+"\"");
                                %>
                       style="font-size:1.05em;text-align:center;"/>
                <button type="submit" style="font-size:1.05em;">BUSCAR</button>
            </form>
            <br>
            <form method="GET" action="consulta" style="margin-top:10px;">
                <input type="hidden" name="datamed" value="${MEDICAO.datahoraautom}"/>
                <input type="hidden" name="dataobs" value="${OBSERVACAO.datahoraobs}"/>
                <button type="submit" name="q" value="prev"
                        style="font-size:1.05em;">Anterior <<</button>
                <button type="submit" name="q" value="next"
                        style="font-size:1.05em;">>> Posterior</button>
            </form>
        </div>

        <div id="idDiv1">
            
            <div id="idDivMedAutom" class="shadowBorder">
                <span style="line-height:0.9;">
                    MEDIDAS AUTOMÁTICAS
                    <br>
                    <span class="classSubTexto">(igual ou anterior ao momento consulta)</span>
                </span>
                <br>
                <br>
                Data-hora: <span id="datahoraautom" class="classTexto1">${MEDICAO.datahoraautom}</span><br>
                Temperatura: <span id="temperatura" class="classTexto1">${MEDICAO.temperatura} ºC</span><br>
                Umidade: <span id="umidade" class="classTexto1">${MEDICAO.umidade}%</span><br>
                Ponto de orvalho: <span id="orvalho" class="classTexto1">${MEDICAO.orvalho} ºC</span><br>
                Pressão atmosférica: <span id="pressao" class="classTexto1">${MEDICAO.pressao} hPa</span><br>
                Taxa de precipitação: <span id="precipitacao" class="classTexto1">${MEDICAO.precipitacao} mm/h</span><br>
                Precipitação acumulada: <span id="precipacumul" class="classTexto1">${MEDICAO.precipacumul} mm</span><br>
                (últimas 24h)<br>
                Velocidade do Vento: <span id="velvento" class="classTexto1">${MEDICAO.velvento} km/h</span><br>
                Direção do vento: <span id="dirvento" class="classTexto1">${MEDICAO.dirvento}</span><br>
            </div>
            
            <div id="idDivObserv" class="shadowBorder">
                <span style="line-height:0.9;">
                    OBSERVAÇÕES
                    <br>
                    <span class="classSubTexto">(igual ou anterior ao momento consulta)</span>
                </span>
                <br>
                <br>
                Data-hora: <span id="datahoraobs" class="classTexto1">${OBSERVACAO.datahoraobs}</span><br>
                Altura das ondas: <span id="altondas" class="classTexto1">${OBSERVACAO.altondas} m</span><br>
                Temperatura da água: <span id="tempagua" class="classTexto1">${OBSERVACAO.tempagua} ºC</span><br>
                Bandeira do serviço de guarda-vidas: 
                <span id="bandsalvavidas" style="color:${OBSERVACAO.bandeira.cor}; font-weight:bold;">
                    <div class="tooltip">${OBSERVACAO.bandeira.nome}
                        <span class="tooltiptext">${OBSERVACAO.bandeira.desc}</span>
                    </div>
                </span><br>
            </div>
            
            
            <div id="idDivFotos" class="shadowBorder">
                FOTOS<br>
                <img class="meia-caixa" src="<%= contexto%>/Resources/Images/praia.jpg" border="0">
                <img class="meia-caixa" src="<%= contexto%>/Resources/Images/radar.png"/>
            </div>
            
            <div id="idDivGraficos" class="shadowBorder">
                GRÁFICOS<br>
                <img class="caixa-inteira" src="<%= contexto%>/Resources/Images/grafico.temper.png"/>
            </div>
            
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
        </div>
            
    </body>
</html>
