package com.example.tp2_suite.ex2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryService {
    @GET("all")
    Call<List<Country>> getAllCountries();
}
