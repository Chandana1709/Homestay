package com.example.homestay;



import java.util.List;

public class IntrestedModel {

    private String objectId;
    private String propertyName;
    private String propertyAmount;
    private String userEmail;
    private String userName;

    public IntrestedModel(String propertyName, String propertyAmount, String userEmail, String userName) {
        this.propertyName = propertyName;
        this.propertyAmount = propertyAmount;
        this.userEmail = userEmail;
        this.userName = userName;

    }


    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPropertyAmount() {
        return propertyAmount;
    }

    public void setPropertyAmount(String propertyAmount) {
        this.propertyAmount = propertyAmount;
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
