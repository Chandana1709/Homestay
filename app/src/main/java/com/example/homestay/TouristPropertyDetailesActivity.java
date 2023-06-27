package com.example.homestay;

//import static com.example.homestay.AddPropertyActivity.SELECT_IMAGE_CODE;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseUser;
import com.parse.SaveCallback;


import java.util.ArrayList;


public class  TouristPropertyDetailesActivity extends AppCompatActivity {
    // private static final int SELECT_IMAGE_CODE = ;
    // creating variables for our edit text
    private TextView propertyNameEdt, availableroomsEdt, propertyAddressEdt,propertyAmountEdt,cooking,parking;

    // creating variable for button
    private Button favoritePropertyBtn,bookPropertyBtn;



    // creating a strings for storing our values from edittext fields.
    private String propertyName;

    private String propertyAddress;
    private String propertyAmount;
   private String availablerooms,maxrooms;

    // creating a strings for storing our values from edittext fields.
    private String originalPropertyName;
    private ImageView itemImage;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri imagePath;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tourist_property_detailes);

        // initializing our edittext and buttons
        bookPropertyBtn = findViewById(R.id.idbook);
        favoritePropertyBtn = findViewById(R.id.idfavorite);
        propertyNameEdt = findViewById(R.id.idusername);
        availableroomsEdt = findViewById(R.id.idavailablerooms);
        propertyAddressEdt = findViewById(R.id.idaddress);
        propertyAmountEdt = findViewById(R.id.idcost);
        cooking = findViewById(R.id.idselfcooking);
        parking = findViewById(R.id.idparking);







        originalPropertyName = getIntent().getStringExtra("propertyName");
        getDataFromServer();

        bookPropertyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristPropertyDetailesActivity.this, BookingForm.class);
                intent.putExtra("username", ParseUser.getCurrentUser().getUsername());
                String amount = propertyAmountEdt.getText().toString();
                intent.putExtra("amount", amount);
                String propname = propertyNameEdt.getText().toString();
                intent.putExtra("propname",propname);
                intent.putExtra("availablerooms",availablerooms);
                intent.putExtra("maxrooms",maxrooms);
                startActivity(intent);
            }
        });




        favoritePropertyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(TouristPropertyDetailesActivity.this, BookingForm.class);
//                intent.putExtra("username", ParseUser.getCurrentUser().getEmail());
//                String propertyName = propertyNameEdt.getText().toString();
//                startActivity(intent);

                add();
            }
        });

}
//
//
//

    public void add(){
       // private TextView propertyNameEdt, propertyDurationEdt, propertyAddressEdt,propertyAmountEdt,cooking,parking;
        ParseObject bookings = new ParseObject("intrested");

        ParseUser currentUser = ParseUser.getCurrentUser();

        String amt = propertyAmountEdt.getText().toString();
        String propname = propertyNameEdt.getText().toString();
        // Set the name and age properties

        bookings.put("name", currentUser.getUsername());
        bookings.put("email", currentUser.getEmail());
        bookings.put("propname", propname);
        bookings.put("amount", amt);


        // Save the object to the database
        bookings.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Object saved successfully
                    Toast.makeText(TouristPropertyDetailesActivity.this, "added to db", Toast.LENGTH_SHORT).show();
                } else {
                    // Error occurred while saving object
                    Toast.makeText(TouristPropertyDetailesActivity.this, "error in adding to db", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void getDataFromServer() {
        String originalPropertyName = getIntent().getStringExtra("propertyName");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("properties");
        query.whereEqualTo("propertyName", originalPropertyName);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (object != null) {
                        propertyNameEdt = findViewById(R.id.idusername);
                        availableroomsEdt = findViewById(R.id.idavailablerooms);
                        propertyAddressEdt = findViewById(R.id.idaddress);
                        propertyAmountEdt = findViewById(R.id.idcost);
                        cooking = findViewById(R.id.idselfcooking);
                        parking = findViewById(R.id.idparking);



                        propertyNameEdt.setText(object.getString("propertyName"));
//                        propertyDurationEdt.setText(object.getString("propertyDuration"));
                        propertyAddressEdt.setText(object.getString("propertyAddress"));
                        propertyAmountEdt.setText(object.getString("propertyAmount"));
                        cooking.setText(object.getString("propertyCooking"));
                        parking.setText(object.getString("propertyParking"));
                        availableroomsEdt.setText(object.getString("availablerooms"));
                        availablerooms = object.getString("availablerooms");
                        maxrooms = object.getString("maxrooms");
                    } else {
                        Log.e(TAG, "Error fetching data: no results found for query");
                    }
                } else {
                    Log.e(TAG, "Error fetching data: " + e.getMessage());
                }
            }
        });
    }
    }
