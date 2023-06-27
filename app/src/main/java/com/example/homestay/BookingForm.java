//razorpay api key : rzp_test_tcEq8NFueN65o6


package com.example.homestay;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookingForm extends AppCompatActivity implements View.OnClickListener {
    private TextView checkinDateTextView;
    private TextView checkoutDateTextView;
    private TextView name,email,amount,totalamountfield,propName;
    private EditText noofrooms;
    private int checkinYear, checkinMonth, checkinDay, checkoutYear, checkoutMonth, checkoutDay;
    private Button nextbtn,amtbtn;
    private String availablerooms,maxrooms;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);
        amtbtn = findViewById(R.id.idAmountBtn);
        nextbtn = findViewById(R.id.next);
        checkinDateTextView = findViewById(R.id.checkinDateTextView);
        checkoutDateTextView = findViewById(R.id.checkoutDateTextView);

        totalamountfield = findViewById(R.id.totalamount);
        name = findViewById(R.id.nameTextView); // initialize nameTextView
        email = findViewById(R.id.emailTextView);
        propName = findViewById(R.id.propNameTextView); // initialize emailTextView
        amount = findViewById(R.id.amount);
        propName = findViewById(R.id.propNameTextView);
        noofrooms = findViewById(R.id.noofrooms);
        // Retrieve name and email of current user


        // Set name and email in the corresponding TextViews


        Button checkinDateButton = findViewById(R.id.checkinDateButton);
        Button checkoutDateButton = findViewById(R.id.checkoutDateButton);

        checkinDateButton.setOnClickListener(this);
        checkoutDateButton.setOnClickListener(this);
        getDataFromServer();


        amtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfDays = calculateNumberOfDays();
                //int cost = amount.getText().toString();
                String text = amount.getText().toString();
                int cost = Integer.parseInt(text);
                if (numberOfDays>0) {
                    int temp = Integer.parseInt(noofrooms.getText().toString());
                    int totalamount = numberOfDays * cost * temp;
                    totalamountfield.setText(String.valueOf(totalamount));
                }
                else{
                    int temp = Integer.parseInt(noofrooms.getText().toString());
                    int totalamount = 1 * cost*temp;
                    totalamountfield.setText(String.valueOf(totalamount));
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rooms = noofrooms.getText().toString();
                int arooms = Integer.parseInt(availablerooms);
                int brooms = Integer.parseInt(rooms);
                if(brooms <= arooms) {

                    Intent intent = new Intent(BookingForm.this, Payment.class);
                    intent.putExtra("username", ParseUser.getCurrentUser().getUsername());
                    intent.putExtra("email", ParseUser.getCurrentUser().getEmail());
                    String text = totalamountfield.getText().toString();
                    String propNameIntent = propName.getText().toString();
                    String noofroomsentered = noofrooms.getText().toString();

                    // int cost = Integer.parseInt(text);
                    intent.putExtra("amount", text);
                    intent.putExtra("propname", propNameIntent);
                    intent.putExtra("availablerooms", availablerooms);
                    intent.putExtra("maxrooms", maxrooms);
                    intent.putExtra("noofrooms", noofroomsentered);
                    startActivity(intent);
                }else{
                    Toast.makeText(BookingForm.this, "NUM of rooms should be less than available rooms", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.checkinDateButton) {
            showDatePickerDialog(true);
        } else if (view.getId() == R.id.checkoutDateButton) {
            showDatePickerDialog(false);
        }
    }


    private int calculateNumberOfDays() {
        Calendar checkinCalendar = Calendar.getInstance();
        checkinCalendar.set(checkinYear, checkinMonth, checkinDay);

        Calendar checkoutCalendar = Calendar.getInstance();
        checkoutCalendar.set(checkoutYear, checkoutMonth, checkoutDay);

        long differenceInMillis = checkoutCalendar.getTimeInMillis() - checkinCalendar.getTimeInMillis();
        int days = (int) (differenceInMillis / (1000 * 60 * 60 * 24));
        return days;
    }

    private void showDatePickerDialog(final boolean isCheckin) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                if (isCheckin) {
                    checkinYear = year;
                    checkinMonth = month;
                    checkinDay = day;
                    updateCheckinDate();
                } else {
                    checkoutYear = year;
                    checkoutMonth = month;
                    checkoutDay = day;
                    updateCheckoutDate();
                }
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        if (isCheckin) {
            // Set the minimum date for check-in as today's date
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        } else {
            // Set the minimum date for check-out as the check-in date
            Calendar minCalendar = Calendar.getInstance();
            minCalendar.set(checkinYear, checkinMonth, checkinDay);
            datePickerDialog.getDatePicker().setMinDate(minCalendar.getTimeInMillis());
        }

        datePickerDialog.show();
    }

    private void updateCheckinDate() {
        String format = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(checkinYear, checkinMonth, checkinDay);
        String dateString = sdf.format(calendar.getTime());
        checkinDateTextView.setText(dateString);
    }

    private void updateCheckoutDate() {
        String format = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(checkoutYear, checkoutMonth, checkoutDay);
        String dateString = sdf.format(calendar.getTime());
        checkoutDateTextView.setText(dateString);
    }






    private void getDataFromServer() {
        String originalamt = getIntent().getStringExtra("propertyAmount");
        //ParseUser currentUser = ParseUser.getCurrentUser();
        //  ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        //ParseUser currentUser = ParseUser.getCurrentUser();
        //query.whereEqualTo("email", currentUser.getEmail());
        // Retrieve the intent
        Intent intent = getIntent();

// Retrieve the extras from the intent
        Bundle extras = intent.getExtras();
        if (extras != null) {
            name.setText(ParseUser.getCurrentUser().getUsername());
            email.setText(ParseUser.getCurrentUser().getEmail());
            String cost = extras.getString("amount");
            amount.setText(cost);
            String propNameIntent = extras.getString("propname");
            propName.setText(propNameIntent);
            availablerooms = extras.getString("availablerooms");
            maxrooms = extras.getString("maxrooms");

        }
//        query.getFirstInBackground(new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (e == null) {
//                    if (object != null) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                name.setText(object.getString("username"));
//                                email.setText(object.getString("email"));
//
//
//                                if (extras != null) {
//                                    String cost = extras.getString("amount");
//                                    amount.setText(cost);
//                                    String propNameIntent = extras.getString("propname");
//                                    propName.setText(propNameIntent);
//                                    availablerooms = extras.getString("availablerooms");
//                                    maxrooms = extras.getString("maxrooms");
//
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
//    }


    }}