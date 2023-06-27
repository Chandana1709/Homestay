package com.example.homestay;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class TouristRegActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tourist_reg);
        setTitle("SignUp");
        usernameEditText=findViewById(R.id.usernameEditTextSignUp);
        emailEditText=findViewById(R.id.emailEditTextSignUp);
        passwordEditText=findViewById(R.id.passwordEditTextSignUp);
        signUpButton=findViewById(R.id.buttonSignUp);
        textViewLogin=findViewById(R.id.textViewSignUp2);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().toString()!=null && emailEditText.getText().toString()!=null && passwordEditText.getText().toString()!=null){
                    ParseUser user = new ParseUser();
                    user.setUsername(usernameEditText.getText().toString());
                    user.setEmail(emailEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());
                    user.put("user",1);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null){
                                ParseUser.logOut();
                                showAlert("Sign Up successful","Please verify your email and Login");
                            }else {
                                showAlert("Cannot create your account", e.getMessage());
                            }
                        }
                    });
                }

            }
        });
    }
    private void startLoginActivity(){
        Intent intent = new Intent(TouristRegActivity.this,TouristLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startWelcomeActivity(){
        Intent intent = new Intent(TouristRegActivity.this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void showAlert(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                })
                .show();
    }
}
