package br.com.habittracker.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.habittracker.mobile.ui.AddHabitActivity;
import br.com.habittracker.mobile.ui.HabitDetailActivity;
import br.com.habittracker.mobile.ui.habit_list.HabitListAdapter;
import br.com.habittracker.mobile.viewmodel.HabitListViewModel;

public class MainActivity extends AppCompatActivity {
    private HabitListViewModel habitListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        habitListViewModel = new ViewModelProvider(this).get(HabitListViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_habits);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final HabitListAdapter adapter = new HabitListAdapter(habitListViewModel);
        recyclerView.setAdapter(adapter);

        // Seta o listener no adapter
        adapter.setOnHabitClickListener(habit -> {
            Intent intent = new Intent(MainActivity.this, HabitDetailActivity.class);
            intent.putExtra(HabitDetailActivity.EXTRA_HABIT_ID, habit.getId());
            startActivity(intent);
        });

        // Observa a lista de hábitos e atualiza o adapter
        habitListViewModel.getAllHabits().observe(this, habits -> {
            if (habits != null) {
                adapter.setHabits(habits);
            }
        });

        // Observa o resultado da operação de toggle
        habitListViewModel.toggleSuccess.observe(this, success -> {
            if (success) {
                // Se a operação foi bem sucedida, recarrega a lista para refletir a mudança
                habitListViewModel.refreshHabits();
            } else {
                Toast.makeText(this, "Falha ao atualizar o hábito.", Toast.LENGTH_SHORT).show();
                // Também recarrega para reverter visualmente a mudança otimista
                habitListViewModel.refreshHabits();
            }
        });

        findViewById(R.id.fab_add_habit).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega os dados toda vez que a activity volta a ser visível
        habitListViewModel.refreshHabits();
    }
}