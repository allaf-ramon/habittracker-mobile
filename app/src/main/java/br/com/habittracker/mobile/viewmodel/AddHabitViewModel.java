package br.com.habittracker.mobile.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.habittracker.mobile.data.model.HabitRequest;
import br.com.habittracker.mobile.data.repository.HabitRepository;

public class AddHabitViewModel extends ViewModel {
    private HabitRepository repository;
    public MutableLiveData<Boolean> createSuccess = new MutableLiveData<>();

    public AddHabitViewModel() {
        this.repository = new HabitRepository();
    }

    public void createHabit(String name, String description) {
        HabitRequest request = new HabitRequest(name, description);
        repository.createHabit(request, createSuccess);
    }
}
