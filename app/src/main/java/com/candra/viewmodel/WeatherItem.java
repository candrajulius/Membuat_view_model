package com.candra.viewmodel;

public class WeatherItem {
    private int id;
    private String name;
    private String currentWeather;
    private String deskripsi;
    private String temperature;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
