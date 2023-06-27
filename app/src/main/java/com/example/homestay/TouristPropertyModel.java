package com.example.homestay;

import com.denzcoskun.imageslider.models.SlideModel;

import java.util.List;

public class TouristPropertyModel {

    private String objectId;
    private String propertyName;

    private String propertyAddress;
    private String propertyAmount;
    private List<SlideModel> images;
    private String userEmail;
    private String propertyParking;
    private String propertyCooking;
    private double averagerating;


    public TouristPropertyModel(String propertyName, String propertyAddress, List<SlideModel> images, Double averagerating) {
        this.propertyName = propertyName;
        this.propertyAddress = propertyAddress;
//        this.userEmail = userEmail;
        this.images = images;
        this.averagerating = averagerating;

    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }




    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getPropertyAmount() {
        return propertyAmount;
    }

    public void setPropertyAmount(String propertyAmount) {
        this.propertyAmount = propertyAmount;
    }

    public String getPropertyParking() {
        return propertyParking;
    }

    public void setPropertyParking(String propertyParking) {
        this.propertyParking = propertyParking;
    }

    public String getPropertyCooking() {
        return propertyCooking;
    }

    public void setPropertyCooking(String propertyCooking) {
        this.propertyCooking = propertyCooking;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public float getRating() {
        return (float) averagerating;
    }

    public void setRating(double averagerating) {
        this.averagerating = averagerating;
    }

    public List<SlideModel> getImages() {
        return images;
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}

