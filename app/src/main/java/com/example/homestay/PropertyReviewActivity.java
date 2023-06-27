package com.example.homestay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class PropertyReviewActivity extends AppCompatActivity {
    private TextView propname;
    private Button btnSend;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_review);
        propname = findViewById(R.id.propname);
        btnSend = findViewById(R.id.btnSend);
        ratingBar = (RatingBar) findViewById(R.id.rbStars);
        setdata();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();

            }
        });

    }

    private void setdata() {
        Intent intent = getIntent();

// Retrieve the extras from the intent
        Bundle extras = intent.getExtras();

        String name = extras.getString("propname");
        propname.setText(name);


    }

    private void update() {
// convert image into byte array
        ParseQuery<ParseObject> query= ParseQuery.getQuery("properties");

        // adding a condition where our course name must be equal to the original course name
        query.whereEqualTo("propertyName", propname.getText().toString());
        double rating = ratingBar.getRating();
//ParseObject object = query.getFirst();


        query.getFirstInBackground(new GetCallback<ParseObject>(){
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {


                    String objectID=object.getObjectId().toString();

                    query.getInBackground(objectID, new GetCallback<ParseObject>() {

                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null ){

                                //object.put("status",status);
                                //object.put("availablerooms",local_current_arooms);

                                object.increment("noofreviews", 1);
                                object.increment("totalrate", rating);

                                double col1 = object.getDouble("noofreviews");
                                double col2 = object.getDouble("totalrate");
                                double average = (col2 / col1);
                                object.put("averagerating", average);


                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        // inside on done method we are checking
                                        // if the error is null or not.
                                        if (e == null) {
                                            //Toast.makeText(Payment.this, "Updated", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(PropertyReviewActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                        } else {
                                           // Toast.makeText(Payment.this, "failed to update1", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(PropertyReviewActivity.this, "failed to update1", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                //Toast.makeText(Payment.this, "failed to update2", Toast.LENGTH_SHORT).show();
                                Toast.makeText(PropertyReviewActivity.this, "failed to update2", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                   // Toast.makeText(Payment.this, "failed to get object id", Toast.LENGTH_SHORT).show();
                    Toast.makeText(PropertyReviewActivity.this, "failed to get object id", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}