package br.com.habittracker.mobile.data.model;

import com.google.gson.annotations.SerializedName;

public class HabitStatsResponse {
    @SerializedName("currentStreak")
    private int currentStreak;

    @SerializedName("longestStreak")
    private int longestStreak;

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }
}
