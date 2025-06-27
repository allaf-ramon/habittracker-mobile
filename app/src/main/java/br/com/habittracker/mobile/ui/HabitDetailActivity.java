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
    private TextView textName, textStreak, textLongestStreak, textSuccessPercent;
    private MaterialToolbar toolbar;
    private long habitId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            findViewById(R.id.app_bar_layout).setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        textName = findViewById(R.id.textview_detail_habit_name);
        textStreak = findViewById(R.id.textview_detail_current_streak);
        textLongestStreak = findViewById(R.id.textview_detail_longest_streak);
        textSuccessPercent = findViewById(R.id.textview_success_rate_percent);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());

        habitId = getIntent().getLongExtra(EXTRA_HABIT_ID, -1);
        if (habitId == -1) {
            Toast.makeText(this, getString(R.string.error_habit_id_not_found), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(HabitDetailViewModel.class);

        setupObservers();
    }

    private void setupObservers() {
        viewModel.getHabitDetails(habitId).observe(this, habit -> {
            if (habit != null) {
                textName.setText(habit.getName());
            }
        });

        viewModel.getHabitStats(habitId).observe(this, stats -> {
            if (stats != null) {
                textStreak.setText(getString(R.string.stat_days_format, stats.getCurrentStreak()));
                textLongestStreak.setText(getString(R.string.stat_days_format, stats.getLongestStreak()));
                textSuccessPercent.setText(getString(R.string.stat_percent_format, stats.getSuccessRate()));
            }
        });

        viewModel.deleteSuccess.observe(this, success -> {
            if (success) {
                Toast.makeText(this, getString(R.string.toast_delete_success), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, getString(R.string.toast_delete_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (habitId != -1) {
            setupObservers();
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