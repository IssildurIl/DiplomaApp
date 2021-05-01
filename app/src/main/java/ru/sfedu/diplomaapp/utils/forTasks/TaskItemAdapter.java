package ru.sfedu.diplomaapp.utils.forTasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.sfedu.diplomaapp.databinding.RvListTaskBinding;
import ru.sfedu.diplomaapp.interfaces.TaskClickListener;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;

public class TaskItemAdapter extends ListAdapter<Task,TaskItemAdapter.ItemViewHolder>{

    TaskClickListener clickListener;

    public TaskItemAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, TaskClickListener clickListener) {
        super(diffCallback);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ItemViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if(getCurrentList()!= null){
            Task task = getItem(position);
            holder.bind(task, clickListener);
        }else{
            holder.binding.taskName.setText("No projects");
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        RvListTaskBinding binding;

        private ItemViewHolder(@NonNull RvListTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private static ItemViewHolder from (@NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            RvListTaskBinding binding= RvListTaskBinding.inflate(layoutInflater,parent,false);
            return new ItemViewHolder(binding);
        }
        private void bind(Task task, TaskClickListener taskClickListener){
            binding.setTask(task);
            binding.setClicklistener(taskClickListener);
            binding.executePendingBindings();
        }
    }
}
