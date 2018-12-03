package com.example.emrullah.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameText, passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userNameText = findViewById(R.id.signUpActivity_userName);
        passwordText=findViewById(R.id.signUpActivity_password);


        //This code checks if there is any user logged in before , if there is then it logs in instead of asking for account
        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser!=null){
            Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
            startActivity(intent);
        }



    }

    //This code makes the sign in jobs
    public void signIn(View view){

        ParseUser.logInInBackground(userNameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!= null)
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), "Welcome " + user.getUsername(), Toast.LENGTH_LONG).show();
                    //Intent
                    Intent intentTo_feedActivity =new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intentTo_feedActivity);
                }
            }
        });

    }

    //This code gets the information from user and registers into database.
    public void signUp(View view){
        ParseUser user= new ParseUser();
        user.setUsername(userNameText.getText().toString());
        user.setPassword(passwordText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null)
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), "User created!", Toast.LENGTH_LONG).show();
                    Intent intentTo_feedActivity =new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intentTo_feedActivity);
                }
            }
        });
    }


}
