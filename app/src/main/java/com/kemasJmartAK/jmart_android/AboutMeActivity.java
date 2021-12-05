package com.kemasJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    Button b1, b2, b3;
    RelativeLayout r1, r2;
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

        Button registerButton = (Button) findViewById(R.id.storeButton);
        Button registerStore = (Button) findViewById(R.id.registerStoreButton);
        Button cancelRegister = (Button) findViewById(R.id.cancelStoreButton);

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
                cancelRegister.setOnClickListener (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutRegister.setVisibility(View.GONE);
                        registerButton.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
    /*
        b1 = (Button) findViewById(R.id.storeButton);
        b2 = (Button) findViewById(R.id.registerStoreButton);
        b3 = (Button) findViewById(R.id.cancelStoreButton);

        r1 = (RelativeLayout) findViewById(R.id.registerStoreLayout);
        r2 = (RelativeLayout) findViewById(R.id.storeFinishLayout);


    public void b1Clicked(View v){
        b1.setVisibility(View.GONE);
        r1.setVisibility(View.VISIBLE);
        r2.setVisibility(View.GONE);
    }

    public void b2Clicked(View v){
        b1.setVisibility(View.GONE);
        r1.setVisibility(View.GONE);
        r2.setVisibility(View.VISIBLE);
    }

    public void b3Clicked(View v){
        b1.setVisibility(View.VISIBLE);
        r1.setVisibility(View.GONE);
        r2.setVisibility(View.GONE);
    }
    */
}