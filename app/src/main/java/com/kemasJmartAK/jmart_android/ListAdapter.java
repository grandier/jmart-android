package com.kemasJmartAK.jmart_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kemasJmartAK.jmart_android.model.Product;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private static ArrayList<Product> listProduct;
    private LayoutInflater mInflater;

    public ListAdapter(Context fragment1, ArrayList<Product> result) {
        listProduct = result;
        mInflater = LayoutInflater.from(fragment1);
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int i) {
        return listProduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
