package ru.sfedu.diplomaapp.mainlist.mainfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentToDoBinding;
import ru.sfedu.diplomaapp.utils.forTasks.TaskDiffCallback;
import ru.sfedu.diplomaapp.utils.forTasks.TaskItemAdapter;
import ru.sfedu.diplomaapp.utils.forTasks.TasksViewModel;


public class ToDo extends Fragment {

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_ID= "SP_EMPLOYEE_ID";
    long employeeIdFromBundle,employeeIdFromSp;;
    Bundle bundle = new Bundle();
    SharedPreferences mSettings;
    TasksViewModel tvm;
    NavController navController;

    public ToDo() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentToDoBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_to_do,container,false);
        binding.setLifecycleOwner(this);
        tvm = new ViewModelProvider(this).get(TasksViewModel.class);
        binding.setTasksListViewModel(tvm);
        TaskItemAdapter tia = new TaskItemAdapter(new TaskDiffCallback(), task -> {
            tvm.onTaskItemClicked(task.get_id());
        });
        shared();

        binding.recview.setAdapter(tia);
        tvm.getNavigateToTaskEdit().observe(getViewLifecycleOwner(), taskId -> {
            if(taskId!=null){
                bundle.putLong("E_TASK_ID", taskId);
                NavHostFragment.findNavController(this).navigate(R.id.action_navFragment_to_watchTask,bundle);
                tvm.onTaskItemNavigated();
            }
        });

        tvm.outdatedTask.observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                tia.submitList(tasks);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.fab).setOnClickListener(v -> {
            navController.navigate(R.id.action_toDo_to_createTask);
        });
    }
    private void shared() {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        try{
            Bundle catchbundle = getParentFragment().getArguments();
            employeeIdFromBundle = catchbundle.getLong("Auth_Employee_Id");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_ID)) {
            employeeIdFromSp=mSettings.getLong(APP_PREFERENCES_EMPLOYEE_ID,0);
        }
        if(employeeIdFromBundle!=0){
            tvm.getTaskByEmployeeAndDate(new Date().getTime(),employeeIdFromBundle);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putLong(APP_PREFERENCES_EMPLOYEE_ID, employeeIdFromBundle);
            editor.apply();
        }else{
            tvm.getTaskByEmployeeAndDate(new Date().getTime(),employeeIdFromSp);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putLong(APP_PREFERENCES_EMPLOYEE_ID, employeeIdFromSp);
            editor.apply();
        }
    }
}