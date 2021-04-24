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


public class EditTaskProjectsList extends Fragment {
    Integer transactionSpinnerVal;
    String transactionTaskName,transactionTaskDescription;
    Long transactionEmployeeId;
    ProjectsViewModel pvm;
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
        binding.setProjectsListViewModel(pvm);

        Bundle bundle = this.getArguments();
        Bundle sendBundle = new Bundle();
        try{
            transactionSpinnerVal = bundle.getInt("E_SPINNER_VAL");
            transactionTaskName = bundle.getString("E_TASK_NAME");
            transactionTaskDescription = bundle.getString("E_TASK_DESCRIPTION");
            transactionEmployeeId = bundle.getLong("E_EMPLOYEE_ID");
            sendBundle.putString("E_TASK_NAME",transactionTaskName);
            sendBundle.putString("E_TASK_DESCRIPTION",transactionTaskDescription);
            sendBundle.putLong("E_EMPLOYEE_ID",transactionEmployeeId);
            sendBundle.putInt("E_SPINNER_VAL",transactionSpinnerVal);
        }catch (Exception e){
            e.printStackTrace();
        }
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