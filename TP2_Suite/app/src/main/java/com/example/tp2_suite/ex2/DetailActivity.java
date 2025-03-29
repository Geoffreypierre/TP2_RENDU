package com.example.tp2_suite.ex2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tp2_suite.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex2_activity_detail);

        if (savedInstanceState == null && getIntent() != null) {
            String name = getIntent().getStringExtra("country_name");
            String capital = getIntent().getStringExtra("country_capital");
            String population = getIntent().getStringExtra("country_population");
            String flag = getIntent().getStringExtra("country_flag");
            String description = getIntent().getStringExtra("country_description");

            Country country = new Country();
            country.setName(name);
            country.setCapitalManually(capital);
            country.setPopulationFromString(population);
            country.setFlagUrl(flag);

            if (description != null && description.startsWith("Pays situé en ")) {
                String region = description.substring("Pays situé en ".length());
                country.setRegion(region);
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_container, CountryDetailFragment.newInstance(country))
                    .commit();
        }
    }
}

