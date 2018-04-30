<%@page import="java.net.URL"%>
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
            Data-hora:  
            <form method="GET" action="controller">
                <input type="text" size="16" name="search" 
                      value="04/04/2018 10:10"
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
                Temperatura: <span id="temperatura" class="classTexto1">${MEDICAO.temperatura}ºC</span><br>
                Umidade: <span id="umidade" class="classTexto1">${MEDICAO.umidade}%</span><br>
                Ponto de orvalho: <span id="orvalho" class="classTexto1">${MEDICAO.orvalho}ºC</span><br>
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
                Data-hora: <span id="datahoraobs" class="classTexto1">04/04/2018 10:10</span><br>
                Altura das ondas: <span id="altondas" class="classTexto1">0,9 m</span><br>
                Temperatura da água: <span id="tempagua" class="classTexto1">20ºC</span><br>
                Bandeira do serviço de guarda-vidas: 
                <span id="bandsalvavidas" style="color:forestgreen;font-weight:bold;">
                    verde
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
