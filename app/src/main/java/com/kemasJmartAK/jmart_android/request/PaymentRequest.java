package com.kemasJmartAK.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.kemasJmartAK.jmart_android.LoginActivity;
import com.kemasJmartAK.jmart_android.fragment1;

import java.util.HashMap;
import java.util.Map;

/**
 * Creating payment request about payment activity
 * @author Kemas Rafly Omar Thoriq
 */
public class PaymentRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:6969/payment/create";
    public final Map<String,String> params;

    public PaymentRequest(String productCount, String shipmentAddress, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId",String.valueOf(LoginActivity.loggedAccount.id));
        params.put("productId",String.valueOf(fragment1.productClicked.id));
        params.put("productCount",productCount);
        params.put("shipmentAddress",shipmentAddress);
        params.put("shipmentPlan",String.valueOf(fragment1.productClicked.shipmentPlans));
    }

    public Map<String,String> getParams(){
        return params;
    }
}
