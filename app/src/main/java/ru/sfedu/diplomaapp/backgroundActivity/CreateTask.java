package ru.sfedu.diplomaapp.backgroundActivity;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import java.util.Calendar;
import java.util.Date;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentCreateTaskBinding;
import ru.sfedu.diplomaapp.models.DevelopersTask;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.models.TestersTask;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;

public class CreateTask extends Fragment {

    TaskViewModel tvm;

    Calendar dateAndTime= Calendar.getInstance();
    NavController navController;
    ProjectViewModel pvm;
    EmployeeViewModel evm;
    Long projectId,employeeId;
    EditText mutedAddTime;
    public CreateTask() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCreateTaskBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_task,container,false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectViewModel.class);
        evm = new ViewModelProvider(this).get(EmployeeViewModel.class);
        tvm = new ViewModelProvider(this).get(TaskViewModel.class);
        try {
            Bundle getFromProjectList= this.getArguments();
            projectId = getFromProjectList.getLong("PROJECT_ID");
            employeeId =  getFromProjectList.getLong("EMPLOYEE_ID");
            binding.taskName.setText(getFromProjectList.getString("TASK_NAME"));
            binding.taskDesc.setText(getFromProjectList.getString("TASK_DESCRIPTION"));
            binding.spinner.selectItemByIndex(getFromProjectList.getInt("SPINNER_VAL"));
            if(employeeId!=0) {
                switch (getFromProjectList.getInt("SPINNER_VAL")) {
                    case 0: {
                        evm.getEmployee(employeeId);
                        evm.employee.observe(getViewLifecycleOwner(), employee -> {
                            binding.addEmployee.setText(employee.getFirstName());
                        });
                        break;
                    }
                    case 1: {
                        evm.getDeveloper(employeeId);
                        evm.developer.observe(getViewLifecycleOwner(), developer -> {
                            binding.addEmployee.setText(developer.getFirstName());
                        });
                        break;
                    }
                    case 2: {
                        evm.getTester(employeeId);
                        evm.tester.observe(getViewLifecycleOwner(), tester -> {
                            binding.addEmployee.setText(tester.getFirstName());
                        });
                        break;
                    }
                }
            }
            pvm.getProject(projectId);
            binding.addProjectTo.setText(getFromProjectList.getString("PROJECT_NAME"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_createTask_to_navFragment,null,navBuilder.build());
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        mutedAddTime = binding.addTime;

        binding.setProjectViewModel(pvm);
        binding.setEmployeeViewModel(evm);
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

    protected void colorBar() {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this.getContext(), R.color.primary));
    }


    //нажатия
    protected void buttons(ru.sfedu.diplomaapp.databinding.FragmentCreateTaskBinding binding){
        binding.returnto.setOnClickListener(v -> {
            RippleDrawable rippledImage = new
                    RippleDrawable(ColorStateList.valueOf(Color.BLACK),
                    binding.returnto.getDrawable(), null);
            binding.returnto.setImageDrawable(rippledImage);
            if(binding.taskName.getText().length() == 0){
                binding.taskName.setError("Назовите задачу");
                return;
            }
            if(binding.addEmployee.getText().length() == 0){
                binding.addEmployee.setError("Выберите исполнителя");
                return;
            }
            if(binding.addProjectTo.getText().length() == 0){
                 binding.addProjectTo.setError("Выберите проект");
                 return;
            }
            switch (binding.spinner.getSelectedIndex()){
                case 0:
                    tvm.insertTask(new Task(binding.taskName.getText().toString(),binding.taskDesc.getText().toString(),
                            employeeId,projectId,0,new Date().getTime(),dateAndTime.getTime().getTime()));
                case 1:
                    tvm.insertDevelopersTask(new DevelopersTask(binding.taskName.getText().toString(),binding.taskDesc.getText().toString(),
                            employeeId,projectId,0,new Date().getTime(),dateAndTime.getTime().getTime()));
                case 2:
                    tvm.insertTestersTask(new TestersTask(binding.taskName.getText().toString(),binding.taskDesc.getText().toString(),
                            employeeId,projectId,0,new Date().getTime(),dateAndTime.getTime().getTime()));
            }

            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_createTask_to_navFragment,null,navBuilder.build());
        });
        binding.addTime.setOnClickListener(v -> {
            setDate(v);
        });
        binding.addProjectTo.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            if(binding.taskName.getText().length()!=0){
                bundle.putString("TASK_NAME",binding.taskName.getText().toString());
            }
             if(binding.taskDesc.getText().length()!=0){
                 bundle.putString("TASK_DESCRIPTION",binding.taskDesc.getText().toString());
             }
            if(binding.spinner.length()!=0){
                bundle.putInt("SPINNER_VAL",binding.spinner.getSelectedIndex());
            }
            if(employeeId!=0){
                bundle.putLong("EMPLOYEE_ID",employeeId);
            }
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_createTask_to_projectList,bundle);
        });
        binding.addEmployee.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            if(binding.taskName.getText().length()!=0){
                bundle.putString("TASK_NAME",binding.taskName.getText().toString());
            }
            if(binding.taskDesc.getText().length()!=0){
                bundle.putString("TASK_DESCRIPTION",binding.taskDesc.getText().toString());
            }
            if(binding.spinner.length()!=0){
                bundle.putInt("SPINNER_VAL",binding.spinner.getSelectedIndex());
            }
            if(projectId!=0){
                bundle.putLong("PROJECT_ID",projectId);
            }
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_createTask_to_userList,bundle);
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