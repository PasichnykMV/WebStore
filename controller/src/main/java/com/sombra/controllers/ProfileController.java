package com.sombra.controllers;

import com.sombra.model.User;
import com.sombra.services.JdbcLotService;
import com.sombra.services.JdbcUserService;
import com.sombra.services.LotService;
import com.sombra.services.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by PasichnykMV on 19.08.2016.
 */
@WebServlet(urlPatterns = "/profile")
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ProfileController.class.getName());

    private UserService userService;
    private LotService lotService;

    public void init() throws ServletException {
        this.userService = new JdbcUserService();
        this.lotService = new JdbcLotService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering to ProfileController");
        Integer id = (Integer) request.getSession().getAttribute("userId");

        request.setAttribute("lots", lotService.getAllUsersOrders(id));
        request.setAttribute("user", userService.getById(id));
        LOGGER.info("UserID = " + id.toString());
        request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering to doPost in ProfileController");
        Optional<String> enable = Optional.ofNullable(request.getParameter("enable"));
            if (enable.isPresent() && enable.get().equals("change") ){
                    Integer userId = Integer.parseInt(request.getParameter("user_id"));
                    User user = userService.getById(userId);
                    if (user.is_enable()){
                        user.setEnable(false);
                        userService.update(user);
                        LOGGER.info("User "+user.getName()+" banned");
                    } else {
                        user.setEnable(true);
                        userService.update(user);
                        LOGGER.info("User "+user.getName()+" unbanned");
                    }

                response.sendRedirect(request.getContextPath() + "/admin?page=users");
            }

        Optional<String> edit = Optional.ofNullable(request.getParameter("edit"));
        if (edit.isPresent() && edit.get().equals("true") ){
            updateProfile(request);
            LOGGER.info("Profile updated");
        }

        LOGGER.info("doPost finished");
    }

    private void updateProfile(HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        User user = userService.getById(userId);
        String password = request.getParameter("password");
        if(password.equals(user.getPassword())){
            user.setName(request.getParameter("name"));
            user.setLastName(request.getParameter("last_name"));
            user.setEmail(request.getParameter("email"));

            Optional<String> newPassword = Optional.ofNullable(request.getParameter("newpassword"));
            if (newPassword.isPresent() && !newPassword.get().equals("")){
                user.setPassword(newPassword.get());
            }
            userService.update(user);
        }
    }

}