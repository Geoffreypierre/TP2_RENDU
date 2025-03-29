package com.example.tp2_suite.ex2;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("name")
    private NameObject name;

    @SerializedName("capital")
    private String[] capital;

    @SerializedName("population")
    private long population;

    @SerializedName("flags")
    private FlagObject flags;

    @SerializedName("region")
    private String region;

    public Country(String name, String capital, String population, String flag, String description) {
        this.name = new NameObject();
        this.flags = new FlagObject();

    }

    public Country() {
    }

    public static class NameObject {
        @SerializedName("common")
        private String common;

        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }
    }

    public static class FlagObject {
        @SerializedName("png")
        private String png;

        public String getPng() {
            return png;
        }

        public void setPng(String png) {
            this.png = png;
        }
    }

    public String getName() {
        return name != null ? name.getCommon() : "";
    }

    public String getCapital() {
        return (capital != null && capital.length > 0) ? capital[0] : "N/A";
    }

    public String getPopulation() {
        return String.format("%,d", population);
    }

    public String getFlag() {
        return flags != null ? flags.getPng() : "";
    }

    public String getDescription() {
        return "Pays situé en " + region;
    }

    public void setName(String name) {
        if (this.name == null) {
            this.name = new NameObject();
        }
        this.name.setCommon(name);
    }

    public void setCapitalManually(String capital) {
        this.capital = new String[]{capital};
    }

    public void setPopulationFromString(String populationStr) {
        try {
            this.population = Long.parseLong(populationStr.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            this.population = 0;
        }
    }

    public void setFlagUrl(String flagUrl) {
        if (this.flags == null) {
            this.flags = new FlagObject();
        }
        this.flags.setPng(flagUrl);
    }

    public void setRegion(String region) {
        this.region = region;
    }
}