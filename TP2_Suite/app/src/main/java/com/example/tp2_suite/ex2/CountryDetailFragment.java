package com.example.tp2_suite.ex2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.tp2_suite.R;

import java.io.Serializable;

public class CountryDetailFragment extends Fragment {
    private static final String ARG_COUNTRY = "country";
    private Country country;

    public static CountryDetailFragment newInstance(Country country) {
        CountryDetailFragment fragment = new CountryDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COUNTRY, (Serializable) country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            country = (Country) getArguments().getSerializable(ARG_COUNTRY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ex2_fragment_country_detail, container, false);

        ImageView flagImageView = view.findViewById(R.id.iv_detail_flag);
        TextView nameTextView = view.findViewById(R.id.tv_detail_name);
        TextView capitalTextView = view.findViewById(R.id.tv_detail_capital);
        TextView populationTextView = view.findViewById(R.id.tv_detail_population);

        if (country != null) {
            Glide.with(this).load(country.getFlag()).into(flagImageView);
            nameTextView.setText(country.getName());
            capitalTextView.setText("Capital: " + country.getCapital());
            populationTextView.setText("Population: " + country.getPopulation());
        }

        return view;
    }
}