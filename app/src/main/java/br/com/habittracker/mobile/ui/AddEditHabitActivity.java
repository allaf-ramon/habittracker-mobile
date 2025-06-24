package br.com.habittracker.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import br.com.habittracker.mobile.R;
import br.com.habittracker.mobile.viewmodel.AddEditHabitViewModel;

public class AddEditHabitActivity extends AppCompatActivity {
    public static final String EXTRA_HABIT_ID = "br.com.habittracker.mobile.HABIT_ID";
    private AddEditHabitViewModel addEditHabitViewModel;
    private EditText editTextName;
    private EditText editTextDescription;
    private Long currentHabitId = null; // null significa modo de criação

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_habit);

        View mainView = findViewById(R.id.main);
        // Guarda o padding inicial definido no XML
        final int initialPaddingLeft = mainView.getPaddingLeft();
        final int initialPaddingTop = mainView.getPaddingTop();
        final int initialPaddingRight = mainView.getPaddingRight();
        final int initialPaddingBottom = mainView.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Soma o padding inicial com o padding das barras do sistema
            v.setPadding(
                    systemBars.left + initialPaddingLeft,
                    systemBars.top + initialPaddingTop,
                    systemBars.right + initialPaddingRight,
                    systemBars.bottom + initialPaddingBottom
            );
            return insets;
        });

        editTextName = findViewById(R.id.edit_text_habit_name);
        editTextDescription = findViewById(R.id.edit_text_habit_description);
        Button buttonSave = findViewById(R.id.button_save_habit);

        addEditHabitViewModel = new ViewModelProvider(this).get(AddEditHabitViewModel.class);

        if (getIntent().hasExtra(EXTRA_HABIT_ID)) {
            currentHabitId = getIntent().getLongExtra(EXTRA_HABIT_ID, -1);
            setTitle(R.string.edit_habit);

            addEditHabitViewModel.getHabitById(currentHabitId).observe(this, habit -> {
                if (habit != null) {
                    editTextName.setText(habit.getName());
                    editTextDescription.setText(habit.getDescription());
                }
            });
        } else {
            setTitle(R.string.add_habit);
        }

        addEditHabitViewModel.saveSuccess.observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish(); // Volta para a tela anterior
            } else {
                Toast.makeText(this, "Erro ao salvar.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String description = editTextDescription.getText().toString();

            if (name.trim().isEmpty()) {
                editTextName.setError("O nome é obrigatório");
                return;
            }

            addEditHabitViewModel.saveHabit(currentHabitId, name, description);
        });
    }
}