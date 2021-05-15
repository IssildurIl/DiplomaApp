package ru.sfedu.diplomaapp.backgroundActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentCreateTaskBinding;
import ru.sfedu.diplomaapp.databinding.FragmentEditTaskBinding;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;

public class EditTask extends Fragment {
    TaskViewModel tvm;
    EditText mutedAddEmployee,mutedAddProjectTo,mutedAddTime,mutedAddPoint;
    Calendar dateAndTime= Calendar.getInstance();
    NavController navController;
    ProjectViewModel pvm;
    EmployeeViewModel evm;
    Long projectId,employeeId,taskId;
    int taskType;
    public EditTask() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditTaskBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_task,container,false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectViewModel.class);
        evm = new ViewModelProvider(this).get(EmployeeViewModel.class);
        tvm = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setProjectViewModel(pvm);
        binding.setEmployeeViewModel(evm);
        binding.setTaskViewModel(tvm);
        Bundle getFrom = getArguments();
        projectId= getFrom.getLong("E_PROJECT_ID");
        employeeId = getFrom.getLong("E_USER_ID");
        taskId = getFrom.getLong("E_TASK_ID");
        taskType = getFrom.getInt("E_TASK_TYPE");
        try{
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
        }catch (Exception e){
            e.printStackTrace();
        }

        init(binding);
        buttons(binding);

        OnBackPressedCallback callback = new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                    Bundle bundle = new Bundle();
                    bundle.putLong("projectId",projectId);
                    NavOptions.Builder navBuilder =  new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                    navController.navigate(R.id.action_editTask_to_editProject,bundle,navBuilder.build());
                    tvm.eventTaskUpdateFinished();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        colorBar();
        setInitialDateTime();
    }


    private void init(FragmentEditTaskBinding binding) {
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


    protected void buttons(FragmentEditTaskBinding binding){
        binding.returnto.setOnClickListener(v -> {
            switch (taskType) {
                case 0: {
                    saveEditData(binding,tvm.task);
                    break;
                }
                case 1: {
                    saveEditData(binding,tvm.developersTask);
                    break;
                }
                case 2: {
                    saveEditData(binding,tvm.testersTask);
                    break;
                }
            }
        });
        mutedAddTime.setOnClickListener(v -> {
            setDate(v);
        });

        mutedAddProjectTo.setOnClickListener(v->{
            switch (taskType) {
                case 0: {
                    editMutedAddProjectTo(binding,tvm.task);
                    break;
                }
                case 1: {
                    editMutedAddProjectTo(binding,tvm.developersTask);
                    break;
                }
                case 2: {
                    editMutedAddProjectTo(binding,tvm.testersTask);
                    break;
                }
            }

        });

        mutedAddEmployee.setOnClickListener(v->{
            switch (taskType) {
                case 0: {
                    updateMutedEmployee(binding,tvm.task);
                    break;
                }
                case 1: {
                    updateMutedEmployee(binding,tvm.developersTask);
                    break;
                }
                case 2: {
                    updateMutedEmployee(binding,tvm.testersTask);
                    break;
                }
            }
        });

    }



    //Дата и время
    public void setDate(View v) {
        new DatePickerDialog(this.getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {
        mutedAddTime.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }


    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d= (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    private void initEmployee(FragmentEditTaskBinding binding, LiveData<? extends Task> taskLiveData) {
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
                    if(employeeId!=0){
                        evm.getEmployee(employeeId);
                    }else {
                        evm.getEmployee(task.getEmployeeId());
                    }
                    evm.employee.observe(getViewLifecycleOwner(),employee -> {
                        binding.addEmployee.setText(employee.getFirstName());
                    });
                    break;
                }
                case "ru.sfedu.diplomaapp.models.developerstask":{
                    if(employeeId!=0){
                        evm.getDeveloper(employeeId);
                    }else {
                        evm.getDeveloper(task.getEmployeeId());
                    }
                    evm.developer.observe(getViewLifecycleOwner(),employee -> {
                        binding.addEmployee.setText(employee.getFirstName());
                    });
                    break;
                }
                case "ru.sfedu.diplomaapp.models.testerstask":{
                    if(employeeId!=0){
                        evm.getTester(employeeId);
                    }else {
                        evm.getTester(task.getEmployeeId());
                    }
                    evm.tester.observe(getViewLifecycleOwner(),employee -> {
                        binding.addEmployee.setText(employee.getFirstName());
                    });
                    break;
                }
            }
        });
    }

    private void updateMutedEmployee(FragmentEditTaskBinding binding, LiveData<? extends Task> liveData) {
        liveData.getValue().setTaskName(binding.taskName.getText().toString());
        liveData.getValue().setTaskDescription(binding.taskDesc.getText().toString());
        liveData.getValue().setStatus(binding.spinner.getSelectedIndex());
        if(projectId!=0){
            liveData.getValue().setProjectId(projectId);
        }
        liveData.getValue().setDeadline(dateAndTime.getTime().getTime());
        switch (taskType) {
            case 0: {
                tvm.updateTaskToEmployee();
                break;
            }
            case 1: {
                tvm.updateDevelopersTaskToEmployee();
                break;
            }
            case 2: {
                tvm.updateTestersTaskToEmployee();
                break;
            }
        }
        tvm.getEventTaskToEmployeeUpd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                Bundle bundle = new Bundle();
                bundle.putLong("E_TASK_ID",taskId);
                bundle.putLong("E_PROJECT_ID",employeeId);
                bundle.putInt("E_TASK_TYPE",taskType);
                if(binding.spinner.length()!=0){
                    bundle.putInt("SPINNER_VAL", binding.spinner.getSelectedIndex());
                }
                NavHostFragment.findNavController(this).navigate(R.id.action_editTask_to_editTaskUserList,bundle);
                tvm.eventTaskToEmployeeUpdateFinished();
            }
        });
    }

    private void saveEditData(FragmentEditTaskBinding binding, LiveData<? extends Task> liveData) {
        if (binding.taskName.getText().toString().length() != 0) {
            liveData.getValue().setTaskName(binding.taskName.getText().toString());
        }
        if (binding.taskDesc.getText().toString().length() != 0) {
            liveData.getValue().setTaskDescription(binding.taskDesc.getText().toString());
        }
        if (projectId != null && projectId != 0) {
            liveData.getValue().setProjectId(projectId);
        }
        if (employeeId != null && employeeId != 0) {
            liveData.getValue().setEmployeeId(employeeId);
        }
        liveData.getValue().setStatus(binding.spinner.getSelectedIndex());
        liveData.getValue().setDeadline(dateAndTime.getTime().getTime());
        switch (taskType) {
            case 0: {
                tvm.updateTask();
                break;
            }
            case 1: {
                tvm.updateDevelopersTask();
                break;
            }
            case 2: {
                tvm.updateTestersTask();
                break;
            }
        }
        tvm.getEventTaskUpd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                liveData.observe(getViewLifecycleOwner(),task -> {
                    Bundle bundle = new Bundle();
                    bundle.putLong("projectId",task.getProjectId());
                    NavOptions.Builder navBuilder =  new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                    navController.navigate(R.id.action_editTask_to_editProject,bundle,navBuilder.build());
                    tvm.eventTaskUpdateFinished();
                });
            }
        });
    }

    private void editMutedAddProjectTo(FragmentEditTaskBinding binding,LiveData<? extends Task> liveData) {
        liveData.getValue().setTaskName(binding.taskName.getText().toString());
        liveData.getValue().setTaskDescription(binding.taskDesc.getText().toString());
        liveData.getValue().setStatus(binding.spinner.getSelectedIndex());
        if(employeeId!=0){
            liveData.getValue().setEmployeeId(employeeId);
        }
        liveData.getValue().setDeadline(dateAndTime.getTime().getTime());
        switch (taskType) {
            case 0: {
                tvm.updateTaskToProject();
                break;
            }
            case 1: {
                tvm.updateDevelopersTaskToProject();
                break;
            }
            case 2: {
                tvm.updateTestersTaskToProject();
                break;
            }
        }
        tvm.getEventTaskToProjectUpd().observe(getViewLifecycleOwner(),aBoolean -> {
            if(aBoolean) {
                Bundle bundle = new Bundle();
                bundle.putLong("E_TASK_ID", taskId);
                bundle.putLong("E_USER_ID",employeeId);
                bundle.putInt("E_TASK_TYPE",taskType);
                if(binding.spinner.length()!=0){
                    bundle.putInt("SPINNER_VAL", binding.spinner.getSelectedIndex());
                }
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_editTask_to_editTaskProjectsList, bundle, navBuilder.build());
                tvm.eventTaskToProjectUpdateFinished();
            }
        });
    }
}