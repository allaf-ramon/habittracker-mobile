package br.com.habittracker.mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.habittracker.mobile.ui.AddHabitActivity;
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

        RecyclerView recyclerView = findViewById(R.id.recycler_view_habits);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final HabitListAdapter adapter = new HabitListAdapter();
        recyclerView.setAdapter(adapter);

        habitListViewModel = new ViewModelProvider(this).get(HabitListViewModel.class);
        habitListViewModel.getAllHabits().observe(this, habits -> {
            if (habits != null) {
                adapter.setHabits(habits);
            }
        });

        findViewById(R.id.fab_add_habit).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
            startActivity(intent);
        });
    }
}