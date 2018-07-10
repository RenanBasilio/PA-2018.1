<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% URL contexto = new URL(
            "https",
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
        <title>Portal do Observador</title>
        <link rel="stylesheet" type="text/css" href="<%= contexto%>/resources/css/index.css"/>
        <link rel="stylesheet" type="text/css" href="<%= contexto%>/scripts/bgrins-spectrum-98454b5/spectrum.css"/>
        <script type="text/javascript" src="<%= contexto%>/scripts/jquery-3.3.1/dist/jquery.min.js"></script>
        <script type="text/javascript" src="<%= contexto%>/scripts/bgrins-spectrum-98454b5/spectrum.js"></script>
        <script type="text/javascript" src="<%= contexto%>/scripts/util.js"></script>
        <script type="text/javascript" src="<%= contexto%>/portal/scripts/controleform.js"></script>
    </head>
    <body background="<%= contexto%>/resources/img/praia.jpg">
        <div id="header">
            <div id="nomeSite">Portal do Observador</div>
            <div id="DELPOLI">EEL418 / DEL / POLI / UFRJ</div>
        </div>
        <br><br>
        <div id="mainDiv" class="flex-container">
            <div id="divForm" class="shadow-border flex-child center-justify">
                <div style="float: right">
                    <a href="<%= contexto%>/logout"><img style="height: 1em" src="<%= contexto%>/resources/img/logout.svg"></a>
                </div>
                <form id="formObservacao">
                    <span>Submeter Observação</span>
                    <br>
                    <br>
                    <br>
                    <input type="text" style="width: 90%; font-size: 1.5em; font-weight: bold;" class="texto" id="dataHoraObs" disabled>
                    <br>
                    <br>
                    <table class="center-justify texto" style="width: 95%">
                        <tr>
                            <td style="text-align-last:right">Altura das Ondas</td>
                            <td><input type="text" class="texto" id="altOndas" style="width: 90%"></td>
                            <td>m</td>
                        </tr>
                        <tr>
                            <td style="text-align-last:right">Temperatura da Água</td>
                            <td><input type="text" class="texto" id="tempAgua" style="width: 90%"></td>
                            <td>ºC</td>
                        </tr>
                        <tr>
                            <td style="text-align-last:right">Bandeira</td>
                            <td><input type="text" class="texto" id="bandeira" style="width: 90%"></td>
                        </tr>
                    </table>
                    <br>
                </form>
                <button id="botaoSubmit" class="botao-buscar" onclick="submit()">Submeter</button>
            </div>
        </div>
    </body>
</html>
