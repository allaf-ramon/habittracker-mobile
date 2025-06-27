package br.com.habittracker.mobile.data.model;

import com.google.gson.annotations.SerializedName;

public class HabitStatsResponse {
    @SerializedName("currentStreak")
    private int currentStreak;

    @SerializedName("longestStreak")
    private int longestStreak;

    @SerializedName("successRate")
    private double successRate;

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public double getSuccessRate() {
        return successRate;
    }
}
