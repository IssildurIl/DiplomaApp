package ru.sfedu.diplomaapp.mainlist.mainfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentHellofragmentBinding;
import ru.sfedu.diplomaapp.databinding.FragmentMyTaskBinding;
import ru.sfedu.diplomaapp.utils.forTasks.TaskDiffCallback;
import ru.sfedu.diplomaapp.utils.forTasks.TaskItemAdapter;
import ru.sfedu.diplomaapp.utils.forTasks.TasksViewModel;
import ru.sfedu.diplomaapp.utils.otherUtils.RecyclerDecoration;

public class MyTask extends Fragment {
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_ID= "SP_EMPLOYEE_ID";
    public static final String APP_PREFERENCES_EMPLOYEE_TYPE= "SP_EMPLOYEE_TYPE";
    long employeeIdFromSp;
    int employeeTypeFromSp;
    Bundle bundle = new Bundle();
    SharedPreferences mSettings;
    TasksViewModel tvm;
    NavController navController;
    public MyTask() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMyTaskBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_task,container,false);
        binding.setLifecycleOwner(this);
        tvm = new ViewModelProvider(this).get(TasksViewModel.class);
        binding.setTasksListViewModel(tvm);
        TaskItemAdapter tia = new TaskItemAdapter(new TaskDiffCallback(), task -> {
            tvm.onTaskItemClicked(task.get_id());
        });
        shared(tia);

        binding.recview.setAdapter(tia);
        tvm.getNavigateToTaskEdit().observe(getViewLifecycleOwner(), taskId -> {
            if(taskId!=null){
                bundle.putLong("E_TASK_ID", taskId);
                bundle.putInt("E_TASK_TYPE",employeeTypeFromSp);
                NavHostFragment.findNavController(this).navigate(R.id.action_navFragment_to_watchTask,bundle);
                tvm.onTaskItemNavigated();
            }
        });
        binding.goToPersonal.setOnClickListener(v -> {
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_navFragment_to_personalCabinet,null,navBuilder.build());
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.fab).setOnClickListener(v -> {
            navController.navigate(R.id.action_myTask_to_createTask);
        });
    }

    private void shared(TaskItemAdapter tia) {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_ID)) {
            employeeIdFromSp=mSettings.getLong(APP_PREFERENCES_EMPLOYEE_ID,0);
        }
        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_TYPE)) {
            employeeTypeFromSp=mSettings.getInt(APP_PREFERENCES_EMPLOYEE_TYPE,0);
        }
        methodTaskEmployee(employeeIdFromSp,employeeTypeFromSp,tia);

    }

    private void methodTaskEmployee(long employeeIdFromSp, int status,TaskItemAdapter tia) {
        switch (status) {
            case 0:
                tvm.getTaskByEmployee(employeeIdFromSp);
                tvm.taskListByEmployee.observe(getViewLifecycleOwner(), tasks -> {
                    if (tasks != null) {
                        tia.submitList(tasks);
                    }
                });
                break;
            case 1:
                tvm.getTaskByDeveloper(employeeIdFromSp);
                tvm.taskListByDeveloper.observe(getViewLifecycleOwner(), tasks -> {
                    if (tasks != null) {
                        tia.submitList(tasks);
                    }
                });
                break;
            case 2:
                tvm.getTaskByTester(employeeIdFromSp);
                tvm.taskListByTester.observe(getViewLifecycleOwner(), tasks -> {
                    if (tasks != null) {
                        tia.submitList(tasks);
                    }
                });
                break;
            default:
                break;
        }
    }
}