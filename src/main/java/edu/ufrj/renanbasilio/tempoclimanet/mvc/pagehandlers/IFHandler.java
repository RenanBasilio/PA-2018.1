package edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface que declara a forma de um tratador de páginas.
 */
public interface IFHandler {
    /**
     * Processa um request a este tratador.
     * @param request O request recebido pelo Servlet.
     * @param response A resposta do Servlet.
     * @return A resposta do tratador.
     */
    public String process(HttpServletRequest request,
                            HttpServletResponse response);
}
