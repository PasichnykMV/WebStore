package com.sombra.controllers;

import com.sombra.model.User;
import com.sombra.model.UserRole;
import com.sombra.services.JdbcUserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * Created by PasichnykMV on 19.08.2016.
 */
@WebServlet(urlPatterns = {"/registration"})
public class RegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

    private JdbcUserService userService;

    public void init() throws ServletException {
        this.userService = new JdbcUserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = parseUser(request);
        userService.save(user);

        EmailService.getInstance().sendMessage("sign up","Successful registration!", user);
        LOGGER.info("User "+user.getName()+" successfully registered, message send to user.email");

        request.setAttribute("msg", "Hello "+user.getName()+
                "!\nYou have successfully signed up at our shop. Please Sign In:");

        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    private User parseUser(HttpServletRequest request) throws IOException, ServletException {
        LOGGER.info("Start parseUser method");
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setLastName(request.getParameter("last_name"));
        user.setCreationDate(new Date());
        user.setEnable(true);

        UserRole role = new UserRole();
        role.setId(1);
        user.setRole(role);

        LOGGER.info("User "+user.getName()+" successfully parsed");
        return user;
    }

}
