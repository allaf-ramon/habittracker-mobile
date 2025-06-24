package br.com.habittracker.mobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.habittracker.mobile.data.model.HabitResponse;
import br.com.habittracker.mobile.data.model.HabitStatsResponse;
import br.com.habittracker.mobile.data.repository.HabitRepository;

public class HabitDetailViewModel extends ViewModel {
    private final HabitRepository repository;
    public final MutableLiveData<Boolean> deleteSuccess = new MutableLiveData<>();

    public HabitDetailViewModel() {
        this.repository = new HabitRepository();
    }

    /**
     * Retorna um LiveData com os detalhes do hábito.
     * A Activity irá observar este LiveData para atualizar a UI.
     * @param habitId O ID do hábito a ser buscado.
     */
    public LiveData<HabitResponse> getHabitDetails(Long habitId) {
        return repository.getHabitById(habitId);
    }

    /**
     * Retorna um LiveData com as estatísticas do hábito.
     * A Activity irá observar este LiveData para atualizar a UI.
     * @param habitId O ID do hábito a ser buscado.
     */
    public LiveData<HabitStatsResponse> getHabitStats(Long habitId) {
        return repository.getHabitStats(habitId);
    }

    public void refreshData(long habitId) {
        // A lógica de recarga é acionada na Activity ao re-observar o LiveData.
        // Este método serve como um ponto de entrada explícito se necessário no futuro.
    }

    public void deleteHabit(Long habitId) {
        repository.deleteHabit(habitId, deleteSuccess);
    }
}
