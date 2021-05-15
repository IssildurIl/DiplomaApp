package ru.sfedu.diplomaapp.backgroundActivity.fragmentForTasks;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.Calendar;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentEditTaskBinding;
import ru.sfedu.diplomaapp.databinding.FragmentWatchTaskBinding;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;

public class WatchTask extends Fragment {
    TaskViewModel tvm;
    EditText mutedAddEmployee,mutedAddProjectTo,mutedAddTime,mutedAddPoint;
    Calendar dateAndTime= Calendar.getInstance();
    NavController navController;
    ProjectViewModel pvm;
    EmployeeViewModel evm;
    Long projectId,employeeId,taskId;
    int taskType;

    public WatchTask() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWatchTaskBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_watch_task,container,false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectViewModel.class);
        evm = new ViewModelProvider(this).get(EmployeeViewModel.class);
        tvm = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setProjectViewModel(pvm);
        binding.setEmployeeViewModel(evm);
        binding.setTaskViewModel(tvm);
        catchData(binding);
        init(binding);
        buttons(binding);
        return binding.getRoot();
    }

    private void catchData(FragmentWatchTaskBinding binding) {
        try {
            Bundle getFromProjectList= this.getArguments();
            taskId = getFromProjectList.getLong("E_TASK_ID");
            taskType = getFromProjectList.getInt("E_TASK_TYPE");

            switch (taskType){
                case 0:{
                    tvm.getTask(taskId);
                    initEmployee(binding,tvm.task);
                    break;
                }
                case 1:{
                    tvm.getDevelopersTask(taskId);
                    initEmployee(binding,tvm.developersTask);
                    break;
                }
                case 2:{
                    tvm.getTestersTask(taskId);
                    initEmployee(binding,tvm.testersTask);
                    break;
                }
            }
            tvm.getTask(taskId);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        colorBar();
    }


    private void init(FragmentWatchTaskBinding binding) {
        mutedAddEmployee =binding.addEmployee;
        mutedAddProjectTo = binding.addProjectTo;
        mutedAddTime = binding.addTime;
        mutedAddPoint = binding.addPoint;
    }


    protected void colorBar() {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this.getContext(), R.color.primary));
    }


    //нажатия
    protected void buttons(FragmentWatchTaskBinding binding){
        binding.returnto.setOnClickListener(v -> {
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_watchTask_to_navFragment,null,navBuilder.build());
        });
    }

    private void initEmployee(FragmentWatchTaskBinding binding, LiveData<? extends Task> taskLiveData) {
        taskLiveData.observe(getViewLifecycleOwner(),task -> {
            binding.taskName.setText(task.getTaskName());
            binding.taskDesc.setText(task.getTaskDescription());
            binding.spinner.selectItemByIndex((int) task.getStatus());
            binding.addTime.setText(DateUtils.formatDateTime(getContext(),
                    task.getDeadline(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
            pvm.getProject(task.getProjectId());
            pvm.project.observe(getViewLifecycleOwner(),project -> {
                binding.addProjectTo.setText(project.getTitle());
            });
            switch (task.getClass().getName().toLowerCase()){
                case "ru.sfedu.diplomaapp.models.task":{
                    evm.getEmployee(task.getEmployeeId());
                    evm.employee.observe(getViewLifecycleOwner(),employee -> {
                        binding.addEmployee.setText(employee.getFirstName());
                    });
                    break;
                }
                case "ru.sfedu.diplomaapp.models.developerstask":{
                    evm.getDeveloper(task.getEmployeeId());
                    evm.developer.observe(getViewLifecycleOwner(),employee -> {
                        binding.addEmployee.setText(employee.getFirstName());
                    });
                    break;
                }
                case "ru.sfedu.diplomaapp.models.testerstask":{
                    evm.getTester(task.getEmployeeId());
                    evm.tester.observe(getViewLifecycleOwner(),employee -> {
                        binding.addEmployee.setText(employee.getFirstName());
                    });
                    break;
                }
            }
        });
    }
}