package com.kemasJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kemasJmartAK.jmart_android.model.Store;
import com.kemasJmartAK.jmart_android.request.RegisterStoreRequest;
import com.kemasJmartAK.jmart_android.request.TopUpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutMeActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static Store storeAccount = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView name = (TextView) findViewById(R.id.nameUser);
        name.setText("" + LoginActivity.getLoggedAccount().name);

        TextView email = (TextView) findViewById(R.id.emailUser);
        email.setText("" + LoginActivity.getLoggedAccount().email);

        TextView balance = (TextView) findViewById(R.id.moneyBalance);
        balance.setText("" + LoginActivity.getLoggedAccount().balance);

        Button buttonTopUP = (Button) findViewById(R.id.buttonTopUp);
        EditText topUpInput = (EditText) findViewById(R.id.insertTopUp);

        topUpInput.setText("0");
        buttonTopUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(Boolean.parseBoolean(response)){
                            Toast.makeText(AboutMeActivity.this, "Topup berhasil", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AboutMeActivity.this, "Topup error!", Toast.LENGTH_SHORT).show();
                        }
                        LoginActivity.loggedAccount.balance += Double.parseDouble(topUpInput.getText().toString());
                        finish();
                        startActivity(getIntent());
                    }
                };
                TopUpRequest topUpRequest = new TopUpRequest(LoginActivity.getLoggedAccount().id, Double.parseDouble(topUpInput.getText().toString()), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(topUpRequest);
            }
        });

        Button registerButton = (Button) findViewById(R.id.storeButton);
        Button registerStore = (Button) findViewById(R.id.registerStoreButton);
        Button cancelRegister = (Button) findViewById(R.id.cancelStoreButton);
        EditText nameRegisterStore = (EditText) findViewById(R.id.nameStoreCreate);
        EditText addressRegisterStore = (EditText) findViewById(R.id.addressStoreCreate);
        EditText phoneNumberRegisterStore = (EditText) findViewById(R.id.phoneStoreCreate);

        RelativeLayout layoutRegister = (RelativeLayout) findViewById(R.id.registerStoreLayout);
        RelativeLayout layoutStore = (RelativeLayout) findViewById(R.id.storeFinishLayout);

        registerButton.setVisibility(View.GONE);
        layoutRegister.setVisibility(View.GONE);
        layoutStore.setVisibility(View.GONE);

        if (LoginActivity.getLoggedAccount().store == null){
            registerButton.setVisibility(View.VISIBLE);
        }
        else if (LoginActivity.getLoggedAccount().store != null){
            TextView dataName = (TextView) findViewById(R.id.storeNameDesc);
            dataName.setText("" + LoginActivity.getLoggedAccount().store.name);
            TextView dataAddress = (TextView) findViewById(R.id.storeAddressDesc);
            dataAddress.setText("" + LoginActivity.getLoggedAccount().store.address);
            TextView dataPhone = (TextView) findViewById(R.id.storePhoneDesc);
            dataPhone.setText("" + LoginActivity.getLoggedAccount().store.phoneNumber);
            layoutStore.setVisibility(View.VISIBLE);
        }
        else{
            registerButton.setVisibility(View.VISIBLE);
        }

        registerButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButton.setVisibility(View.GONE);
                layoutRegister.setVisibility(View.VISIBLE);
                cancelRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutRegister.setVisibility(View.GONE);
                        registerButton.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        registerStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                            Toast.makeText(AboutMeActivity.this, "Create Store Success!", Toast.LENGTH_SHORT).show();
                            LoginActivity.loggedAccount.store = gson.fromJson(object.toString(),Store.class);
                            System.out.println(LoginActivity.loggedAccount.store);
                            finish();
                            startActivity(getIntent());
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(AboutMeActivity.this, "Create Store Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                RegisterStoreRequest request = new RegisterStoreRequest(LoginActivity.getLoggedAccount().id,nameRegisterStore.getText().toString(),addressRegisterStore.getText().toString(),phoneNumberRegisterStore.getText().toString(),listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(request);
            }
        });
    }
}