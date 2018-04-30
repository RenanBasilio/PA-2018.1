package edu.ufrj.renanbasilio.tempoclimanet.mvc.pagehandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IFHandler {
    public String process(HttpServletRequest request,
                            HttpServletResponse response);
}
