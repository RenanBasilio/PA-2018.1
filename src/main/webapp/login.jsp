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
        <script type="text/javascript" src="<%= contexto%>/scripts/jquery-3.3.1/dist/jquery.min.js"></script>
    </head>
    <body background="<%= contexto%>/resources/img/praia.jpg">
        <div id="header">
            <div id="nomeSite">Portal do Observador</div>
            <div id="DELPOLI">EEL418 / DEL / POLI / UFRJ</div>
        </div>
        <br><br>
        <div id="mainDiv" class="flex-container">
            <div id="divLogin" class="shadow-border flex-child center-justify">
                <form id="loginform" method="POST" action="j_security_check">
                    <br>
                    <br>
                    <br>
                    <span>Login</span>
                    <br>
                    <br>
                    <table class="center-justify texto">
                        <tr>
                            <td style="text-align-last: right"><span class="texto">Usuário</span></td>
                            <td><input type="text" class="texto" name="j_username"></td>
                        </tr>
                        <tr>
                            <td style="text-align-last: right"><span class="texto">Senha</span></td>
                            <td><input type="password" class="texto" name="j_password"></td>
                        </tr>
                    </table>
                    <br>
                    <button id="botaoEntrar" class="botao-buscar" 
                        type="submit">Entrar</button>
                </form>
            </div>
        </div>
    </body>
</html>
