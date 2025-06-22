package br.com.habittracker.mobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.habittracker.mobile.data.model.HabitResponse;
import br.com.habittracker.mobile.data.repository.HabitRepository;

public class HabitListViewModel extends ViewModel {
    private HabitRepository repository;
    private LiveData<List<HabitResponse>> allHabits;

    public HabitListViewModel() {
        repository = new HabitRepository();
        allHabits = repository.getAllHabits();
    }

    public LiveData<List<HabitResponse>> getAllHabits() {
        return allHabits;
    }

    public void refreshHabits() {
        allHabits = repository.getAllHabits();
    }
}
