package br.com.habittracker.mobile.network;

import java.util.List;

import br.com.habittracker.mobile.data.model.HabitRequest;
import br.com.habittracker.mobile.data.model.HabitResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HabitApiService {
    @GET("v1/habits")
    Call<List<HabitResponse>> getAllHabits();

    @POST("v1/habits")
    Call<HabitResponse> createHabit(@Body HabitRequest habitRequest);

    @POST("v1/habits/{habitId}/completions/{date}")
    Call<Void> markAsCompleted(@Path("habitId") Long habitId, @Path("date") String date);

    @DELETE("v1/habits/{habitId}/completions/{date}")
    Call<Void> markAsNotCompleted(@Path("habitId") Long habitId, @Path("date") String date);
}
