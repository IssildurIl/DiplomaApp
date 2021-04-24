package ru.sfedu.diplomaapp.backgroundActivity;

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
    ImageButton returnto;
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

        try {
            Bundle getFromProjectList= this.getArguments();
            projectId = getFromProjectList.getLong("E_PROJECT_ID");
            employeeId =  getFromProjectList.getLong("E_EMPLOYEE_ID");
            taskId = getFromProjectList.getLong("TASK_ID");
            binding.taskName.setText(getFromProjectList.getString("E_TASK_NAME"));
            binding.taskDesc.setText(getFromProjectList.getString("E_TASK_DESCRIPTION"));
            binding.spinner.selectItemByIndex(getFromProjectList.getInt("E_SPINNER_VAL"));
            tvm.getTask(taskId);
            tvm.task.observe(getViewLifecycleOwner(),task -> {
                if(task!=null) {
                    binding.taskName.setText(task.getTaskName());
                    binding.taskDesc.setText(task.getTaskDescription());
                    pvm.getProject(task.getProjectId());
                    evm.getEmployee(task.getEmployeeId());
                }
            });
            pvm.getProject(projectId);
            evm.getEmployee(employeeId);
            binding.addEmployee.setText(getFromProjectList.getString("E_EMPLOYEE_NAME"));
            binding.addProjectTo.setText(getFromProjectList.getString("E_PROJECT_NAME"));
        }catch (Exception e) {
            e.printStackTrace();
        }


        tvm.getEventTaskUpd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_editTask_to_editProject);
                tvm.eventTaskUpdateFinished();
            }
        });


        init(binding);
        buttons(binding);
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
        returnto = binding.returnto;
    }


    protected void colorBar() {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this.getContext(), R.color.primary));
    }


    //нажатия
    protected void buttons(FragmentEditTaskBinding binding){
        returnto.setOnClickListener(v -> {
            if(binding.taskName.getText().toString().length()!=0){
                tvm.task.getValue().setTaskName(binding.taskName.getText().toString());
            }
            if(binding.taskDesc.getText().toString().length()!=0){
                tvm.task.getValue().setTaskDescription(binding.taskDesc.getText().toString());
            }
            if(projectId!=null){
                tvm.task.getValue().setProjectId(employeeId);
            }else{
                tvm.task.observe(getViewLifecycleOwner(),task -> {
                    if(task!=null) {
                        tvm.task.getValue().setProjectId(task.getProjectId());
                    }
                });
            }
            if(employeeId!=null){
                tvm.task.getValue().setEmployeeId(employeeId);
            }else{
                tvm.task.observe(getViewLifecycleOwner(),task -> {
                    if(task!=null) {
                        tvm.task.getValue().setEmployeeId(task.getEmployeeId());
                    }
                });
            }
            tvm.task.getValue().setStatus(binding.spinner.getSelectedIndex());
            tvm.task.getValue().setDeadline(dateAndTime.getTime().getTime());
            tvm.updateTask();
        });
        mutedAddTime.setOnClickListener(v -> {
            setTime(v);
            setDate(v);
        });
        mutedAddProjectTo.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            if(binding.taskName.getText().length()!=0){
                bundle.putString("E_TASK_NAME",binding.taskName.getText().toString());
            }
            if(binding.taskDesc.getText().length()!=0){
                bundle.putString("E_TASK_DESCRIPTION",binding.taskDesc.getText().toString());
            }
            if(binding.spinner.length()!=0){
                bundle.putInt("E_SPINNER_VAL",binding.spinner.getSelectedIndex());
            }
            if(employeeId!=0){
                bundle.putLong("E_EMPLOYEE_ID",employeeId);
            }
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_editTask_to_editTaskProjectsList,bundle);
        });
        mutedAddEmployee.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            if(binding.taskName.getText().length()!=0){
                bundle.putString("E_TASK_NAME",binding.taskName.getText().toString());
            }
            if(binding.taskDesc.getText().length()!=0){
                bundle.putString("E_TASK_DESCRIPTION",binding.taskDesc.getText().toString());
            }
            if(binding.spinner.length()!=0){
                bundle.putInt("E_SPINNER_VAL",binding.spinner.getSelectedIndex());
            }
            if(projectId!=0){
                bundle.putLong("E_PROJECT_ID",projectId);
            }
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_editTask_to_editTaskUserList,bundle);
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

    // отображаем диалоговое окно для выбора времени
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

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t= (view, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d= (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };
}