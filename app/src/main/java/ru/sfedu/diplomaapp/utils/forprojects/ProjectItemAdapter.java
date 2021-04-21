package ru.sfedu.diplomaapp.utils.forprojects;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfedu.diplomaapp.databinding.RvListProjectBinding;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.interfaces.ProjectClickListener;

public class ProjectItemAdapter extends ListAdapter<Project,ProjectItemAdapter.ProjectItemViewHolder> {

    private ProjectClickListener projectClickListener;

    public ProjectItemAdapter(@NonNull ProjectDiffCallback diffCallback, ProjectClickListener projectClickListener) {
        super(diffCallback);
        this.projectClickListener = projectClickListener;
    }

    @NonNull
    @Override
    public ProjectItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ProjectItemViewHolder.from(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull ProjectItemViewHolder holder, int position) {
        if(getCurrentList()!= null){
            Project project = getItem(position);
            holder.bind(project, projectClickListener);
        }else{
            holder.binding.projectName.setText("No projects");
        }
    }


    public static class ProjectItemViewHolder extends RecyclerView.ViewHolder {
        RvListProjectBinding binding;

        private ProjectItemViewHolder(@NonNull RvListProjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private static ProjectItemViewHolder from (@NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            RvListProjectBinding binding= RvListProjectBinding.inflate(layoutInflater,parent,false);
            return new ProjectItemViewHolder(binding);
        }
        private void bind(Project project, ProjectClickListener projectClickListener){
            binding.setProject(project);
            binding.setClicklistener(projectClickListener);
            binding.executePendingBindings();
        }
    }
}
