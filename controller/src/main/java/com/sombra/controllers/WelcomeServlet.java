package com.sombra.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sombra.model.Category;
import com.sombra.model.Lot;
import com.sombra.services.JdbcCategoryService;
import com.sombra.services.JdbcLotService;
import com.sombra.services.JdbcUserService;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

@WebServlet(urlPatterns = "")
public class WelcomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(WelcomeServlet.class.getName());

    private JdbcCategoryService categoryService;
    private JdbcLotService lotService;
    private JdbcUserService userService;

    public void init() throws ServletException {
        this.categoryService = new JdbcCategoryService();
        this.lotService = new JdbcLotService();
        this.userService = new JdbcUserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering to WelcomeServlet controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = authentication.getName();
            LOGGER.info("user.email =  "+currentUserEmail);
            Integer userId =  userService.getIdByEmail(currentUserEmail);
            request.getSession().setAttribute("userId", userId);
            Integer lotsCount = userService.getLotsIncartCount(userId);
            request.setAttribute("lotsCount", lotsCount);
        }

        request.setAttribute("categories", categoryService.getAll());
        LOGGER.info("Pass " + categoryService.getAll().size() + " cat`s   to /index.jsp");

        Optional<String> category = Optional.ofNullable(request.getParameter("category"));
        List<Lot> lots;
        if (category.isPresent()) {
            if (category.get() != null && !category.get().equals("")) {
                Integer categoryId = Integer.valueOf(category.get());
                lots = lotService.getAllForCategory(
                        completeCategoriesIdSet(categoryId), categoryId);
                request.setAttribute("lots", lots);
                LOGGER.info("Pass " + lots.size() + " lots   to /index.jsp");
            }
        } else {
            lots = lotService.getAll();
            request.setAttribute("lots", lots);
            LOGGER.info("Pass " + lots.size() + " lots   to /index.jsp");
        }

        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    private Set<Integer> completeCategoriesIdSet(Integer categoryId) {

        Set<Integer> set = new HashSet<>();
        set.add(categoryId);
        Category category = categoryService.getById(categoryId);
        for (Category subcat : category.getSubcategories()) {
            set.add(subcat.getId());
            if (completeCategoriesIdSet(subcat.getId()) != null) {
                set.addAll(completeCategoriesIdSet(subcat.getId()));
            }
        }
        return set;
    }

}
