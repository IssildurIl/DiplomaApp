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


public class UserList extends Fragment {
    EmployeesViewModel evm;

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
        binding.setProjectsListViewModel(evm);

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
                NavHostFragment.findNavController(this).navigate(UserListDirections.actionUserListToCreateTask(0,userId));
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