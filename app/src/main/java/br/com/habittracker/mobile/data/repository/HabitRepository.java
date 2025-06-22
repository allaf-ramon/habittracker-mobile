package br.com.habittracker.mobile.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

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
                // Em um app real, trataríamos o erro aqui (ex: log, mensagem ao usuário)
                data.setValue(null);
            }
        });
        return data;
    }
}
