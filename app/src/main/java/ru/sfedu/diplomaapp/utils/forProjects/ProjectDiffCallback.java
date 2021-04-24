package ru.sfedu.diplomaapp.utils.forProjects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import ru.sfedu.diplomaapp.models.Project;

public class ProjectDiffCallback extends DiffUtil.ItemCallback<Project> {
    @Override
    public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
        return oldItem.get_id() == newItem.get_id();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
        return oldItem.equals(newItem);
    }
}
