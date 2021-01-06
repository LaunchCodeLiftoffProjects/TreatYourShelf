package org.liftoff.BookApp;

import org.liftoff.BookApp.controllers.AuthenticationController;
import org.liftoff.BookApp.data.UserRepository;
import org.liftoff.BookApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;


    private static final List<String> whitelist = Arrays.asList("/login", "/register", "/logout", "/css", "/","/search");

    private static boolean isWhitelisted(String path) {
        // Path: localhost:8080/login
        // trim out localhost:8080 -> compare new path to my list of white listed paths
        // Path: /login
        for (String pathRoot : whitelist) {
            if (path.equals(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        if (isWhitelisted(request.getRequestURI())) {
            return true;
        }

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (user != null) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }

}