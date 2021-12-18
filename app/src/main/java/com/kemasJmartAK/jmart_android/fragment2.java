package com.kemasJmartAK.jmart_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemasJmartAK.jmart_android.model.Product;
import com.kemasJmartAK.jmart_android.request.RequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment2} factory method to
 * create an instance of this fragment.
 */
public class fragment2 extends Fragment {
    private static final Gson gson = new Gson();
    public static int status = 0;
    public static ArrayList<Product> listFiltered = new ArrayList<Product>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View productView = inflater.inflate(R.layout.fragment_fragment2, container, false);
        EditText name = productView.findViewById(R.id.nameInputFilter);
        EditText lowest = productView.findViewById(R.id.lowestInputFilter);
        EditText highest = productView.findViewById(R.id.highestInputFilter);
        CheckBox newCheck = productView.findViewById(R.id.newFilter);
        CheckBox usedCheck = productView.findViewById(R.id.usedFilter);
        Spinner category = productView.findViewById(R.id.spinnerCategory);
        Button apply = productView.findViewById(R.id.applyButton);
        Button clear = productView.findViewById(R.id.clearButton);

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
                status = 0;
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_SHORT).show();
                Response.Listener<String> listenerFiltered = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object = new JSONArray(response);
                            if(object != null){
                                listFiltered = gson.fromJson(object.toString(),new TypeToken<ArrayList<Product>>(){}.getType());
                                System.out.println(listFiltered);
                                Toast.makeText(getActivity(),"Filtered",Toast.LENGTH_SHORT).show();
                                status = 1;
                            }else{
                                Toast.makeText(getActivity(),"No Data",Toast.LENGTH_SHORT).show();
                            }
                            getActivity().finish();
                            getActivity().overridePendingTransition(0,0);
                            getActivity().startActivity(getActivity().getIntent());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(RequestFactory.getProduct(fragment1.page,fragment1.pageSize,name.getText().toString(),lowest.getText().toString(),highest.getText().toString(),String.valueOf(usedCheck.isChecked()),category.getSelectedItem().toString(),listenerFiltered,null));
            }
        });
        return productView;
    }
}