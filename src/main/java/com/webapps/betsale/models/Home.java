package com.webapps.betsale.models;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Home extends AbstractEntity {
    @NotBlank
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
    @NotBlank
    private String addressName;
    @NotBlank
    @Positive
    @NotBlank
    private double price;
    @Range(min=1900,max=2040)
    private double yearBuilt;
    @Range(min=0,max=5000)
    private double area;
    @Range(min=0,max=30)
    @NotBlank
    private double bedrooms;
    @Range(min=0,max=30)
    @NotBlank
    private double bathrooms;
    @ManyToOne
    private User user;
    @OneToMany
    private List<HomeImage> homeImages;
    private double mickyScore;
    public Home(){

    }
    public void saveImages(List<HomeImage> allImages){
        homeImages = allImages;
    }
    public void addImage(HomeImage homeImage){
        homeImages.add(homeImage);
    }
    public ArrayList<String> getImages(){
        ArrayList<String> allPaths = new ArrayList<>();
       for(int i=0;i<homeImages.size();i++){
           allPaths.add(homeImages.get(i).getImagePath());
       }
       return allPaths;
    }
}
