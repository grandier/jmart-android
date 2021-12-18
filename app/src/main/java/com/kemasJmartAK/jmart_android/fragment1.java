package com.kemasJmartAK.jmart_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemasJmartAK.jmart_android.model.Account;
import com.kemasJmartAK.jmart_android.model.Product;
import com.kemasJmartAK.jmart_android.request.RequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment1} factory method to
 * create an instance of this fragment.
 */
public class fragment1 extends Fragment {

    private static final Gson gson = new Gson();
    public static ArrayList<Product> productsList = new ArrayList<>();
    static final int pageSize = 15;
    static int page = 0;
    public static Product productClicked = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inf = inflater.inflate(R.layout.fragment_fragment1, container, false);
        EditText pageNumber = (EditText) inf.findViewById(R.id.position);
        Button next = (Button) inf.findViewById(R.id.next_button);
        Button prev = (Button) inf.findViewById(R.id.prev_button);
        Button go = (Button) inf.findViewById(R.id.go_button);

        pageNumber.setText(String.valueOf(page + 1 ), TextView.BufferType.EDITABLE);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Previous", Toast.LENGTH_SHORT).show();
                page -= 1;
                if(page < 0){
                    page = 0;
                }
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Next", Toast.LENGTH_SHORT).show();
                page += 1;
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Go", Toast.LENGTH_SHORT).show();
                page = Integer.parseInt(pageNumber.getText().toString()) - 1;
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if(object != null){
                        if(fragment2.status == 0){
                            productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>(){}.getType());
                        }else{
                            productsList = fragment2.listFiltered;
                        }

                        System.out.println(productsList);
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                productsList
                        );

                        ListView lv = (ListView) inf.findViewById(R.id.listView);

                        lv.setAdapter(listViewAdapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                productClicked = (Product) lv.getItemAtPosition(i);
                                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(RequestFactory.getPage("product",page,pageSize,listener,null));
        return inf;
    }
}