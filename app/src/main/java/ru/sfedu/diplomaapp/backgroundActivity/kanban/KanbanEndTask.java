package ru.sfedu.diplomaapp.backgroundActivity.kanban;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentKanbanEndTaskBinding;
import ru.sfedu.diplomaapp.utils.forTasks.TaskDiffCallback;
import ru.sfedu.diplomaapp.utils.forTasks.TaskItemAdapter;
import ru.sfedu.diplomaapp.utils.forTasks.TasksViewModel;


public class KanbanEndTask extends Fragment {
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_ID= "SP_EMPLOYEE_ID";
    long employeeId;
    SharedPreferences mSettings;
    TasksViewModel tvm;
    Bundle bundle = new Bundle();
    long projectId;
    public KanbanEndTask() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentKanbanEndTaskBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_kanban_end_task,container,false);
        binding.setLifecycleOwner(this);
        tvm = new ViewModelProvider(this).get(TasksViewModel.class);
        binding.setTasksListViewModel(tvm);
        TaskItemAdapter tia = new TaskItemAdapter(new TaskDiffCallback(), task -> {
            tvm.onTaskItemClicked(task.get_id());
        });
        binding.recview.setAdapter(tia);
        bundle = getParentFragment().getArguments();
        shared();

        tvm.getNavigateToTaskEdit().observe(getViewLifecycleOwner(), taskId -> {
            if(taskId!=null){
                bundle.putLong("E_TASK_ID", taskId);
                NavHostFragment.findNavController(this).navigate(R.id.action_kanbanStartTask_to_editTask,bundle);
                tvm.onTaskItemNavigated();
            }
        });
        projectId = bundle.getLong("projectId");
        tvm.getTaskListFinished(projectId,employeeId);
        tvm.taskListFinished.observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                tia.submitList(tasks);
            }
        });

        return binding.getRoot();
    }

    private void shared() {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_ID)) {
            employeeId=mSettings.getLong(APP_PREFERENCES_EMPLOYEE_ID,0);
        }
        try{
            employeeId = bundle.getLong("Auth_Employee_Id");
        }catch (Exception e){
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(APP_PREFERENCES_EMPLOYEE_ID, employeeId);
        editor.apply();
    }
}