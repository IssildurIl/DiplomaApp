package ru.sfedu.diplomaapp.utils.forEmployees;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Project;

public class EmployeeDiffCallback extends DiffUtil.ItemCallback<Employee> {
    @Override
    public boolean areItemsTheSame(@NonNull Employee oldItem, @NonNull Employee newItem) {
        return oldItem.get_id() == newItem.get_id();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Employee oldItem, @NonNull Employee newItem) {
        return oldItem.equals(newItem);
    }
}
