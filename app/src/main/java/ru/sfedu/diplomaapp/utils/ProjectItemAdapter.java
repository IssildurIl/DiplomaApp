package ru.sfedu.diplomaapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.models.Project;

public class ProjectItemAdapter extends RecyclerView.Adapter<ProjectItemAdapter.ProjectItemViewHolder> {

    private List<Project> data;

    @NonNull
    @Override
    public ProjectItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_list_project,parent,false);
        return new ProjectItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectItemViewHolder holder, int position) {
        if(data!= null){
            Project project = data.get(position);
            holder.name.setText(project.getTitle());
        }else{
            holder.name.setText("No projects");
        }
    }

    @Override
    public int getItemCount() {
        if (data == null){
            return 0;
        } else {
            return data.size();
        }
    }


    public void setData(List<Project> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ProjectItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name, taskNumber;
        public ImageView projectImg;

        public ProjectItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.projectName);
            taskNumber = itemView.findViewById(R.id.projectCount);
            projectImg = itemView.findViewById(R.id.projectImage);
        }
    }
}
