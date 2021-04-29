package ru.sfedu.diplomaapp.mainlist.mainfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentHellofragmentBinding;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;

public class HelloAct extends Fragment {
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_ID= "SP_EMPLOYEE_ID";
    SharedPreferences mSettings;
    NavController navController;
    EmployeeViewModel evm;
    long employeeId;
    Bundle bundle = new Bundle();
    public HelloAct() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentHellofragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_hellofragment,container,false);
        binding.setLifecycleOwner(this);
        evm = new ViewModelProvider(this).get(EmployeeViewModel.class);
        binding.setEmployeeListViewModel(evm);
        shared();
        evm.employee.observe(getViewLifecycleOwner(),employee -> {
            String name = employee.getFirstName().split("\u0020")[0];
            String hellostr = String.format("Добрый день, %s",name);
            binding.descriptionView1.setText(hellostr);
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.fab).setOnClickListener(v -> {
            navController.navigate(R.id.action_hellofragment_to_createTask);
        });
    }

    private void shared() {
        Bundle catchbundle = getParentFragment().getArguments();
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_ID)) {
            employeeId=mSettings.getLong(APP_PREFERENCES_EMPLOYEE_ID,0);
        }
        try{
            employeeId = catchbundle.getLong("Auth_Employee_Id");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(employeeId!=0){
            evm.getEmployee(employeeId);
        }
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(APP_PREFERENCES_EMPLOYEE_ID, employeeId);
        editor.apply();
    }

}