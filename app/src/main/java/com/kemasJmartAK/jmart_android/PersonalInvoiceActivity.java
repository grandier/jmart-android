package com.kemasJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemasJmartAK.jmart_android.model.Payment;
import com.kemasJmartAK.jmart_android.model.Product;
import com.kemasJmartAK.jmart_android.request.RequestFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity that serve information about  invoice account
 * @author Kemas Rafly Omar Thoriq
 */
public class PersonalInvoiceActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    public static ArrayList<Payment> paymentList = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    static int pageSize = 20;
    static Integer page = 0;
    static Product paymentClicked = null;
    static int checker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_invoice);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        paymentList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Payment>>() {
                        }.getType());
                        convertPayment();
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                                PersonalInvoiceActivity.this, android.R.layout.simple_list_item_1, products
                        );
                        ListView lv = (ListView) findViewById(R.id.personalInvoice);
                        lv.setAdapter(listViewAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PersonalInvoiceActivity.this);
        requestQueue.add(RequestFactory.getPage("payment", page, pageSize, listener, null));

        Button refresh = findViewById(R.id.refrehsButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker == 0) {
                    Toast.makeText(PersonalInvoiceActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
                    PersonalInvoiceActivity.this.finish();
                    PersonalInvoiceActivity.this.overridePendingTransition(0, 0);
                    PersonalInvoiceActivity.this.startActivity(PersonalInvoiceActivity.this.getIntent());
                    checker += 1;
                } else if (checker > 0) {
                    Toast.makeText(PersonalInvoiceActivity.this, "Up to Date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.clear();
                checker = 0;
                Intent intent = new Intent(PersonalInvoiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void convertPayment() {
        int i = 0;
        for (Payment each : paymentList) {
            if(each.buyerId == LoginActivity.loggedAccount.id){
                Response.Listener<String> listenerConvert = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectConvert = new JSONObject(response);
                            if (objectConvert != null) {
                                Product temp = gson.fromJson(objectConvert.toString(),Product.class);
                                products.add(temp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(PersonalInvoiceActivity.this);
                requestQueue.add(RequestFactory.getById("product", each.productId, listenerConvert, null));
            }
        }
    }
}