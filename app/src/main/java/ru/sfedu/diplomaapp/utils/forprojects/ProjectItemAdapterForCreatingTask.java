package ru.sfedu.diplomaapp.utils.forprojects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.RvListProjectForCreatingTaskBinding;
import ru.sfedu.diplomaapp.interfaces.ProjectClickListener;
import ru.sfedu.diplomaapp.models.Project;

public class ProjectItemAdapterForCreatingTask extends ListAdapter<Project, ProjectItemAdapterForCreatingTask.ItemViewHolder> {

    private ProjectClickListener projectClickListener;

    public ProjectItemAdapterForCreatingTask(@NonNull ProjectDiffCallback diffCallback, ProjectClickListener projectClickListener) {
        super(diffCallback);
        this.projectClickListener = projectClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return ItemViewHolder.from(parent);
        }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (getCurrentList()!= null) {
            Project project = getItem(position);
            holder.bind(project,projectClickListener);
        }
        else {
            holder.binding.projectName.setText("No tasks");
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        RvListProjectForCreatingTaskBinding binding;

        public ItemViewHolder(@NonNull RvListProjectForCreatingTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public static ItemViewHolder from(@NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            RvListProjectForCreatingTaskBinding binding = RvListProjectForCreatingTaskBinding.inflate(layoutInflater,parent,false);
            return new ItemViewHolder(binding);
        }

        public void bind(Project project, ProjectClickListener projectClickListener) {
            binding.setProject(project);
            binding.setClicklistener(projectClickListener);
            binding.executePendingBindings();
        }
    }

}


