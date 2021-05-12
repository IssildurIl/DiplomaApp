package ru.sfedu.diplomaapp.mainlist;

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

import org.jetbrains.annotations.NotNull;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentHellofragmentBinding;
import ru.sfedu.diplomaapp.databinding.FragmentPersonalCabinetBinding;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;


public class PersonalCabinet extends Fragment {
    EmployeeViewModel evm;
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_ID= "SP_EMPLOYEE_ID";
    public static final String APP_PREFERENCES_EMPLOYEE_TYPE= "SP_EMPLOYEE_TYPE";
    SharedPreferences mSettings;
    long employeeIdFromSp;
    int employeeTypeFromSp;
    NavController navController;
    public PersonalCabinet() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPersonalCabinetBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_personal_cabinet,container,false);
        binding.setLifecycleOwner(this);
        evm = new ViewModelProvider(this).get(EmployeeViewModel.class);
        binding.setEmployeeViewModel(evm);
        shared(binding);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        binding.radioMale.setOnClickListener(v -> binding.imageView.setImageResource(R.drawable.avatar_man));
        binding.radioFemale.setOnClickListener(v -> binding.imageView.setImageResource(R.drawable.avatar_woman));
        binding.logOutBtn.setOnClickListener(v->{
            SharedPreferences.Editor editor = mSettings.edit();
            editor.remove(APP_PREFERENCES_EMPLOYEE_ID);
            editor.remove(APP_PREFERENCES_EMPLOYEE_TYPE);
            editor.apply();

            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_personalCabinet_to_auth,null,navBuilder.build());

        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void shared(FragmentPersonalCabinetBinding binding) {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (mSettings.contains(APP_PREFERENCES_EMPLOYEE_ID)) {
            employeeIdFromSp = mSettings.getLong(APP_PREFERENCES_EMPLOYEE_ID, 0);
        }
        if (mSettings.contains(APP_PREFERENCES_EMPLOYEE_TYPE)) {
            employeeTypeFromSp = mSettings.getInt(APP_PREFERENCES_EMPLOYEE_TYPE, 0);
        }
        methodGetEmployee(binding,employeeIdFromSp,employeeTypeFromSp);
    }

    private void methodGetEmployee(FragmentPersonalCabinetBinding binding, long employeeIdFromSp, int status) {
        switch (status) {
            case 0:
                evm.getEmployee(employeeIdFromSp);
                evm.employee.observe(getViewLifecycleOwner(),employee -> {
                    binding.userName.setText(employee.getFirstName());
                    binding.userProfession.setText("Employee");
                });
                break;
            case 1:
                evm.getDeveloper(employeeIdFromSp);
                evm.developer.observe(getViewLifecycleOwner(),developer -> {
                    binding.userName.setText(developer.getFirstName());
                    binding.userProfession.setText("Developer");
                });
                break;
            case 2:
                evm.getTester(employeeIdFromSp);
                evm.tester.observe(getViewLifecycleOwner(),tester -> {
                    binding.userName.setText(tester.getFirstName());
                    binding.userProfession.setText("Tester");
                });
                break;
            default:
                break;
        }
    }
}