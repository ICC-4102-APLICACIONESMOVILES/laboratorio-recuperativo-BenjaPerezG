package com.example.rabinovich.lab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    private String rut;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(getString(R.string.shared_preferences_file), MODE_PRIVATE);
    }

    @Override
    protected void onStart(){
        super.onStart();
        rut = preferences.getString("rut", "");
        password = preferences.getString("password", "");

        if(rut.equals("") || password.equals("")){
            Intent login_intent = new Intent(this, LoginActivity.class);
            startActivity(login_intent);
        }else{
            Intent change_intent = new Intent(this, ChangeActivity.class);
            startActivity(change_intent);
        }
    }

}
