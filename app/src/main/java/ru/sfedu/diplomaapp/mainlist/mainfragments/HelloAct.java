package ru.sfedu.diplomaapp.mainlist.mainfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
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
import android.widget.ArrayAdapter;

import java.util.List;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentHellofragmentBinding;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;

public class HelloAct extends Fragment {
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_ID= "SP_EMPLOYEE_ID";
    public static final String APP_PREFERENCES_EMPLOYEE_TYPE= "SP_EMPLOYEE_TYPE";
    SharedPreferences mSettings;
    NavController navController;
    EmployeeViewModel evm;
    long employeeIdFromSp;
    int employeeTypeFromSp;
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
        shared(binding);
        binding.goToPersonal.setOnClickListener(v -> {
            RippleDrawable rippledImage = new
                    RippleDrawable(ColorStateList.valueOf(Color.BLACK),
                    binding.goToPersonal.getDrawable(), null);
            binding.goToPersonal.setImageDrawable(rippledImage);
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_navFragment_to_personalCabinet,null,navBuilder.build());
        });
        String[] motivation = getResources().getStringArray(R.array.helloActScene);
        int a = (int) (Math.random()*(motivation.length));
        binding.motivation.setText(motivation[a]);
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

    private void shared(FragmentHellofragmentBinding binding) {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_ID)) {
            employeeIdFromSp=mSettings.getLong(APP_PREFERENCES_EMPLOYEE_ID,3);
        }
        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_TYPE)) {
            employeeTypeFromSp=mSettings.getInt(APP_PREFERENCES_EMPLOYEE_TYPE,3);
        }
        methodGetEmployee(binding,employeeIdFromSp,employeeTypeFromSp);

    }

    private void methodGetEmployee(FragmentHellofragmentBinding binding, long employeeIdFromSp, int status) {
        switch (status) {
            case 0:
                evm.getEmployee(employeeIdFromSp);
                evm.employee.observe(getViewLifecycleOwner(),employee -> {
                    String name = employee.getFirstName().split("\u0020")[0];
                    String hellostr = String.format("Добрый день, %s",name);
                    binding.descriptionView1.setText(hellostr);
                });
                break;
            case 1:
                evm.getDeveloper(employeeIdFromSp);
                evm.developer.observe(getViewLifecycleOwner(),developer -> {
                    String name = developer.getFirstName().split("\u0020")[0];
                    String hellostr = String.format("Добрый день, %s",name);
                    binding.descriptionView1.setText(hellostr);
                });
                break;
            case 2:
                evm.getTester(employeeIdFromSp);
                evm.tester.observe(getViewLifecycleOwner(),tester -> {
                    String name = tester.getFirstName().split("\u0020")[0];
                    String hellostr = String.format("Добрый день, %s",name);
                    binding.descriptionView1.setText(hellostr);
                });
                break;
            default:
                break;
        }
    }


}