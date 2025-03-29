    package com.example.tp2_suite.ex1;

    import android.app.AlertDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import androidx.appcompat.app.AppCompatActivity;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.SeekBar;
    import android.widget.Toast;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import com.example.tp2_suite.service.MusicService;

    import com.example.tp2_suite.R;

    import java.util.Collections;
    import java.util.Comparator;
    import java.util.List;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class FirstActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private CountryAdapter adapter;
        private ImageView volumeButton;
        private ImageView settingsButton;
        private boolean isPlaying = true;
        private float currentVolume = 4.0f;
        private Intent musicServiceIntent;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.ex1_activity_main);

            musicServiceIntent = new Intent(this, MusicService.class);
            startService(musicServiceIntent);

            recyclerView = findViewById(R.id.recyclerView);
            volumeButton = findViewById(R.id.volumeButton);
            settingsButton = findViewById(R.id.settingsButton);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            setupVolumeControl();
            setupPlayPauseControl();
            loadCountries();
        }

        private void setupVolumeControl() {
            volumeButton.setOnClickListener(v -> showVolumeDialog());
        }

        private void setupPlayPauseControl() {
            settingsButton.setOnClickListener(v -> {
                isPlaying = !isPlaying;
                Intent intent = new Intent(this, MusicService.class);
                intent.setAction(isPlaying ? "PLAY" : "PAUSE");
                startService(intent);

                settingsButton.setImageResource(isPlaying ?
                        R.drawable.ic_volume_settings :
                        R.drawable.ic_volume_settings_off);
            });
        }

        private void showVolumeDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_slider, null);
            SeekBar volumeSeekBar = dialogView.findViewById(R.id.volumeSeekBar);

            volumeSeekBar.setProgress((int)(currentVolume * 100));

            volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    currentVolume = progress / 100f;
                    Intent intent = new Intent(FirstActivity.this, MusicService.class);
                    intent.setAction("VOLUME");
                    intent.putExtra("volume", currentVolume);
                    startService(intent);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            builder.setView(dialogView)
                    .setPositiveButton("OK", null);

            builder.create().show();
        }

        private void loadCountries() {
            CountryService service = ApiClient.getClient().create(CountryService.class);
            Call<List<Country>> call = service.getAllCountries();

            call.enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.bringToFront();

                    if (response.isSuccessful() && response.body() != null) {
                        List<Country> countries = response.body();

                        Collections.sort(countries, new Comparator<Country>() {
                            @Override
                            public int compare(Country c1, Country c2) {
                                return c1.getCommonName().compareToIgnoreCase(c2.getCommonName());
                            }
                        });

                        adapter = new CountryAdapter(countries, FirstActivity.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(FirstActivity.this, "Erreur de récupération des pays", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    Toast.makeText(FirstActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            stopService(new Intent(this, MusicService.class));
        }
    }
