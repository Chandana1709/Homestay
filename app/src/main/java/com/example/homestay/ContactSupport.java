package com.example.homestay;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class ContactSupport extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText feedbackEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);

        // Initialize views
        ratingBar = (RatingBar) findViewById(R.id.rbStars);
//        feedbackEditText = findViewById(R.id.etFeedback);
        sendButton = findViewById(R.id.btnSend);

        // Set click listener for the send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user's feedback and rating
//                String feedback = feedbackEditText.getText().toString();
                float rating = ratingBar.getRating();

                // Save the feedback and rating to the Parse server
                ParseObject feedbackObject = new ParseObject("properties");
//                feedbackObject.put("feedback", feedback);
                feedbackObject.put("rating", rating);
                feedbackObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(ContactSupport.this, "Feedback sent!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ContactSupport.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}