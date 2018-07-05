package com.example.rabinovich.lab;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeActivity extends AppCompatActivity {

    long dollarValue;
    private EditText valueText;
    private TextView resultText;
    final String url = "https://data.magnet.cl/api/v1/currencies/usd/clp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        dollarValue = 650;
        valueText = (EditText) findViewById(R.id.valueText);
        resultText = (TextView) findViewById(R.id.resultText);
    }

    @Override
    protected void onStart(){
        super.onStart();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    dollarValue = response.getLong("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);



        Button clpToUsdButton = (Button) findViewById(R.id.clpToUsdButton);
        clpToUsdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float new_value = Convert(Integer.parseInt(valueText.getText().toString()), 1);
                resultText.setText(Float.toString(new_value));
            }
        });

        Button usdToClpButton = (Button) findViewById(R.id.usdToClpButton);
        usdToClpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float new_value = Convert(Integer.parseInt(valueText.getText().toString()), 0);
                resultText.setText(Float.toString(new_value));
            }
        });
    }

    private float Convert(int value, int currency){
        if (currency == 0){
            //USD to CLP
            return (float) value * dollarValue;
        }else if(currency == 1){
            return (float) value / dollarValue;
        }
        return -1;
    }
}
