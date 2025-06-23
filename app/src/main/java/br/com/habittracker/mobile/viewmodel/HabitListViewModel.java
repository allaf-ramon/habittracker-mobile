package br.com.habittracker.mobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import br.com.habittracker.mobile.data.model.HabitResponse;
import br.com.habittracker.mobile.data.repository.HabitRepository;

public class HabitListViewModel extends ViewModel {
    private final HabitRepository repository;
    private final MutableLiveData<List<HabitResponse>> allHabits;
    public final MutableLiveData<Boolean> toggleSuccess = new MutableLiveData<>();

    public HabitListViewModel() {
        repository = new HabitRepository();
        allHabits = new MutableLiveData<>();
        refreshHabits(); // Carga inicial
    }

    public LiveData<List<HabitResponse>> getAllHabits() {
        return allHabits;
    }

    public void refreshHabits() {
        // Usa o LiveData retornado pelo repositório para atualizar 'allHabits'
        repository.getAllHabits().observeForever(habits -> allHabits.setValue(habits));
    }

    public void toggleCompletion(HabitResponse habit) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(Calendar.getInstance().getTime());

        // O 'toggleSuccess' vai notificar a View (MainActivity) que a operação terminou.
        if (habit.isCompletedToday()) {
            repository.markAsNotCompleted(habit.getId(), dateString, toggleSuccess);
        } else {
            repository.markAsCompleted(habit.getId(), dateString, toggleSuccess);
        }
    }
}