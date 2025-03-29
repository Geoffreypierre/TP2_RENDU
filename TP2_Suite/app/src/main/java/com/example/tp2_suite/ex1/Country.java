package com.example.tp2_suite.ex1;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Country {
    @SerializedName("name")
    private Name name;

    @SerializedName("capital")
    private List<String> capital;

    @SerializedName("region")
    private String region;

    @SerializedName("subregion")
    private String subregion;

    @SerializedName("population")
    private int population;

    @SerializedName("flags")
    private Flags flags;

    public String getCommonName() {
        return name.common;
    }

    public String getCapital() {
        return capital != null && !capital.isEmpty() ? capital.get(0) : "N/A";
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public int getPopulation() {
        return population;
    }

    public String getFlagUrl() {
        return flags.png;
    }

    private static class Name {
        @SerializedName("common")
        private String common;
    }

    private static class Flags {
        @SerializedName("png")
        private String png;
    }
}

