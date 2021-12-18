package com.kemasJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kemasJmartAK.jmart_android.model.Payment;
import com.kemasJmartAK.jmart_android.request.PaymentRequest;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Activity that can make you insert money using custom amount to logged account
 * @author Kemas Rafly Omar Thoriq
 */
public class PaymentActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static Payment paid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        TextView name = (TextView) findViewById(R.id.nameProductBuy);
        TextView shipmentPlans = (TextView) findViewById(R.id.shipmentBuy);
        TextView price = (TextView) findViewById(R.id.priceBuy);
        EditText productCount = (EditText) findViewById(R.id.payment_product_count);
        EditText address = (EditText) findViewById(R.id.address_payment);
        Button buy = (Button) findViewById(R.id.buttonBuy);
        name.setText(fragment1.productClicked.name);
        switch (fragment1.productClicked.shipmentPlans) {
            case 0:
                shipmentPlans.setText("INSTANT");
                break;
            case 1:
                shipmentPlans.setText("SAME DAY");
                break;
            case 2:
                shipmentPlans.setText("NEXT DAY");
                break;
            case 3:
                shipmentPlans.setText("REGULER");
                break;
            default:
                shipmentPlans.setText("KARGO");
                break;
        }

        price.setText(String.valueOf(fragment1.productClicked.price));
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                paid = gson.fromJson(object.toString(), Payment.class);
                                System.out.println(paid);
                                Toast.makeText(PaymentActivity.this,"Bought",Toast.LENGTH_SHORT).show();
                                LoginActivity.loggedAccount.balance -= (fragment1.productClicked.price - (fragment1.productClicked.price*(fragment1.productClicked.discount/100)))*Double.parseDouble(productCount.getText().toString());
                                Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }catch (JSONException e){
                            Toast.makeText(PaymentActivity.this,"Failed to Buy",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                PaymentRequest paymentRequest = new PaymentRequest(productCount.getText().toString(),address.getText().toString(),listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
                requestQueue.add(paymentRequest);
            }
        });
    }
}