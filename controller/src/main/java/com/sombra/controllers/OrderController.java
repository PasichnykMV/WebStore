package com.sombra.controllers;

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
import java.util.List;
import java.util.Optional;

/**
 * Created by PasichnykMV on 18.08.2016.
 */
@WebServlet(urlPatterns = {"/order"})
public class OrderController extends HttpServlet {

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
        Integer lotsCount = userService.getLotsIncartCount(userId);
        LOGGER.info("User with id="+userId+" has " + lotsCount + " lots in cart");
        request.setAttribute("lotsCount", lotsCount);

        List<Lot> lots = lotService.getAllLotsInUserCart(userId);
        request.setAttribute("lots", lots);

        request.getRequestDispatcher("/views/cart.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}