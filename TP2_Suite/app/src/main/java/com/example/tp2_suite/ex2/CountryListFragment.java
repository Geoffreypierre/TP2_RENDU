package com.example.tp2_suite.ex2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp2_suite.R;
import com.example.tp2_suite.ex1.ApiClient;
import com.example.tp2_suite.ex1.Country;
import com.example.tp2_suite.ex1.CountryAdapter;
import com.example.tp2_suite.service.MusicService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class CountryListFragment extends Fragment {
    private boolean isMusicPlaying = true;
    private float currentVolume = 1.0f;
    private ImageView volumeButton;
    private ImageView settingsButton;
    private RecyclerView recyclerView;
    private CountryAdapter adapter;
    private OnCountrySelectedListener callback;
    private ProgressBar progressBar;

    public interface OnCountrySelectedListener {
        void onCountrySelected(Country country);
    }

    interface CountryApi {
        @GET("all")
        Call<List<Country>> getAllCountries();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof OnCountrySelectedListener) {
            callback = (OnCountrySelectedListener) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ex2_fragment_country_list, container, false);

        initializeViews(view);
        setupVolumeControls();
        setupRecyclerView();
        fetchCountries();

        requireActivity().startService(new Intent(requireActivity(), MusicService.class));

        return view;
    }

    private void initializeViews(View view) {
        volumeButton = view.findViewById(R.id.volumeButton);
        settingsButton = view.findViewById(R.id.settingsButton);
        recyclerView = view.findViewById(R.id.recycler_view_countries);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setupRecyclerView() {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setHasFixedSize(true);
        } else {
            showError("Error: RecyclerView not found");
        }
        progressBar.setVisibility(View.GONE);
    }

    private void fetchCountries() {
        CountryApi api = ApiClient.getClient().create(CountryApi.class);
        Call<List<Country>> call = api.getAllCountries();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Country> countries = response.body();

                    Collections.sort(countries, new Comparator<Country>() {
                        @Override
                        public int compare(Country c1, Country c2) {
                            return c1.getCommonName().compareToIgnoreCase(c2.getCommonName());
                        }
                    });

                    adapter = new CountryAdapter(countries, requireContext());
                    if (recyclerView != null) {
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    showError("Error fetching countries");
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                showError("Network error: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setupVolumeControls() {
        settingsButton.setOnClickListener(v -> toggleMusic());
        volumeButton.setOnClickListener(v -> showVolumeDialog());
    }

    private void toggleMusic() {
        Intent intent = new Intent(requireActivity(), MusicService.class);
        if (isMusicPlaying) {
            intent.setAction("PAUSE");
            settingsButton.setImageResource(R.drawable.ic_volume_settings_off);
        } else {
            intent.setAction("PLAY");
            settingsButton.setImageResource(R.drawable.ic_volume_settings);
        }
        requireActivity().startService(intent);
        isMusicPlaying = !isMusicPlaying;
    }

    private void showVolumeDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_slider);

        SeekBar volumeSeekBar = dialog.findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setProgress((int)(currentVolume * 100));

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    currentVolume = progress / 100f;
                    Intent intent = new Intent(requireActivity(), MusicService.class);
                    intent.setAction("VOLUME");
                    intent.putExtra("volume", currentVolume);
                    requireActivity().startService(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        dialog.show();
    }

    private void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            getActivity().stopService(new Intent(getActivity(), MusicService.class));
        }
    }
}