package com.sombra.controllers;

import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.model.Lot;
import com.sombra.services.JdbcLotService;
import com.sombra.services.JdbcUserService;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by PasichnykMV on 18.08.2016.
 */
@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CartController.class.getName());

    private JdbcLotService lotService;
    private JdbcUserService userService;

    public void init() throws ServletException {
        this.lotService = new JdbcLotService();
        this.userService = new JdbcUserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Start doGet method");
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        Optional<String> action = Optional.ofNullable(request.getParameter("action"));
        if (action.isPresent() && action.get().equals("delete")) {
            Optional<Integer> lotId = Optional.ofNullable(Integer.parseInt(request.getParameter("lotId")));
            if (lotId.isPresent() && lotId.get() != 0) {
                lotService.deleteLotFromCart(userId, lotId.get());
            }
            response.sendRedirect(request.getHeader("referer"));
        } else {
            Integer lotsCount = userService.getLotsIncartCount(userId);
            LOGGER.info("User with id="+userId+" has " + lotsCount + " lots in cart");

            response.setContentType("text/plain");
            response.getWriter().write((lotsCount).toString());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Start doPost method");
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        Optional<Integer> lotId = Optional.ofNullable(Integer.parseInt(request.getParameter("lotId")));
        if (lotId.isPresent() && lotId.get() != 0) {
            userService.addLotToUsersCart(userId, lotId.get());
        }
    }

}