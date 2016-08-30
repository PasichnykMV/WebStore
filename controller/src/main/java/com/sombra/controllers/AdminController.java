package com.sombra.controllers;

import com.sombra.model.Category;
import com.sombra.model.Lot;
import com.sombra.services.*;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * Created by PasichnykMV on 18.08.2016.
 */
@WebServlet(urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());

    private JdbcCategoryService categoryService;
    private CityService cityService;
    private JdbcLotService lotService;
    private JdbcUserService userService;

    public void init() throws ServletException {
        this.categoryService = new JdbcCategoryService();
        this.lotService = new JdbcLotService();
        this.userService = new JdbcUserService();
        this.cityService = new JdbcCityService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("users", userService.getAll());
        request.setAttribute("categories", categoryService.getAllWithSubcategories());
        request.setAttribute("cities", cityService.getAll());
        LOGGER.info("Entering to AdminController with uri" + request.getQueryString());
        request.getRequestDispatcher("/views/adminPage.jsp").forward(request, response);
    }

}