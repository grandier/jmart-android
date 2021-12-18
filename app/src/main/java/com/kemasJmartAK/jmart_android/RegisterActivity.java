package com.kemasJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kemasJmartAK.jmart_android.model.Account;
import com.kemasJmartAK.jmart_android.request.RegisterRequest;

import org.json.*;

/**
 * Activity that can make you register yourself with your own account
 * @author Kemas Rafly Omar Thoriq
 */
public class RegisterActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText textName = findViewById(R.id.nameRegister);
        EditText textEmail = findViewById(R.id.emailRegister);
        EditText textPassword = findViewById(R.id.passwordRegister);
        Button buttonRegister = findViewById(R.id.registerButton);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                loggedAccount = gson.fromJson(object.toString(), Account.class);
                                startActivity(intent);
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(textName.getText().toString(), textEmail.getText().toString(), textPassword.getText().toString(), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(registerRequest);
            }
        });

    }
}