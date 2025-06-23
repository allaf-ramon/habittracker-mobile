package br.com.habittracker.mobile.data.model;

import com.google.gson.annotations.SerializedName;

public class HabitStatsResponse {
    @SerializedName("currentStreak")
    private int currentStreak;

    public int getCurrentStreak() {
        return currentStreak;
    }
}
