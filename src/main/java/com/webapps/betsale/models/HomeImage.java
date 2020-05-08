package com.webapps.betsale.models;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HomeImage extends AbstractEntity {

    private String imagePath;
    @ManyToOne
    @JoinColumn
    private Home home;
    public HomeImage(){

    }
    public void saveImage(MultipartFile image) throws Exception {
     Path currentPath = Paths.get(".");
     Path absolutePath = currentPath.toAbsolutePath();
     imagePath = "static/photos/"+home.getId()+"/img"+this.getId();
     String finalPath = absolutePath+"src/main/resources/static/photos/";
     File directory = new File(finalPath+Integer.toString(home.getId()));
       if(!directory.exists()){
           directory.mkdir();
       }

     byte[] bytes = image.getBytes();
      Path path = Paths.get(finalPath+Integer.toString(home.getId())+"/img"+this.getId());
    }
    public String getImagePath(){
        return imagePath;
    }

}
