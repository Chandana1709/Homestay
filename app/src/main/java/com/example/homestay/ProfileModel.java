package com.example.homestay;

import com.denzcoskun.imageslider.models.SlideModel;

import java.util.List;

public class ProfileModel {
    private String objectId;
    private String userName;
    private String userEmail;

    public ProfileModel(String userName,String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
