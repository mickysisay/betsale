package com.webapps.betsale.controllers;

import com.webapps.betsale.models.User;
import com.webapps.betsale.models.data.UserRepository;
import com.webapps.betsale.models.dto.LoginFormDTO;
import com.webapps.betsale.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;
    private static final String userSessionKey = "BetSaleUser";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user==null) {
            return null;
        }

        return user.get();
    }
     User getLoggedUser(HttpServletRequest request){
        Integer id = (Integer) request.getSession().getAttribute("reverseRecipeUser");
        Optional option =   userRepository.findById(id);
        User theUser = (User) option.get();
        return theUser;
    }
    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping("")
    public static String homePage(){
        return "index";
   }
   @GetMapping("/login")
    public static String loginPageDisplay(HttpServletRequest request){
       String referrer = request.getHeader("referer");
        return "login";
   }
    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }

        String password = loginFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "login";
        }

        setUserInSession(request.getSession(), theUser);
        //changed here
        model.addAttribute("user",theUser);
        return "redirect:";
    }
   @GetMapping("/signup")
    public static String signupPageDisplay(Model model){
       model.addAttribute(new RegisterFormDTO());
        return "signup";
   }

    @PostMapping("/signup")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "signup";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            return "signup";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "signup";
        }

        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:";
    }
}
