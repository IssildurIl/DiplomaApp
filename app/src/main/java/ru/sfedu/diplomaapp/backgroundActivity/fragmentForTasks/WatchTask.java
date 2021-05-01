package ru.sfedu.diplomaapp.backgroundActivity.fragmentForTasks;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
        tvm.getEventTaskUpd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                tvm.task.observe(getViewLifecycleOwner(),task -> {
                    Bundle bundle = new Bundle();
                    bundle.putLong("projectId",task.getProjectId());
                    NavOptions.Builder navBuilder =  new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                    navController.navigate(R.id.action_watchTask_to_navFragment,bundle,navBuilder.build());
                    tvm.eventTaskUpdateFinished();
                });
            }
        });
        init(binding);
        buttons(binding);
        return binding.getRoot();
    }

    private void catchData(FragmentWatchTaskBinding binding) {
        try {
            Bundle getFromProjectList= this.getArguments();
            taskId = getFromProjectList.getLong("E_TASK_ID");
            tvm.getTask(taskId);
            tvm.task.observe(getViewLifecycleOwner(),task -> {
                binding.spinner.selectItemByIndex((int) task.getStatus());
            });
            if(projectId!=0){
                pvm.getProject(projectId);
            }
            if(employeeId!=0){
                evm.getEmployee(employeeId);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        colorBar();
        setInitialDateTime();
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
            if(binding.taskName.getText().toString().length()!=0){
                tvm.task.getValue().setTaskName(binding.taskName.getText().toString());
            }
            if(binding.taskDesc.getText().toString().length()!=0){
                tvm.task.getValue().setTaskDescription(binding.taskDesc.getText().toString());
            }
            tvm.task.getValue().setStatus(binding.spinner.getSelectedIndex());
            tvm.task.getValue().setDeadline(dateAndTime.getTime().getTime());
            tvm.updateTask();
        });
        mutedAddTime.setOnClickListener(v -> {
            setTime(v);
            setDate(v);
        });
    }

    public void setDate(View v) {
        new DatePickerDialog(this.getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    public void setTime(View v) {
        new TimePickerDialog(this.getContext(), t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    private void setInitialDateTime() {
        mutedAddTime.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }
    TimePickerDialog.OnTimeSetListener t= (view, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };
    DatePickerDialog.OnDateSetListener d= (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

}