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


            <div id="divLocal" class="white-boxed">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Local:&nbsp;<span class="classTexto1">Rio de Janeiro, RJ, Brasil - Posto 9</span>
            </div>
            <div id="divBusca" class="white-boxed">
                <form method="GET" action="consulta">
                    Data-hora: 
                    <input type="text" size="16" name="data" 
                           id="caixaBusca"
                           value="${DATABUSCA}"
                           style="font-size:1.05em;text-align:center;"/>
                    <button id="botaoBuscar"
                            type="button" 
                            style="font-size:1.05em;" 
                            onclick="buscar(data.value);">BUSCAR</button>
                </form>
                <form id="scrollform" method="POST" action="scroll">
                    <input id='datamed' type="hidden" name="datamed" value="${MEDICAO.datahoraautom}"/>
                    <input id='dataobs' type="hidden" name="dataobs" value="${OBSERVACAO.datahoraobs}"/>
                    <input id='databusca' type="hidden" name="databusca" value="${DATABUSCA}"/>
                </form>
            </div>
            <div id="idDiv1">

                <div id="idDivMedAutom" class="shadowBorder">
                    <span style="line-height:0.9;">
                        MEDIDAS AUTOMÁTICAS
                        <span id="scrollformControlsMedicao" style="float:right">
                            <noscript>
                            <button type="submit" name="med" value="prev" form="scrollform"><</button>
                            <button type="submit" name="med" value="next" form="scrollform">></button>
                            </noscript>
                            <div id="scrollformControlsJsMedicao">
                            </div>
                        </span>
                        <br>
                        <span class="classSubTexto">(igual ou anterior ao momento consulta)</span>
                    </span>
                    <br>
                    <br>
                    <div id="divMedicao" class="displayMedicao">
                        <noscript>
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
                        </noscript>
                    </div>
                </div>

                <div id="idDivObserv" class="shadowBorder">
                    <span style="line-height:0.9;">
                        OBSERVAÇÕES
                        <span id="scrollformControlsObservacao" style="float:right">
                            <noscript>
                            <button type="submit" name="obs" value="prev" form="scrollform"><</button>
                            <button type="submit" name="obs" value="next" form="scrollform">></button>
                            </noscript>
                            <div id="scrollformControlsJsObservacao">
                            </div>
                        </span>
                        <br>
                        <span class="classSubTexto">(igual ou anterior ao momento consulta)</span>
                    </span>
                    <br>
                    <br>
                    <div id="divObs" class="displayObservacao">
                        <noscript>
                            <div class="slide">
                                Data-hora: <span id="datahoraobs" class="classTexto1">${OBSERVACAO.datahoraobs}</span><br>
                                Altura das ondas: <span id="altondas" class="classTexto1">${OBSERVACAO.altondas} m</span><br>
                                Temperatura da água: <span id="tempagua" class="classTexto1">${OBSERVACAO.tempagua} ºC</span><br>
                                Bandeira: 
                                <span id="bandsalvavidas" style="color:${OBSERVACAO.bandeira.cor}; font-weight:bold;">
                                    <div class="tooltip">${OBSERVACAO.bandeira.nome}
                                        <span class="tooltiptext">${OBSERVACAO.bandeira.desc}</span>
                                    </div>
                                </span>
                            </div>
                            <br>
                            <br>
                            <br>
                        </noscript>
                    </div>
                    <div class="bottom-align">
                        <div class="center-justify">
                            <p class="classSubTexto">
                            Bandeira levantada pelo serviço de guarda vidas no 
                            momento em que a observação foi realizada.
                            </p>
                        </div>
                    </div>
                </div>


                <div id="idDivFotos" class="shadowBorder">
                    FOTOS<br>
                    <div class='divFotos' style=''>
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
