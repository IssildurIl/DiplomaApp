package ru.sfedu.diplomaapp.backgroundActivity.listsForEditTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentEditTaskUserListBinding;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeDiffCallback;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeItemAdapterForCreatingTask;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeesViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;


public class EditTaskUserList extends Fragment {

    EmployeesViewModel evm;
    Integer transactionSpinnerVal,taskType;
    Long transactionTaskId, transactionProjectId;
    public EditTaskUserList() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditTaskUserListBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_task_user_list,container,false);
        binding.setLifecycleOwner(this);
        evm = new ViewModelProvider(this).get(EmployeesViewModel.class);
        Bundle bundle = this.getArguments();
        Bundle sendBundle = new Bundle();
        try{
            transactionSpinnerVal = bundle.getInt("SPINNER_VAL");
            transactionTaskId = bundle.getLong("E_TASK_ID");
            transactionProjectId = bundle.getLong("E_PROJECT_ID");
            taskType =  bundle.getInt("E_TASK_TYPE");
            sendBundle.putInt("E_TASK_TYPE",taskType);
            sendBundle.putLong("E_TASK_ID",transactionTaskId);
            sendBundle.putLong("E_PROJECT_ID",transactionProjectId);
            sendBundle.putInt("SPINNER_VAL",transactionSpinnerVal);

        }catch(Exception e){
            e.printStackTrace();
        }


        binding.setEmployeesListViewModel(evm);

        EmployeeItemAdapterForCreatingTask eia = new EmployeeItemAdapterForCreatingTask(new EmployeeDiffCallback(),employee -> {
            evm.onEmployeeToTaskItemClicked(employee.get_id());
        });
        binding.recview.setAdapter(eia);

        switch (taskType){
            case 0: {
                evm.employeeList.observe(getViewLifecycleOwner(), employees -> {
                    if (employees != null) {
                        eia.submitList(employees);
                    }
                });
                break;
            }
            case 1:{
                evm.developerList.observe(getViewLifecycleOwner(), developers -> {
                    if (developers != null) {
                        eia.submitList(developers);
                    }
                });
                break;
            }
            case 2:{
                evm.testerList.observe(getViewLifecycleOwner(), testers -> {
                    if (testers != null) {
                        eia.submitList(testers);
                    }
                });
                break;
            }
        }

        evm.getNavigateEmployeeToTask().observe(getViewLifecycleOwner(),employeeId->{
            if(employeeId!=null) {
                sendBundle.putLong("E_USER_ID",employeeId);
                NavHostFragment.findNavController(this).navigate(R.id.action_editTaskUserList_to_editTask,sendBundle);
                evm.onEmployeeToTaskProjectItemNavigated();
            }
        });

        return binding.getRoot();
    }

}