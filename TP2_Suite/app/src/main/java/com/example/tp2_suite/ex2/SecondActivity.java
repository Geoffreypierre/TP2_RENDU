package com.example.tp2_suite.ex2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp2_suite.R;
import com.example.tp2_suite.ex1.ApiClient;
import com.example.tp2_suite.ex1.Country;
import com.example.tp2_suite.ex1.CountryAdapter;
import com.example.tp2_suite.service.MusicService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class SecondActivity extends AppCompatActivity {
    private boolean isMusicPlaying = true;
    private float currentVolume = 1.0f;
    private ImageView volumeButton;
    private ImageView settingsButton;
    private RecyclerView recyclerView;
    private CountryAdapter adapter;

    interface CountryApi {
        @GET("all")
        Call<List<Country>> getAllCountries();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex2_main_activity);
        initializeViews();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CountryListFragment())
                    .commit();
        }

        startService(new Intent(this, MusicService.class));

        if (volumeButton != null && settingsButton != null) {
            setupVolumeControls();
        }
        setupRecyclerView();
        fetchCountries();
    }

    @SuppressLint("MissingInflatedId")
    private void initializeViews() {
        volumeButton = findViewById(R.id.volumeButton);
        settingsButton = findViewById(R.id.settingsButton);
        recyclerView = findViewById(R.id.recycler_view_countries);
    }

    private void setupRecyclerView() {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }
    }

    private void fetchCountries() {
        CountryApi api = ApiClient.getClient().create(CountryApi.class);
        Call<List<Country>> call = api.getAllCountries();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Country> countries = response.body();
                    adapter = new CountryAdapter(countries, SecondActivity.this);
                    if (recyclerView != null) {
                        recyclerView.setAdapter(adapter);
                        recyclerView.bringToFront();
                    }
                } else {
                    showError("Error fetching countries");
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                showError("Network error: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setupVolumeControls() {
        volumeButton.setOnClickListener(v -> toggleMusic());
        settingsButton.setOnClickListener(v -> showVolumeDialog());
    }

    private void toggleMusic() {
        Intent intent = new Intent(this, MusicService.class);
        if (isMusicPlaying) {
            intent.setAction("PAUSE");
            volumeButton.setImageResource(R.drawable.ic_volume_settings_off);
        } else {
            intent.setAction("PLAY");
            volumeButton.setImageResource(R.drawable.ic_volume_settings);
        }
        startService(intent);
        isMusicPlaying = !isMusicPlaying;
    }

    private void showVolumeDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_slider);

        SeekBar volumeSeekBar = dialog.findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setProgress((int)(currentVolume * 100));

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    currentVolume = progress / 100f;
                    Intent intent = new Intent(SecondActivity.this, MusicService.class);
                    intent.setAction("VOLUME");
                    intent.putExtra("volume", currentVolume);
                    startService(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MusicService.class));
    }
}