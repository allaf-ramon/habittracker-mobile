package br.com.habittracker.mobile.data.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.habittracker.mobile.data.model.HabitRequest;
import br.com.habittracker.mobile.data.model.HabitResponse;
import br.com.habittracker.mobile.network.ApiClient;
import br.com.habittracker.mobile.network.HabitApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitRepository {
    private HabitApiService apiService;

    public HabitRepository() {
        apiService = ApiClient.getClient().create(HabitApiService.class);
    }

    public LiveData<List<HabitResponse>> getAllHabits() {
        MutableLiveData<List<HabitResponse>> data = new MutableLiveData<>();

        apiService.getAllHabits().enqueue(new Callback<List<HabitResponse>>() {
            @Override
            public void onResponse(Call<List<HabitResponse>> call, Response<List<HabitResponse>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<HabitResponse>> call, Throwable t) {
                Log.e(TAG, "Falha ao buscar hábitos", t);
                data.setValue(null);
            }
        });
        return data;
    }

    public void createHabit(HabitRequest habitRequest, MutableLiveData<Boolean> success) {
        apiService.createHabit(habitRequest).enqueue(new Callback<HabitResponse>() {
            @Override
            public void onResponse(Call<HabitResponse> call, Response<HabitResponse> response) {
                success.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<HabitResponse> call, Throwable t) {
                success.setValue(false);
            }
        });
    }

    public void markAsCompleted(Long habitId, String date, MutableLiveData<Boolean> success) {
        apiService.markAsCompleted(habitId, date).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                success.postValue(response.isSuccessful());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Falha ao marcar como concluído", t);
                success.postValue(false);
            }
        });
    }

    public void markAsNotCompleted(Long habitId, String date, MutableLiveData<Boolean> success) {
        apiService.markAsNotCompleted(habitId, date).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                success.postValue(response.isSuccessful());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Falha ao desmarcar como concluído", t);
                success.postValue(false);
            }
        });
    }
}
