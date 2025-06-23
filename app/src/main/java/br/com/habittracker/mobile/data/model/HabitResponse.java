package br.com.habittracker.mobile.data.model;

import com.google.gson.annotations.SerializedName;

public class HabitResponse {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("completedToday")
    private boolean completedToday;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompletedToday() {
        return completedToday;
    }
}
