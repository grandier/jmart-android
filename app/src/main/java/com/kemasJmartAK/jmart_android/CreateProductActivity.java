package com.kemasJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kemasJmartAK.jmart_android.model.Product;
import com.kemasJmartAK.jmart_android.request.CreateProductRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Activity about making a new product
 * @author Kemas Rafly Omar Thoriq
 */
public class CreateProductActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static Product product = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        EditText nameInput = (EditText) findViewById(R.id.nameInputCreate);
        EditText weightInput = (EditText) findViewById(R.id.weightInputCreate);
        EditText priceInput = (EditText) findViewById(R.id.priceInputCreate);
        EditText discountInput = (EditText) findViewById(R.id.discountInputCreate);
        CheckBox newCheck = (CheckBox) findViewById(R.id.newCheckCreate);
        CheckBox usedCheck = (CheckBox) findViewById(R.id.usedCheckCreate);
        Spinner category = (Spinner) findViewById(R.id.categoryDrawCreate);
        Spinner shipmentPlans = (Spinner) findViewById(R.id.shipmentDrawCreate);
        Button create = (Button) findViewById(R.id.registerProductButton);
        Button clear = (Button) findViewById(R.id.cancelProductButton);

        newCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    usedCheck.setChecked(false);
                }
            }
        });

        usedCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    newCheck.setChecked(false);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameInput.setText("");
                weightInput.setText("");
                priceInput.setText("");
                discountInput.setText("");
                usedCheck.setChecked(false);
                newCheck.setChecked(false);
                Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(CreateProductActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            product = gson.fromJson(object.toString(),Product.class);
                            Toast.makeText(CreateProductActivity.this,"Product Created",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }catch (JSONException e){
                            Toast.makeText(CreateProductActivity.this,"Product Failed to Create",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                CreateProductRequest requestProduct = new CreateProductRequest(nameInput.getText().toString(),weightInput.getText().toString(),String.valueOf(!newCheck.isChecked()),priceInput.getText().toString(),discountInput.getText().toString(),category.getSelectedItem().toString(),convertShipmentPlans(shipmentPlans.getSelectedItem().toString()),listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(CreateProductActivity.this);
                requestQueue.add(requestProduct);
            }
        });
    }

    private String convertShipmentPlans(String shipment){
        switch (shipment) {
            case "INSTANT":
                return "0";
            case "SAME DAY":
                return "1";
            case "NEXT DAY":
                return "2";
            case "REGULER":
                return "3";
            default:
                return "4";
        }
    }
}