package com.example.homestay;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.List;

public class TouristBookedPropertyActivity extends AppCompatActivity {
    private EditText username,useremail,totalamountfield,propName;
    private Button checkoutbtn,reviewbtn;
    String original_username;
    String propname;
    int noofrooms;
    String availablerooms_in_properties;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_booked_property);
        reviewbtn = findViewById(R.id.reviewbtn);
        checkoutbtn = findViewById(R.id.checkoutbtn);
        totalamountfield = findViewById(R.id.totalamount);
        username = findViewById(R.id.nameTextView); // initialize nameTextView
        useremail = findViewById(R.id.emailTextView);
        propName = findViewById(R.id.propNameTextView);

        checkoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();

            }
        });


        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristBookedPropertyActivity.this, PropertyReviewActivity.class);
                String propNameIntent = propName.getText().toString();
                intent.putExtra("propname", propNameIntent);
                startActivity(intent);
            }
        });


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            original_username = currentUser.getUsername();
            // Do something with the username variable, such as store it in a field or display it in the UI
        }
        // Create Parse query to retrieve data
        ParseQuery<ParseObject> query = ParseQuery.getQuery("bookings");
        query.whereEqualTo("name", original_username);
        query.whereEqualTo("status", 1);

        // Execute query in background
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // Query successful, retrieve data
                    for (ParseObject object : objects) {
                        String name = object.getString("name");
                        String email = object.getString("email");
                        propname = object.getString("propname");
                        noofrooms = object.getInt("noofrooms");
                        String amt = object.getString("amount");
                        // Display data in UI
                        // For example, you can set the text of TextViews and EditTexts
                        //username,useremail,totalamountfield,propName;
                        //TextView nameTextView = findViewById(R.id.nameTextView);
                        //EditText ageEditText = findViewById(R.id.ageEditText);
                        username.setText(name);
                        useremail.setText(email);
                        propName.setText(propname);
                        //totalamountfield.setText(String.valueOf(amt));
                        totalamountfield.setText(amt);

                        // Create Parse query to retrieve data
                        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("properties");
                        query1.whereEqualTo("propertyName", propname);

                        // Execute query in background
                        query1.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null) {
                                    // Query successful, retrieve data
                                    for (ParseObject object : objects) {
                                        // String name = object.getString("name");
                                        availablerooms_in_properties = object.getString("availablerooms");
                                    }
                                } else {
                                    // Query failed, handle error
                                    e.printStackTrace();
                                }
                            }
                        });




                    }
                } else {
                    // Query failed, handle error
                    e.printStackTrace();
                }
            }
        });


        //getting available rooms from properties table





    }

    public void checkout() {

        // Create a new ParseQuery object for the table you want to query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("properties");

// Set the query conditions, for example:
        //query.whereEqualTo("status", 1);
        query.whereEqualTo("propertyName",propname);
// Execute the query asynchronously
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (!objects.isEmpty()) {
                        // Get the first object from the list (assuming there is only one)
                        ParseObject myObject = objects.get(0);

                        // Get the objectId of the object
                        String objectId = myObject.getObjectId();

                        int temp = Integer.parseInt(availablerooms_in_properties) + noofrooms;

                        // Update the column with the new value
                       String  local_rooms = String.valueOf(temp);
                        myObject.put("status", 0);
                        myObject.put("availablerooms",local_rooms);
                        // Save the changes asynchronously
                        myObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Success! The object has been updated
                                    Toast.makeText(TouristBookedPropertyActivity.this, "updated propes", Toast.LENGTH_SHORT).show();
                                    update();
                                     } else {
                                    // Oops, something went wrong
                                    Toast.makeText(TouristBookedPropertyActivity.this, "error in updating props", Toast.LENGTH_SHORT).show();
                                      }
                            }
                        });
                    } else {
                        // No objects were found that match the query
                        Toast.makeText(TouristBookedPropertyActivity.this, "no obj found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Oops, something went wrong
                    Toast.makeText(TouristBookedPropertyActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void update(){
        // Create a new ParseQuery object for the table you want to query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("bookings");

// Set the query conditions, for example:
        query.whereEqualTo("name", original_username);
        query.whereEqualTo("status", 1);

// Execute the query asynchronously
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (!objects.isEmpty()) {
                        // Get the first object from the list (assuming there is only one)
                        ParseObject myObject = objects.get(0);

                        // Get the objectId of the object
                        String objectId = myObject.getObjectId();

                        // Update the column with the new value
                        myObject.put("status", 0);

                        // Save the changes asynchronously
                        myObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Success! The object has been updated
                                    Toast.makeText(TouristBookedPropertyActivity.this, "updated bookings", Toast.LENGTH_SHORT).show();
                                     } else {
                                    // Oops, something went wrong
                                    Toast.makeText(TouristBookedPropertyActivity.this, "status not updated", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(MainActivity.this, "Error updating object: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // No objects were found that match the query
                        Toast.makeText(TouristBookedPropertyActivity.this, "no object found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Oops, something went wrong
                    Toast.makeText(TouristBookedPropertyActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}