<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% URL contexto = new URL(
            request.getScheme(),
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
        <div id="header">
            <div id="nomeSite">TempoClimaNET</div>
            <div id="DELPOLI">EEL418 / DEL / POLI / UFRJ</div>
        </div>
        <div id="local" class="white-box label">
            &nbsp;&nbsp;&nbsp;&nbsp;
            Local: <span class="texto">Rio de Janeiro, RJ, Brasil - Posto 9</span>
        </div>
        <div id="busca" class="white-box label">
            <form method="GET" action="consulta">
                Data-hora: 
                <input type="text" size="16" name="data" 
                       id="caixaBusca"
                       value="${DATABUSCA}"
                       class="search-box"/>
                <button id="botaoBuscar" class="botao-buscar" 
                        type="submit" onclick="buscar(data.value)">BUSCAR</button>
            </form>
            <form id="scrollform" method="POST" action="scroll">
                <input id='datamed' type="hidden" name="datamed" value="${MEDICAO.datahoraautom}"/>
                <input id='dataobs' type="hidden" name="dataobs" value="${OBSERVACAO.datahoraobs}"/>
                <input id='databusca' type="hidden" name="databusca" value="${DATABUSCA}"/>
            </form>
        </div>
                
        <div id="mainDiv" class="flex-container">
            
            <div id="idDivMedAutom" class="flex-child shadow-border">
                <span class="label">
                    MEDIDAS AUTOMÁTICAS
                    <span id="scrollformControlsMedicao" class="scroll-controls">
                        <noscript>
                        <button type="submit" name="med" value="prev" form="scrollform">&#9664;</button>
                        <button type="submit" name="med" value="next" form="scrollform">&#9654;</button>
                        </noscript>
                    </span>
                    <br>
                    <span class="sub-texto">(igual ou anterior ao momento consulta)</span>
                </span>
                <br>
                <br>
                <div id="divMedicao" class="displayMedicao">
                    <noscript>
                        <div>
                            Data-hora: <span name="datahoraautom" class="texto">${MEDICAO.datahoraautom}</span><br>
                            Temperatura: <span name="temperatura" class="texto">${MEDICAO.temperatura} ºC</span><br>
                            Umidade: <span name="umidade" class="texto">${MEDICAO.umidade}%</span><br>
                            Ponto de orvalho: <span name="orvalho" class="texto">${MEDICAO.orvalho} ºC</span><br>
                            Pressão atmosférica: <span name="pressao" class="texto">${MEDICAO.pressao} hPa</span><br>
                            Taxa de precipitação: <span name="precipitacao" class="texto">${MEDICAO.precipitacao} mm/h</span><br>
                            Precipitação acumulada: <span name="precipacumul" class="texto">${MEDICAO.precipacumul} mm</span>
                            <span class="sub-texto">&nbsp;&nbsp;(últimas 24h)</span><br>
                            Velocidade do Vento: <span name="velvento" class="texto">${MEDICAO.velvento} km/h</span><br>
                            Direção do vento: <span name="dirvento" class="texto">${MEDICAO.dirvento}</span><br>
                        </div>
                    </noscript>
                </div>
            </div>

            <div id="idDivObserv" class="flex-child shadow-border">
                <span class="label">
                    OBSERVAÇÕES
                    <span id="scrollformControlsObservacao" class="scroll-controls">
                        <noscript>
                        <button type="submit" name="obs" value="prev" form="scrollform">&#9664;</button>
                        <button type="submit" name="obs" value="next" form="scrollform">&#9654;</button>
                        </noscript>
                    </span>
                    <br>
                    <span class="sub-texto">(igual ou anterior ao momento consulta)</span>
                </span>
                <br>
                <br>
                <div id="divObs" class="displayObservacao">
                    <noscript>
                        <div class="slide">
                            Data-hora: <span name="datahoraobs" class="texto">${OBSERVACAO.datahoraobs}</span><br>
                            Altura das ondas: <span name="altondas" class="texto">${OBSERVACAO.altondas} m</span><br>
                            Temperatura da água: <span name="tempagua" class="texto">${OBSERVACAO.tempagua} ºC</span><br>
                            Bandeira: 
                            <span name="bandsalvavidas" style="color:${OBSERVACAO.bandeira.cor}; font-weight:bold;">
                                <div class="tooltip">${OBSERVACAO.bandeira.nome}
                                    <span class="tooltiptext">${OBSERVACAO.bandeira.desc}</span>
                                </div>
                            </span>
                        </div>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                    </noscript>
                </div>
                <div class="bottom-align">
                    <div class="center-justify">
                        <p class="sub-texto">
                        Bandeira levantada pelo serviço de guarda vidas no 
                        momento em que a observação foi realizada.
                        </p>
                    </div>
                </div>
            </div>


            <div id="idDivFotos" class="flex-child shadow-border">
                FOTOS<br>
                <div id="divFotos" class='displayFotos'>
                    <img class="meia-caixa" src="<%= contexto%>/resources/img/praia.jpg" />
                    <img class="meia-caixa" src="<%= contexto%>/resources/img/radar.png" />
                </div>
            </div>

            <div id="idDivGraficos" class="flex-child shadow-border">
                GRÁFICOS<br>
                <div id="divGraficos" class="displayGraficos">
                    <img class="caixa-inteira" src="<%= contexto%>/resources/img/grafico.temper.png"/>
                </div>
            </div>
        </div>
        <div class="white-box label">Observador? <a href="<%= contexto%>/portal">Faça Login Aquí</a></div
        <br><br>
    </body>
</html>
