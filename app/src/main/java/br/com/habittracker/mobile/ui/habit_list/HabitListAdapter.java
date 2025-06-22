package br.com.habittracker.mobile.ui.habit_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import br.com.habittracker.mobile.R;
import br.com.habittracker.mobile.data.model.HabitResponse;

public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.HabitViewHolder> {
    private List<HabitResponse> habits = new ArrayList<>();

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_habit, parent, false);
        return new HabitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        HabitResponse currentHabit = habits.get(position);
        holder.textViewName.setText(currentHabit.getName());
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void setHabits(List<HabitResponse> habits) {
        this.habits = habits;
        notifyDataSetChanged();
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_habit_name);
        }
    }
}
