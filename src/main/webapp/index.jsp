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

        <link rel="stylesheet" type="text/css" href="<%= contexto%>/resources/css/index.css"/>
        <link rel="stylesheet" type="text/css" href="<%= contexto%>/scripts/slick-1.8.0/slick/slick.css"/>
        <link rel="stylesheet" type="text/css" href="<%= contexto%>/scripts/slick-1.8.0/slick/slick-theme.css"/>

        <script type="text/javascript" src="<%= contexto%>/scripts/jquery-3.3.1/dist/jquery.min.js"></script>
        <script type="text/javascript" src="<%= contexto%>/scripts/slick-1.8.0/slick/slick.min.js"></script>
        <script type="text/javascript" src="<%= contexto%>/scripts/controle.js"></script>
    </head>

    <body background="<%= contexto%>/resources/img/praia.jpg">
        <div class="flex-container">
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
                           value="${DATABUSCA}"
                           style="font-size:1.05em;text-align:center;"/>
                    <button type="button" style="font-size:1.05em;" onclick="buscar(data.value);">BUSCAR</button>
                </form>
                <br>
                <form id="scrollform" method="POST" action="scroll">
                    <input id='datamed' type="hidden" name="datamed" value="${MEDICAO.datahoraautom}"/>
                    <input id='dataobs' type="hidden" name="dataobs" value="${OBSERVACAO.datahoraobs}"/>
                    <input id='databusca' type="hidden" name="databusca" value="${DATABUSCA}"/>
                </form>
            </div>
            <div id="idDiv1">

                <div id="idDivMedAutom" class="shadowBorder">
                    <span style="line-height:0.9;display:inline">
                        MEDIDAS AUTOMÁTICAS
                        <span id="scrollformControls" style="float:right">
                            <noscript>
                            <button type="submit" name="med" value="prev" form="scrollform"><</button>
                            <button type="submit" name="med" value="next" form="scrollform">></button>
                            </noscript>
                            <div id="divMedsJsButtons">
                            </div>
                        </span>
                        <br>
                        <span class="classSubTexto">(igual ou anterior ao momento consulta)</span>
                    </span>
                    <br>
                    <br>
                    <div id="divMedsAuto" class="medidasAutomaticas">
                        <div>
                            Data-hora: <span class="classTexto1">${MEDICAO.datahoraautom}</span><br>
                            Temperatura: <span class="classTexto1">${MEDICAO.temperatura} ºC</span><br>
                            Umidade: <span id="umidade" class="classTexto1">${MEDICAO.umidade}%</span><br>
                            Ponto de orvalho: <span id="orvalho" class="classTexto1">${MEDICAO.orvalho} ºC</span><br>
                            Pressão atmosférica: <span id="pressao" class="classTexto1">${MEDICAO.pressao} hPa</span><br>
                            Taxa de precipitação: <span id="precipitacao" class="classTexto1">${MEDICAO.precipitacao} mm/h</span><br>
                            Precipitação acumulada: <span id="precipacumul" class="classTexto1">${MEDICAO.precipacumul} mm</span><br>
                            (últimas 24h)<br>
                            Velocidade do Vento: <span id="velvento" class="classTexto1">${MEDICAO.velvento} km/h</span><br>
                            Direção do vento: <span id="dirvento" class="classTexto1">${MEDICAO.dirvento}</span><br>
                        </div>
                    </div>
                </div>

                <div id="idDivObserv" class="shadowBorder">
                    <span style="line-height:0.9;">
                        OBSERVAÇÕES
                        <span id="scrollformControls" style="float:right">
                            <button type="submit" name="obs" value="prev" form="scrollform"><</button>
                            <button type="submit" name="obs" value="next" form="scrollform">></button>
                        </span>
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
                    </span>
                    <br>
                </div>


                <div id="idDivFotos" class="shadowBorder">
                    FOTOS<br>
                    <div class='divFotos' style='max-width:75%; margin: 2% auto'>
                        <img class="meia-caixa" src="<%= contexto%>/resources/img/praia.jpg" />
                        <img class="meia-caixa" src="<%= contexto%>/resources/img/radar.png" />
                    </div>
                </div>

                <div id="idDivGraficos" class="shadowBorder">
                    GRÁFICOS<br>
                    <img class="caixa-inteira" src="<%= contexto%>/resources/img/grafico.temper.png"/>
                </div>
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
