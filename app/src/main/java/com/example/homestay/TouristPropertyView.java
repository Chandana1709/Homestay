package com.example.homestay;

import android.os.Bundle;

import android.view.WindowManager;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.denzcoskun.imageslider.ImageSlider;
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

public class TouristPropertyView extends AppCompatActivity {

    // creating variables for our recycler view,
    // progressbar, array list and adapter class.
    private RecyclerView coursesRV;
    //    private ProgressBar loadingPB;

    //    private ProgressBar loadingPB;
    private ArrayList<TouristPropertyModel> courseModalArrayList;
    private TouristPropertyRVAdapter courseRVAdapter;
    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tourist_property_view);

        // initializing our views.
//        loadingPB = findViewById(R.id.idProgressBar);
        coursesRV = findViewById(R.id.idRVCourses);


        courseModalArrayList = new ArrayList<>();

        // calling a method to
        // load recycler view.
        prepareCourseRV();

        // calling a method to get
        // the data from database.
        getDataFromServer();
    }

    private void getDataFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("properties");
        query.whereEqualTo("status", 0);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {

                            String propertyName = objects.get(i).getString("propertyName");
                            String propertyAddress = objects.get(i).getString("propertyAddress");
//                            String userEmail = objects.get(i).getString("userEmail");
                            double averageRating = objects.get(i).getDouble("averagerating");                        // Get the image files from the parse object
                            List<ParseFile> imageFiles = objects.get(i).getList("images");



// Add each image to the slideModels list
                            List<SlideModel> slideModels = new ArrayList<>();
                            for (ParseFile imageFile : imageFiles) {
                                slideModels.add(new SlideModel(imageFile.getUrl(), ScaleTypes.FIT));
                            }


                            courseModalArrayList.add(new TouristPropertyModel(propertyName, propertyAddress,slideModels,averageRating));

                    }
                    // Set the slideModels list to the image slider

                    courseRVAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TouristPropertyView.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void prepareCourseRV() {

        coursesRV.setHasFixedSize(true);
        coursesRV.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        courseRVAdapter = new TouristPropertyRVAdapter(this, courseModalArrayList);

        // setting adapter to our recycler view.
        coursesRV.setAdapter(courseRVAdapter);
    }
}
