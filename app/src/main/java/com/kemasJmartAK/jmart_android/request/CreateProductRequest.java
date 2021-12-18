package com.kemasJmartAK.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.kemasJmartAK.jmart_android.LoginActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Handle request about creating new product
 * @author Kemas Rafly Omar Thoriq
 */
public class CreateProductRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:6969/product/create";
    public final Map<String,String> params;

    public CreateProductRequest(String name, String weight, String conditionUsed, String price, String discount, String category, String shipmentPlans, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        Integer i = LoginActivity.loggedAccount.id;
        params.put("accountId", i.toString());
        params.put("name",name);
        params.put("weight",weight);
        params.put("conditionUsed",conditionUsed);
        params.put("price", price);
        params.put("discount", discount);
        params.put("category", category);
        params.put("shipmentPlans", shipmentPlans);
    }

    public Map<String,String> getParams(){return params;}
}
