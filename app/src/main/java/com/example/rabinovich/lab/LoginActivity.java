package com.example.rabinovich.lab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText rutText;
    private EditText passwordText;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rutText = (EditText) findViewById(R.id.rutText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        preferences = getSharedPreferences(getString(R.string.shared_preferences_file), MODE_PRIVATE);

        Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    private void Register() {
        if(rutText.getText().toString().equals("") || passwordText.getText().toString().equals("")){
            DisplayMessage("Uno o mas campos vacios, por favor llene todos los campos");
        }else if(!isRutValid(rutText.getText().toString())){
            DisplayMessage("rut invalido");
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("rut", rutText.getText().toString());
            editor.putString("password", passwordText.getText().toString());
            editor.commit();
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }
    }

    private void DisplayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean isRutValid(String rut){
        try {
            int numericRut = Integer.parseInt(rut);
        }catch (NumberFormatException exception){
            return false;
        }
        List<Integer> digits = new ArrayList<>();
        for (char character : rut.toCharArray()){
            digits.add(Character.getNumericValue(character));
        }
        int verifyer = digits.get(digits.size() - 1);
        digits.remove(digits.size() - 1);
        List<Integer> numericSeries = new ArrayList<>();
        numericSeries.add(2);
        numericSeries.add(3);
        numericSeries.add(4);
        numericSeries.add(5);
        numericSeries.add(6);
        numericSeries.add(7);

        int addition = 0;
        int j = 0;
        for(int i = digits.size() -1; i >= 0; i--){
            addition += digits.get(i)*numericSeries.get(j);
            if (j == 5){
                j = 0;
            }else{
                j++;
            }
        }
        int module = addition%11;
        Integer difference = 11 - module;
        return difference == verifyer;
    }
}
