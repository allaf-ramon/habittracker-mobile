package br.com.habittracker.mobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.habittracker.mobile.data.model.HabitRequest;
import br.com.habittracker.mobile.data.model.HabitResponse;
import br.com.habittracker.mobile.data.repository.HabitRepository;

public class AddEditHabitViewModel extends ViewModel {
    private HabitRepository repository;
    public final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>();

    public AddEditHabitViewModel() {
        this.repository = new HabitRepository();
    }

    public LiveData<HabitResponse> getHabitById(long habitId) {
        return repository.getHabitById(habitId);
    }

    public void saveHabit(Long habitId, String name, String description) {
        HabitRequest request = new HabitRequest(name, description);

        if (habitId == null) {
            repository.createHabit(request, saveSuccess);
        } else {
            repository.updateHabit(habitId, request, saveSuccess);
        }
    }
}
