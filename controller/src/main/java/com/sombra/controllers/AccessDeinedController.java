package com.sombra.controllers;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PasichnykMV on 19.08.2016.
 */
@WebServlet(urlPatterns = "/403")
public class AccessDeinedController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(first.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String msg = "You have not access to this page or were banned by Administrator (check your e-mail in this case)";

        LOGGER.info("Entering to AccessController, throw 403 code!");
        request.setAttribute("msg_403", msg);
        request.getRequestDispatcher("/views/403.jsp").forward(request, response);
    }
}
