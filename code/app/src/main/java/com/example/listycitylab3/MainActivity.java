package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(int position, City city) {
        cityAdapter.remove(cityAdapter.getItem(position));
        cityAdapter.insert(city, position);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(view -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        ListView citiesList = findViewById(R.id.city_list);
        citiesList.setOnItemClickListener((adapterView, view, i, l) -> {
            City clickedCity = (City) adapterView.getItemAtPosition(i);
            Toast.makeText(this, "Clicked City: " + clickedCity.getName(), Toast.LENGTH_SHORT).show();
            AddCityFragment frag = AddCityFragment.newInstance(clickedCity, i);
            frag.show(getSupportFragmentManager(), "Add/Edit City");
        });
    }
}