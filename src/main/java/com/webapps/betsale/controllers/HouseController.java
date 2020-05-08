package com.webapps.betsale.controllers;

import com.webapps.betsale.models.Home;
import com.webapps.betsale.models.HomeImage;
import com.webapps.betsale.models.User;
import com.webapps.betsale.models.data.HomeImageRepository;
import com.webapps.betsale.models.data.HomeRepository;
import com.webapps.betsale.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/house")
public class HouseController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    HomeRepository homeRepository;
    @Autowired
    HomeImageRepository homeImageRepository;

    User getLoggedUser(HttpServletRequest request){
        Integer id = (Integer) request.getSession().getAttribute("reverseRecipeUser");
        Optional option =   userRepository.findById(id);
        User theUser = (User) option.get();
        return theUser;
    }

    @GetMapping("/addhome")
    public String getAddHomePage(Model model){
        model.addAttribute(new Home());
        return "add/home";
    }
    @PostMapping("/addhome")
    public String processAddHome(@ModelAttribute @Valid Home home, Errors errors, HttpServletRequest request, Model model, @ModelAttribute List<MultipartFile> homeImages){
       if(errors.hasErrors()){
           return "add/home";
       }
      User theUser = getLoggedUser(request);
       List<HomeImage> allHomeImages = new ArrayList<>();
       for(MultipartFile homeImage:homeImages){
           try (InputStream input = homeImage.getInputStream()) {
               try {
                   ImageIO.read(input).toString();
               } catch (Exception e) {
                   // It's not an image.
               }
           }catch(IOException c){

           }
           HomeImage uploadImage = new HomeImage();
           try{
               uploadImage.saveImage(homeImage);
           }catch(Exception e){

           }
           allHomeImages.add(uploadImage);
           homeImageRepository.save(uploadImage);
           //uploaded image
       }//end of loop
        home.saveImages(allHomeImages);
        homeRepository.save(home);
        theUser.Addhome(home);
        userRepository.save(theUser);
         return "redirect:";
    }
}
