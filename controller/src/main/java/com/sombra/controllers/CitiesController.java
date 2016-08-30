package com.sombra.controllers;

import com.sombra.model.City;
import com.sombra.services.CityService;
import com.sombra.services.JdbcCityService;
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
@WebServlet(urlPatterns = "/cities")
public class CitiesController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CitiesController.class.getName());

    private CityService cityService;

    public void init() throws ServletException {
        this.cityService = new JdbcCityService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering to doPost");
        Optional<String> action = Optional.ofNullable(request.getParameter("action"));
        if (action.isPresent() && action.get().equals("delete") ){
            Integer cityId = Integer.parseInt(request.getParameter("city_id"));
            cityService.delete(cityId);
            LOGGER.info("City deleted from list");
        } else {
            City city = new City();
            city.setName(request.getParameter("name"));
            cityService.save(city);
            LOGGER.info("City " + city.getName() + " saved!");
        }
        LOGGER.info("Finish to doPost");
        response.sendRedirect(request.getContextPath() + "/admin?page=cities");
    }
}
