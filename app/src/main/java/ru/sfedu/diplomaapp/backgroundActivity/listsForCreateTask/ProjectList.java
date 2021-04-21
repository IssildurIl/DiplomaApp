package ru.sfedu.diplomaapp.backgroundActivity.listsForCreateTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentProjectListBinding;
import ru.sfedu.diplomaapp.utils.forprojects.ProjectDiffCallback;
import ru.sfedu.diplomaapp.utils.forprojects.ProjectItemAdapterForCreatingTask;
import ru.sfedu.diplomaapp.utils.forprojects.ProjectsViewModel;

public class ProjectList extends Fragment {
    ProjectsViewModel pvm;
    public ProjectList() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProjectListBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_project_list,container,false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectsViewModel.class);
        binding.setProjectsListViewModel(pvm);

        ProjectItemAdapterForCreatingTask pia = new ProjectItemAdapterForCreatingTask(new ProjectDiffCallback(), project -> {
            pvm.onProjectToTaskItemClicked(project.get_id());
        });
        binding.recview.setAdapter(pia);

        pvm.projectList.observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                pia.submitList(projects);
            }
        });

        pvm.getNavigateProjectToTask().observe(getViewLifecycleOwner(),projectId->{
            if(projectId!=null) {
                NavHostFragment.findNavController(this).navigate(ProjectListDirections.actionProjectListToCreateTask(projectId,0));
                pvm.onProjectToTaskProjectItemNavigated();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}