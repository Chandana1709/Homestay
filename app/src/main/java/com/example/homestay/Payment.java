//razorpay api key : rzp_test_tcEq8NFueN65o6
package com.example.homestay;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Payment extends AppCompatActivity implements PaymentResultListener{
    private TextView username,useremail,totalamountfield,propName,noofrooms;
    private Button paybtn;
    String arooms,mrooms,brooms;
    int current_arooms;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paybtn = findViewById(R.id.paybtn);
        totalamountfield = findViewById(R.id.totalamount);
        username = findViewById(R.id.nameTextView); // initialize nameTextView
        useremail = findViewById(R.id.emailTextView);
        propName = findViewById(R.id.propNameTextView);
        noofrooms = findViewById(R.id.noofrooms);
        int price;
        getDataFromServer();
        //String apiKey = "rzp_test_tcEq8NFueN65o6";
       // Razorpay razorpay = new Razorpay(this, apiKey);
        
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();

            }
        });



    }




    private void getDataFromServer() {
       // String originalamt = getIntent().getStringExtra("propertyAmount");
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("tourist");
        query.whereEqualTo("email", currentUser.getEmail());
        // Retrieve the intent
        Intent intent = getIntent();

// Retrieve the extras from the intent
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String name = extras.getString("username");
            String email = extras.getString("email");
            String cost = extras.getString("amount");
            String propNameIntent = extras.getString("propname");
            String rooms = extras.getString("noofrooms");
            arooms = extras.getString("availablerooms");
            mrooms = extras.getString("maxrooms");
            brooms = extras.getString("noofrooms");
            username.setText(name);
            useremail.setText(email);
            totalamountfield.setText(cost);
            propName.setText(propNameIntent);
            noofrooms.setText(rooms);
        }
//
//        query.getFirstInBackground(new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (e == null) {
//                    if (object != null) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //name.setText(object.getString("username"));
//                                //email.setText(object.getString("email"));
//
//                                if (extras != null) {
//                                    String name = extras.getString("username");
//                                    String email = extras.getString("email");
//                                    String cost = extras.getString("amount");
//                                    String propNameIntent = extras.getString("propname");
//                                    String rooms = extras.getString("noofrooms");
//                                    arooms = extras.getString("availablerooms");
//                                    mrooms = extras.getString("maxrooms");
//                                    brooms = extras.getString("noofrooms");
//                                    username.setText(name);
//                                    useremail.setText(email);
//                                    totalamountfield.setText(cost);
//                                    propName.setText(propNameIntent);
//                                    noofrooms.setText(rooms);
//                                }
//                            }
//                        });
//                    } else {
//                        Log.e(TAG, "Error fetching data: no results found for query");
//                    }
//                } else {
//                    Log.e(TAG, "Error fetching data: " + e.getMessage());
//                }
//            }
//        });
    }

    public void startPayment(){
        Checkout checkout = new Checkout();

        checkout.setImage(R.mipmap.ic_launcher);

        final Activity activity = this;

        try{
            String name = null,email = null,cost = null;
            int price = 0;
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();

            if (extras != null) {
                name = extras.getString("username");
                email = extras.getString("email");
                cost = extras.getString("amount");
                price = Integer.parseInt(cost);
                price = price*100;

            }

            JSONObject options = new JSONObject();
            options.put("name",name);
            options.put("description","ABCD paid to book room");
            options.put("send_sms_hash",true);
            options.put("allow_rotation",false);

            options.put("currency","INR");
            
            options.put("amount",price);
            
            JSONObject preFill = new JSONObject();
            preFill.put("email",email);
            preFill.put("contact","phone");
            
            options.put("prefill",preFill);
            checkout.open(activity,options);
        } catch(Exception e){
            Toast.makeText(activity, "Error in payment"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Successful"+s, Toast.LENGTH_SHORT).show();
        //status col update
        current_arooms=Integer.parseInt(arooms) - Integer.parseInt(brooms);

                        if(current_arooms == 0){
                            status = 1;
                        }else if(current_arooms > 0){
                            status = 0;
                        }
       String  local_rooms = String.valueOf(current_arooms);
        update(propName.getText().toString(),status,local_rooms);
        //add to booking table
         //prop name , user email , amount, trans id
        add();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Failed"+s, Toast.LENGTH_SHORT).show();
    }


    private void update(String originalPropertyName, int status, String local_current_arooms) {
// convert image into byte array
        ParseQuery<ParseObject> query= ParseQuery.getQuery("properties");

        // adding a condition where our course name must be equal to the original course name
        query.whereEqualTo("propertyName", originalPropertyName);

//ParseObject object = query.getFirst();


        query.getFirstInBackground(new GetCallback<ParseObject>(){
            @Override
            public void done(ParseObject object, ParseException e) {
                // inside done method we check
                // if the error is null or not.
                if (e == null) {

                    // if the error is null then we are getting
                    // our object id in below line.

                    String objectID=object.getObjectId().toString();
//                            Log.i("UpdateProperty", "objectId: " + objectID);





                    // after getting our object id we will
                    // move towards updating our course.
                    // calling below method to update our course.
                    query.getInBackground(objectID, new GetCallback<ParseObject>() {

                        @Override
                        public void done(ParseObject object, ParseException e) {
                            // in this method we are getting the
                            // object which we have to update.
                            if (e == null ){

                                // in below line we are adding new data
                                // to the object which we get from its id.
                                // on below line we are adding our data
                                // with their key value in our object.
//                                object.put("propertyName", propertyName);
                                object.put("status",status);
                                object.put("availablerooms",local_current_arooms);

//                                        object.setObjectId(objectID);
                                // after adding new data then we are
                                // calling a method to save this data
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        // inside on done method we are checking
                                        // if the error is null or not.
                                        if (e == null) {
                                            Toast.makeText(Payment.this, "Updated", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Payment.this, "failed to update1", Toast.LENGTH_SHORT).show();
                                            }
                                    }
                                });
                            } else {
                                Toast.makeText(Payment.this, "failed to update2", Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                } else {
                    Toast.makeText(Payment.this, "failed to get object id", Toast.LENGTH_SHORT).show();
                     }
            }

        });
    }






    public void add(){
        ParseObject bookings = new ParseObject("bookings");
        String amt = totalamountfield.getText().toString();
        String name = username.getText().toString();
        String email = useremail.getText().toString();
        String propname = propName.getText().toString();
        // Set the name and age properties
        int status = 1;
        bookings.put("name", name);
        bookings.put("email", email);
        bookings.put("propname", propname);
        bookings.put("amount", amt);
        bookings.put("noofrooms",Integer.parseInt(brooms));
        bookings.put("status",status);

        // Save the object to the database
        bookings.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Object saved successfully
                    Toast.makeText(Payment.this, "added to db", Toast.LENGTH_SHORT).show();
                } else {
                    // Error occurred while saving object
                    Toast.makeText(Payment.this, "error in adding to db", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

