package ru.sfedu.diplomaapp.backgroundActivity.listsForEditTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentEditTaskUserListBinding;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeDiffCallback;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeItemAdapterForCreatingTask;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeesViewModel;


public class EditTaskUserList extends Fragment {
    EmployeesViewModel evm;
    Integer transactionSpinnerVal;
    String transactionTaskName,transactionTaskDescription;
    Long transactionProjectId;
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
        binding.setEmployeesListViewModel(evm);

        Bundle bundle = this.getArguments();
        Bundle sendBundle = new Bundle();
        try{
            transactionSpinnerVal = bundle.getInt("E_SPINNER_VAL");
            transactionTaskName = bundle.getString("E_TASK_NAME");
            transactionTaskDescription = bundle.getString("E_TASK_DESCRIPTION");
            transactionProjectId = bundle.getLong("E_PROJECT_ID");
            sendBundle.putString("E_TASK_NAME",transactionTaskName);
            sendBundle.putString("E_TASK_DESCRIPTION",transactionTaskDescription);
            sendBundle.getLong("E_PROJECT_ID",transactionProjectId);
            sendBundle.putInt("E_SPINNER_VAL",transactionSpinnerVal);
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
                sendBundle.putLong("E_EMPLOYEE_ID",userId);
                NavHostFragment.findNavController(this).navigate(R.id.action_editTaskUserList_to_editTask,sendBundle);
                evm.onEmployeeToTaskProjectItemNavigated();
            }
        });
        return binding.getRoot();
    }

}