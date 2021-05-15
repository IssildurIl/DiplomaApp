package ru.sfedu.diplomaapp.backgroundActivity.listsForEditTask;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentEditTaskProjectsListBinding;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectDiffCallback;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectItemAdapterForCreatingTask;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectsViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;


public class EditTaskProjectsList extends Fragment {
    Long transactionEmployeeId,transactionTaskId;
    TaskViewModel tvm;
    ProjectsViewModel pvm;
    int taskType;
    public EditTaskProjectsList() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditTaskProjectsListBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_task_projects_list,container,false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectsViewModel.class);
        tvm = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setProjectsListViewModel(pvm);

        Bundle bundle = this.getArguments();
        Bundle sendBundle = new Bundle();
        try{
            transactionTaskId = bundle.getLong("E_TASK_ID");
            transactionEmployeeId = bundle.getLong("E_USER_ID");
            taskType =  bundle.getInt("E_TASK_TYPE");
            sendBundle.putInt("E_TASK_TYPE",taskType);
            sendBundle.putLong("E_TASK_ID",transactionTaskId);
            sendBundle.putLong("E_USER_ID",transactionEmployeeId);
        }catch (Exception e){
            e.printStackTrace();
        }
        tvm.getTask(transactionTaskId);
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
                sendBundle.putLong("E_PROJECT_ID",projectId);
                NavHostFragment.findNavController(this).navigate(R.id.action_editTaskProjectsList_to_editTask,sendBundle);
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