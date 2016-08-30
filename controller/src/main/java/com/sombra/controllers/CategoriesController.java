package com.sombra.controllers;

/**
 * Created by PasichnykMV on 24.08.2016.
 */

import com.sombra.model.Category;
import com.sombra.services.CategoryService;
import com.sombra.services.JdbcCategoryService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by PasichnykMV on 24.08.2016.
 */
@WebServlet(urlPatterns = "/categories")
public class CategoriesController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CategoriesController.class.getName());

    private CategoryService categoryService;

    public void init() throws ServletException {
        this.categoryService = new JdbcCategoryService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<String> action = Optional.ofNullable(request.getParameter("action"));
        if (action.isPresent() && action.get().equals("delete") ){
            categoryService.delete(Integer.parseInt(request.getParameter("id")));
            LOGGER.info("Category deleted");
        } else {
            Category category = new Category();
            category.setTitle(request.getParameter("title"));
            Optional<Integer> parentId = Optional.ofNullable(Integer.parseInt(request.getParameter("parentId")));
            if (parentId.isPresent() && parentId.get() != 0){
                category.setParentId(parentId.get());
            }
            categoryService.save(category);
            LOGGER.info("Category saved");
        }
        LOGGER.info("Finish to doPost");
        response.sendRedirect(request.getContextPath() + "/admin?page=categories");
    }

}
