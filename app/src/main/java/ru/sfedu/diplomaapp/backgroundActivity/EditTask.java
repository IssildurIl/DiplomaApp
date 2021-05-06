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

        taskId = getFrom.getLong("E_TASK_ID");

        tvm.getTask(taskId);
        try{
            projectId= getFrom.getLong("E_PROJECT_ID");
            employeeId = getFrom.getLong("E_USER_ID");
            tvm.task.observe(getViewLifecycleOwner(),task -> {
                binding.spinner.selectItemByIndex((int) task.getStatus());
                binding.addTime.setText(DateUtils.formatDateTime(getContext(),
                        task.getDeadline(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
                pvm.getProject(task.getProjectId());
                pvm.project.observe(getViewLifecycleOwner(),project -> {
                    binding.addProjectTo.setText(project.getTitle());
                });
                evm.getEmployee(task.getEmployeeId());
                evm.employee.observe(getViewLifecycleOwner(),employee -> {
                    binding.addEmployee.setText(employee.getFirstName());
                });
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        tvm.getEventTaskUpd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                tvm.task.observe(getViewLifecycleOwner(),task -> {
                    Bundle bundle = new Bundle();
                    bundle.putLong("projectId",task.getProjectId());
                    NavOptions.Builder navBuilder =  new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                    navController.navigate(R.id.action_editTask_to_editProject,bundle,navBuilder.build());
                    tvm.eventTaskUpdateFinished();
                });
            }
        });
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
            if(binding.taskName.getText().toString().length()!=0){
                tvm.task.getValue().setTaskName(binding.taskName.getText().toString());
            }
            if(binding.taskDesc.getText().toString().length()!=0){
                tvm.task.getValue().setTaskDescription(binding.taskDesc.getText().toString());
            }
            if(projectId!=null && projectId!=0){
                tvm.task.getValue().setProjectId(projectId);
            }
            if(employeeId!=null && employeeId!=0){
                tvm.task.getValue().setEmployeeId(employeeId);
            }
            tvm.task.getValue().setStatus(binding.spinner.getSelectedIndex());
            tvm.task.getValue().setDeadline(dateAndTime.getTime().getTime());
            tvm.updateTask();
        });
        mutedAddTime.setOnClickListener(v -> {
            setDate(v);
        });

        mutedAddProjectTo.setOnClickListener(v->{
            tvm.task.getValue().setTaskName(binding.taskName.getText().toString());
            tvm.task.getValue().setTaskDescription(binding.taskDesc.getText().toString());
            tvm.task.getValue().setStatus(binding.spinner.getSelectedIndex());
            if(employeeId!=0){
                tvm.task.getValue().setEmployeeId(employeeId);
            }
            tvm.task.getValue().setDeadline(dateAndTime.getTime().getTime());
            tvm.updateTaskToProject();
            tvm.getEventTaskToProjectUpd().observe(getViewLifecycleOwner(),aBoolean -> {
                if(aBoolean) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("E_TASK_ID", taskId);
                    bundle.putLong("E_USER_ID",employeeId);
                    NavOptions.Builder navBuilder = new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                    navController.navigate(R.id.action_editTask_to_editTaskProjectsList, bundle, navBuilder.build());
                    tvm.eventTaskToProjectUpdateFinished();
                }
            });

        });

        mutedAddEmployee.setOnClickListener(v->{
            tvm.task.getValue().setTaskName(binding.taskName.getText().toString());
            tvm.task.getValue().setTaskDescription(binding.taskDesc.getText().toString());
            tvm.task.getValue().setStatus(binding.spinner.getSelectedIndex());
            if(projectId!=0){
                tvm.task.getValue().setProjectId(projectId);
            }
            tvm.task.getValue().setDeadline(dateAndTime.getTime().getTime());
            tvm.updateTaskToEmployee();
            tvm.getEventTaskToEmployeeUpd().observe(getViewLifecycleOwner(), aBoolean -> {
                if(aBoolean){
                    Bundle bundle = new Bundle();
                    bundle.putLong("E_TASK_ID",taskId);
                    bundle.putLong("E_PROJECT_ID",employeeId);
                    NavHostFragment.findNavController(this).navigate(R.id.action_editTask_to_editTaskUserList,bundle);
                    tvm.eventTaskToEmployeeUpdateFinished();
                }
            });
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

}