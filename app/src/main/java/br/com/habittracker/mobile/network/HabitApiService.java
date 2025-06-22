package br.com.habittracker.mobile.network;

import java.util.List;

import br.com.habittracker.mobile.data.model.HabitResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HabitApiService {
    @GET("v1/habits")
    Call<List<HabitResponse>> getAllHabits();
}
