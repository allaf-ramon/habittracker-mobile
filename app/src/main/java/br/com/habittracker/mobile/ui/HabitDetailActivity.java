package br.com.habittracker.mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;

import br.com.habittracker.mobile.R;
import br.com.habittracker.mobile.viewmodel.HabitDetailViewModel;

public class HabitDetailActivity extends AppCompatActivity {
    public static final String EXTRA_HABIT_ID = "br.com.habittracker.mobile.HABIT_ID";
    private HabitDetailViewModel viewModel;
    private TextView textName, textDescription, textStreak;
    private long habitId;

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

        MaterialToolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textName = findViewById(R.id.textview_detail_habit_name);
        textDescription = findViewById(R.id.textview_detail_habit_description);
        textStreak = findViewById(R.id.textview_detail_current_streak);

        this.habitId = getIntent().getLongExtra(EXTRA_HABIT_ID, -1);
        if (this.habitId == -1) {
            Toast.makeText(this, "Erro: ID do Hábito não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(HabitDetailViewModel.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega os dados toda vez que a activity fica visível
        if (habitId != -1) {
            viewModel.getHabitDetails(this.habitId).observe(this, habit -> {
                if (habit != null) {
                    textName.setText(habit.getName());
                    textDescription.setText(habit.getDescription());
                }
            });

            viewModel.getHabitStats(this.habitId).observe(this, stats -> {
                if (stats != null) {
                    textStreak.setText(String.valueOf(stats.getCurrentStreak()));
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            finish(); // Volta para a tela anterior
            return true;
        }

        if (itemId == R.id.action_edit_habit) {
            Intent intent = new Intent(this, AddEditHabitActivity.class);
            // Passa o ID para a activity saber que está em modo de edição
            intent.putExtra(EXTRA_HABIT_ID, this.habitId);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_delete_habit) {
            showDeleteConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_habit)
                .setMessage(R.string.delete_habit_confirmation)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    // Chama o método do ViewModel para excluir
                    viewModel.deleteHabit(habitId);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}