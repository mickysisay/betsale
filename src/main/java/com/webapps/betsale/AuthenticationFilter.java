package com.webapps.betsale;

import com.webapps.betsale.models.User;
import com.webapps.betsale.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    private static final List<String> blackListed = Arrays.asList("/addhome","/savehome");
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        // Don't require sign-in for whitelisted pages
        if (isBlackListed(request.getRequestURI())) {
            // returning true indicates that the request may proceed
            // The user is NOT logged in
            response.sendRedirect("/login");
            return false;
        }

     //   HttpSession session = request.getSession();
      //  User user = homeController.getUserFromSession(session);

        // The user is logged in
        //if (user != null) {
         //   return true;
        //}

        return true;
    }
    private static boolean isBlackListed(String path) {
        for (String pathRoot : blackListed) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

}