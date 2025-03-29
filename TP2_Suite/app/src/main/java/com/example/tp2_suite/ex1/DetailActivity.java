package com.example.tp2_suite.ex1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.tp2_suite.R;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView name = findViewById(R.id.countryName);
        TextView capital = findViewById(R.id.countryCapital);
        TextView region = findViewById(R.id.countryRegion);
        TextView population = findViewById(R.id.countryPopulation);
        ImageView flag = findViewById(R.id.countryFlag);

        name.setText(getIntent().getStringExtra("name"));
        capital.setText("Capitale: " + getIntent().getStringExtra("capital"));
        region.setText("Région: " + getIntent().getStringExtra("region"));
        population.setText("Population: " + getIntent().getIntExtra("population", 0));

        Glide.with(this).load(getIntent().getStringExtra("flag")).into(flag);
    }
}
