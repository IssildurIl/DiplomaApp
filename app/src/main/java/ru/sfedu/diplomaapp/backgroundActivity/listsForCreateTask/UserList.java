package ru.sfedu.diplomaapp.backgroundActivity.listsForCreateTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentUserListBinding;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeDiffCallback;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeItemAdapterForCreatingTask;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeesViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;


public class UserList extends Fragment {
    EmployeesViewModel evm;
    Integer transactionSpinnerVal;
    String transactionTaskName,transactionTaskDescription;
    Long transactionProjectId;
    TaskViewModel tvm;
    public UserList() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentUserListBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_list,container,false);
        binding.setLifecycleOwner(this);
        evm = new ViewModelProvider(this).get(EmployeesViewModel.class);
        binding.setEmployeesListViewModel(evm);

        Bundle bundle = this.getArguments();
        Bundle sendBundle = new Bundle();
        try{
            transactionSpinnerVal = bundle.getInt("SPINNER_VAL");
            transactionTaskName = bundle.getString("TASK_NAME");
            transactionTaskDescription = bundle.getString("TASK_DESCRIPTION");
            transactionProjectId = bundle.getLong("PROJECT_ID");
            sendBundle.putString("TASK_NAME",transactionTaskName);
            sendBundle.putString("TASK_DESCRIPTION",transactionTaskDescription);
            sendBundle.getLong("PROJECT_ID",transactionProjectId);
            sendBundle.putInt("SPINNER_VAL",transactionSpinnerVal);
        }catch (Exception e){
            e.printStackTrace();
        }

        EmployeeItemAdapterForCreatingTask eia = new EmployeeItemAdapterForCreatingTask(new EmployeeDiffCallback(),employee -> {
            evm.onEmployeeToTaskItemClicked(employee.get_id());
        });
        binding.recview.setAdapter(eia);

        evm.employeeList.observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                eia.submitList(projects);
            }
        });

        evm.getNavigateEmployeeToTask().observe(getViewLifecycleOwner(), userId->{
            if(userId!=null) {
                sendBundle.putLong("EMPLOYEE_ID",userId);
                NavHostFragment.findNavController(this).navigate(R.id.action_userList_to_createTask,sendBundle);
                evm.onEmployeeToTaskProjectItemNavigated();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}