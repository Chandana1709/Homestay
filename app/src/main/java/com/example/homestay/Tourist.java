package com.example.homestay;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.google.gson.annotations.SerializedName;


@ParseClassName("Tourist")
public class Tourist extends ParseObject {
    @SerializedName("email")
    private String email;
    private String password;

    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
    }

    public String getPassword() {
        return getString("password");
    }

    public void setPassword(String password) {
        put("password", password);
    }
}
