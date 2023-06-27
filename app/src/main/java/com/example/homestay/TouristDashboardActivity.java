package com.example.homestay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class TouristDashboardActivity extends AppCompatActivity {
    TextView welcomeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_dashboard);
   CardView support = findViewById(R.id.idsupport);
//        CardView addproperty = findViewById(R.id.idaddproperty);
        CardView booked = findViewById(R.id.idbookedproperty);
        CardView view = findViewById(R.id.idviewproperty);
        CardView logout = findViewById(R.id.idlogout);
        welcomeTextView=findViewById(R.id.welcomeviewname);

//         CardView intrested = findViewById(R.id.idfavoriteproperty);

                    // User object is returned in "object" variable
        String welcomeMessage="Welcome "+ ParseUser.getCurrentUser().getUsername();
        welcomeTextView.setText(welcomeMessage);


//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TouristDashboardActivity.this, ProfileActivity.class);
//                intent.putExtra("username", ParseUser.getCurrentUser().getUsername());
//                startActivity(intent);
//            }
//        });
//        addproperty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TouristDashboardActivity.this, AddPropertyActivity.class);
//                startActivity(intent);
//            }
//        });
//        intrested.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TouristDashboardActivity.this, IntrestedViewActivity.class);
//                startActivity(intent);
//            }
//        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristDashboardActivity.this, TouristPropertyView.class);
                startActivity(intent);
            }
        });
        booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristDashboardActivity.this, TouristBookedPropertyActivity.class);
                startActivity(intent);
            }
        });
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristDashboardActivity.this, ContactSupport.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristDashboardActivity.this, TouristLoginActivity.class);
                startActivity(intent);
            }
        });
    }

//        intrested.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(TouristDashboardActivity.this, TouristLoginActivity.class);
//            startActivity(intent);
//        }
//    });
//}

}