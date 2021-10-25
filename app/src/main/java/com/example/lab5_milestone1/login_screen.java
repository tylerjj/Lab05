package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class login_screen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username","");

        if (!username.isEmpty()){
            goToHomeScreen(username);
        } else {
            setContentView(R.layout.activity_main);
        }

    }

    public void goToHomeScreen(String username) {
        Intent intent = new Intent(this, home_screen.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void login(View view){
        EditText inputUsername = (EditText) findViewById(R.id.inputUsername);
        EditText inputPassword = (EditText) findViewById(R.id.inputPassword);

        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()){

            Toast.makeText(login_screen.this, "Please make sure all fields are complete.", Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);

            // Add username to SharedPreferences object.
            sharedPreferences.edit().putString("username", username).apply();
            // Start second activity
            goToHomeScreen(username);
        }
    }




}