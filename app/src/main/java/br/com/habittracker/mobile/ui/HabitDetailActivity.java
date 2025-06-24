package br.com.habittracker.mobile.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import br.com.habittracker.mobile.R;
import br.com.habittracker.mobile.viewmodel.HabitDetailViewModel;

public class HabitDetailActivity extends AppCompatActivity {
    public static final String EXTRA_HABIT_ID = "br.com.habittracker.mobile.HABIT_ID";
    private HabitDetailViewModel viewModel;
    private TextView textName, textDescription, textStreak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_habit_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textName = findViewById(R.id.textview_detail_habit_name);
        textDescription = findViewById(R.id.textview_detail_habit_description);
        textStreak = findViewById(R.id.textview_detail_current_streak);

        long habitId = getIntent().getLongExtra(EXTRA_HABIT_ID, -1);
        if (habitId == -1) {
            Toast.makeText(this, "Erro: ID do Hábito não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(HabitDetailViewModel.class);

        // Observa e atualiza os detalhes do hábito
        viewModel.getHabitDetails(habitId).observe(this, habit -> {
            if (habit != null) {
                textName.setText(habit.getName());
                textDescription.setText(habit.getDescription());
            }
        });

        // Observa e atualiza as estatísticas
        viewModel.getHabitStats(habitId).observe(this, stats -> {
            if (stats != null) {
                textStreak.setText(String.valueOf(stats.getCurrentStreak()));
            }
        });
    }
}