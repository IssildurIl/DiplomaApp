package ru.sfedu.diplomaapp.utils.forTasks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;

public class TaskDiffCallback extends DiffUtil.ItemCallback<Task> {
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.get_id() == newItem.get_id();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.equals(newItem);
    }
}
