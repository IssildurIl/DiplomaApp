package ru.sfedu.diplomaapp.utils.forProjects;

import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Observable;
import java.util.concurrent.CompletableFuture;

import ru.sfedu.diplomaapp.databinding.RvListProjectBinding;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.interfaces.ProjectClickListener;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TasksViewModel;

public class ProjectItemAdapter extends ListAdapter<Project,ProjectItemAdapter.ProjectItemViewHolder> {

    private ProjectClickListener projectClickListener;
    TasksViewModel tvm;
    public ProjectItemAdapter(@NonNull ProjectDiffCallback diffCallback, ProjectClickListener projectClickListener,TasksViewModel tvm) {
        super(diffCallback);
        this.projectClickListener = projectClickListener;
        this.tvm = tvm;
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
            holder.bind(project, projectClickListener,tvm);
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
        private void bind(Project project, ProjectClickListener projectClickListener,TasksViewModel tvm){
            binding.setTasksViewModel(tvm);
            AsyncTask.execute(()->{
            tvm.getNumTaskByProject(project.get_id());
            });
            project.setTaskNumber(String.valueOf(tvm.numberOfTasks));
            binding.setProject(project);
            binding.setClicklistener(projectClickListener);
            binding.executePendingBindings();

        }
    }
}
