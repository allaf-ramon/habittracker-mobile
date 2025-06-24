package br.com.habittracker.mobile.ui.habit_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.habittracker.mobile.R;
import br.com.habittracker.mobile.data.model.HabitResponse;
import br.com.habittracker.mobile.viewmodel.HabitListViewModel;

public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.HabitViewHolder> {
    private List<HabitResponse> habits = new ArrayList<>();
    private final HabitListViewModel viewModel;
    private OnHabitClickListener clickListener;

    public HabitListAdapter(HabitListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public interface OnHabitClickListener {
        void onHabitClick(HabitResponse habit);
    }

    public void setOnHabitClickListener(OnHabitClickListener listener) {
        this.clickListener = listener;
    }

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

        holder.checkBoxCompleted.setOnCheckedChangeListener(null);
        holder.checkBoxCompleted.setChecked(currentHabit.isCompletedToday());

        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleCompletion(currentHabit);
        });

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onHabitClick(currentHabit);
            }
        });
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
        private final CheckBox checkBoxCompleted;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_habit_name);
            checkBoxCompleted = itemView.findViewById(R.id.checkbox_habit_completed);
        }
    }
}
