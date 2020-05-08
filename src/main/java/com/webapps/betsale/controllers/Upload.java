package com.webapps.betsale.controllers;
import com.webapps.betsale.models.Home;
import com.webapps.betsale.models.HomeImage;
import com.webapps.betsale.models.data.HomeImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("upload")
public class Upload {
    @Autowired
    HomeImageRepository homeImageRepository;
    @PostMapping("image")
    public String uploadImage(MultipartFile img){
        try (InputStream input = img.getInputStream()) {
            try {
                ImageIO.read(input).toString();
            } catch (Exception e) {
                // It's not an image.
                return "not an image file";
            }
        }catch(IOException c){

        }
        HomeImage homeImage = new HomeImage();
        try{
            homeImage.saveImage(img);
        }catch(Exception e){
            return "Couldn't upload image";
        }
        return "succesfully uploaded";
    }
}
