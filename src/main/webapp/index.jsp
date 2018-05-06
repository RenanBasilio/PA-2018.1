<%@page import="edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloObservacao"%>
<%@page import="edu.ufrj.renanbasilio.tempoclimanet.mvc.models.ModeloMedicao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat" %>
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
        
        <link rel="stylesheet" type="text/css" href="<%= contexto%>/Resources/CSS/index.css"/>

    </head>
    
    <body>
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
                       value=<% DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                
                                if (request.getParameter("data") == null) {
                                    Date date = new Date();
                                    String dateStr = dateFormat.format(date);
                                    
                                    ModeloMedicao medidas = ModeloMedicao.fromDB(dateStr);
                                    ModeloObservacao observacoes = ModeloObservacao.fromDB(dateStr);
                                    request.setAttribute("MEDICAO", medidas);
                                    request.setAttribute("OBSERVACAO", observacoes);
                                    
                                    out.println("\""+dateStr+"\"");
                                }
                                else {
                                    out.println("\""+request.getParameter("data")+"\"");
                                }
                                 %>
                       style="font-size:1.05em;text-align:center;"/>
                <button type="submit" style="font-size:1.05em;">BUSCAR</button>
                <br>
                <br>
                <button type="submit" value="previous"
                        style="font-size:1.05em;">Anterior <<</button>
                <button type="submit" value="next"
                        style="font-size:1.05em;">>> Posterior</button>
            </form>
        </div>

        <div id="idDiv1">
            
            <div id="idDivMedAutom" class="shadowBorder">
                MEDIDAS AUTOMÁTICAS<br>
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
                OBSERVAÇÕES<br>
                <br>
                Data-hora: <span id="datahoraobs" class="classTexto1">${OBSERVACAO.datahoraobs}</span><br>
                Altura das ondas: <span id="altondas" class="classTexto1">${OBSERVACAO.altondas} m</span><br>
                Temperatura da água: <span id="tempagua" class="classTexto1">${OBSERVACAO.tempagua} ºC</span><br>
                Bandeira do serviço de guarda-vidas: 
                <span id="bandsalvavidas" style="color:${OBSERVACAO.bandeira.cor};font-weight:bold;">
                    <div class="tooltip">${OBSERVACAO.bandeira.nome}
                        <span class="tooltiptext">${OBSERVACAO.bandeira.desc}</span>
                    </div>
                </span><br>
            </div>
            
            
            <div id="idDivFotos" class="shadowBorder">
                FOTOS<br>
                <img class="meia-caixa" src="<%= contexto%>/Resources/Images/praia.jpg"/>
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
