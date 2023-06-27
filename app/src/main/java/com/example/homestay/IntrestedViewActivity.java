package com.example.homestay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class IntrestedViewActivity extends AppCompatActivity {


    private RecyclerView IntrestedRV;

    private ArrayList<IntrestedModel> courseModalArrayList;
    private IntrestedRVAdapter intrestedRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrested_view);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // initializing our views.
//        loadingPB = findViewById(R.id.idProgressBar);
        IntrestedRV = findViewById(R.id.idRVIntersted);



        courseModalArrayList = new ArrayList<>();

        // calling a method to
        // load recycler view.
        prepareCourseRV();

        // calling a method to get
        // the data from database.
        getDataFromServer();


    }

    private void getDataFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("intrested");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        String email = currentUser.getEmail();
                        if (objects.get(i).getString("userEmail").equals(email)) {
                            String propertyName = objects.get(i).getString("propertyName");
                            String propertyAmount = objects.get(i).getString("propertyAmount");
                            String userName = objects.get(i).getString("userName");
                            String userEmail= objects.get(i).getString("userEmail");



// Add each image to the slideModels list


                            courseModalArrayList.add(new IntrestedModel(propertyName, propertyAmount, userEmail,userName));
                        }
                    }
                    // Set the slideModels list to the image slider

                    intrestedRVAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(IntrestedViewActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void prepareCourseRV() {

        IntrestedRV.setHasFixedSize(true);
        IntrestedRV.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        intrestedRVAdapter = new IntrestedRVAdapter(this, courseModalArrayList);

        // setting adapter to our recycler view.
        IntrestedRV.setAdapter(intrestedRVAdapter);
    }

}