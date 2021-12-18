package com.kemasJmartAK.jmart_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.kemasJmartAK.jmart_android.model.Product;

/**
 * Main Activity that you always see
 * @author Kemas Rafly Omar Thoriq
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new fragment1(), "Product");
        vpAdapter.addFragment(new fragment2(), "Filter");
        viewPager.setAdapter(vpAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem create = menu.findItem(R.id.create_product);
        MenuItem search = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Butuh apa hari ini?");

        create.setVisible(LoginActivity.getLoggedAccount().store != null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.account_button) {
            Toast.makeText(this, "Account Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,AboutMeActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.create_product) {
            Toast.makeText(this, "Creating Product", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,CreateProductActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.history_button) {
            Toast.makeText(this, "Checking History", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,PersonalInvoiceActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}