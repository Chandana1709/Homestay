package com.example.homestay;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.homestay.AddPropertyActivity.SELECT_IMAGE_CODE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE_CODE = 1;
    private EditText username, phone;

    private TextView emailid;
    // creating variable for button
    private Button submitProfileBtn, btnImage;
    private String usernameEdt,phoneEdt;
    // creating a strings for storing our values from edittext fields.
    private String originalUserName;
    private ImageView itemImage;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri imagePath;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        submitProfileBtn = findViewById(R.id.idBtnUpdate);
        username = findViewById(R.id.idusername);
        phone = findViewById(R.id.idphone);
        itemImage = findViewById(R.id.idimageview);
        btnImage = findViewById(R.id.idImageBtn);
        getDataFromServer();

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }

        });
        submitProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameEdt = username.getText().toString();
                phoneEdt = phone.getText().toString();
                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(usernameEdt)) {
                    username.setError("Please enter Profile Name");
                }  else if (TextUtils.isEmpty(phoneEdt)) {
                    phone.setError("Please enter phone number");
                } else {
                    // calling method to update data.
                    updateData(usernameEdt,phoneEdt);
                }
            }
        });



    }

    private void getDataFromServer() {

        String originalEmail = ParseUser.getCurrentUser().getEmail();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("email", originalEmail);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (object != null) {
                        username.setText(object.getString("username"));
                        phone.setText(object.getString("phone"));

                    } else {
                        Log.e(TAG, "Error fetching data: no results found for query");
                    }
                } else {
                    Log.e(TAG, "Error fetching data: " + e.getMessage());
                }
            }
        });
    }


    private void updateData(String username, String  phone) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByteArray = stream.toByteArray();

        // create a ParseFile with image data
        ParseFile imageFile = new ParseFile("image.png", imageByteArray);

        // Configure Query with our query.
//        String originalemail = ParseUser.getCurrentUser().getEmail();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
//
//        // adding a condition where our course name must be equal to the original course name
//        query.whereEqualTo("email", originalemail);

        // in below method we are getting the unique id
        // of the course which we have to make update.
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            //            imageFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseObject object, ParseException e) {
                // inside done method we check
                // if the error is null or not.
                if (e == null) {


                    // if the error is null then we are getting
                    // our object id in below line.
                    String objectID = object.getObjectId().toString();

                    // after getting our object id we will
                    // move towards updating our course.
                    // calling below method to update our course.
                    query.getInBackground(objectID, new GetCallback<ParseObject>() {

                        @Override
                        public void done(ParseObject object, ParseException e) {
                            // in this method we are getting
                            // object which we have to update.
                            if (e == null) {

                                // in below line we are adding new data
                                // to the object which we get from its id.
                                // on below line we are adding our data
                                // with their key value in our object.
                                object.put("username", username);
                                object.put("phone", phone);
                                object.put("image", imageFile);
                                // after adding new data then we are
                                // calling a method to save this data
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        // inside on done method we are checking
                                        // if the error is null or not.
                                        if (e == null) {
                                            // if the error is null our data has been updated.
                                            // we are displaying a toast message and redirecting
                                            // our user to home activity where we are displaying course list.
                                            Toast.makeText(ProfileActivity.this, "Profile Updated..", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(ProfileActivity.this, ProfileActivity.class);
                                            startActivity(i);
                                        } else {
                                            // below line is for error handling.
                                            Toast.makeText(ProfileActivity.this, "Fail to update data " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                // on below line we are displaying a message
                                // if we don't get the object from its id.
                                Toast.makeText(ProfileActivity.this, "Fail to update profile " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // this is error handling if we don't get the id for our object
                    Toast.makeText(ProfileActivity.this, "Fail to get object ID..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void chooseImage() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "title"), SELECT_IMAGE_CODE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                Uri uri = data.getData();
                itemImage.setImageURI(uri);
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                itemImage.setImageBitmap(imageToStore);
                Toast.makeText(this, "image to bitmap", Toast.LENGTH_SHORT).show();
            }
//            if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK && data == null && data.getData() != null) {
//
//
//            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}