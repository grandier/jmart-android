package com.kemasJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

/**
 * Activity that can make you register your own store that linked with your logged account
 * @author Kemas Rafly Omar Thoriq
 */
public class StoreInvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_invoice);
        ListView storeHistory = findViewById(R.id.storeHistory);
    }
}