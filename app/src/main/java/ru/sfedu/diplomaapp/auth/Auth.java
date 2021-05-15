package ru.sfedu.diplomaapp.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentAuthBinding;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Tester;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;

public class Auth extends Fragment {
    NavController navController;
    EmployeeViewModel evm;
    String password,email;
    long employeeId,employeeIdFromSp;
    int employeeTypeFromSp;
    int observeCount =0;
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_ID= "SP_EMPLOYEE_ID";
    public static final String APP_PREFERENCES_EMPLOYEE_TYPE= "SP_EMPLOYEE_TYPE";
    public static final String APP_PREFERENCES_EMPLOYEE_EMAIL= "SP_EMPLOYEE_EMAIL";
    public static final String APP_PREFERENCES_EMPLOYEE_PASSWORD= "SP_EMPLOYEE_PASSWORD";
    SharedPreferences mSettings;

    public Auth() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_EMAIL) && mSettings.contains(APP_PREFERENCES_EMPLOYEE_PASSWORD)){
            email=mSettings.getString(APP_PREFERENCES_EMPLOYEE_EMAIL,"");
            password=mSettings.getString(APP_PREFERENCES_EMPLOYEE_PASSWORD,"");
            if(email.length()!=0 && password.length()!=0) {
                switch (employeeTypeFromSp) {
                    case 0: {
                        evm.getEmployeeByEmail(email, password);
                        empPreSearch(evm.employeeByEmail);
                    }
                    case 1: {
                        evm.getDeveloperByEmail(email, password);
                        empPreSearch(evm.developerByEmail);
                    }
                    case 2: {
                        evm.getTesterByEmail(email, password);
                        empPreSearch(evm.testerByEmail);
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAuthBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_auth, container,false);
        binding.setLifecycleOwner(this);
        evm = new ViewModelProvider(this).get(EmployeeViewModel.class);
        binding.setEmployeeViewModel(evm);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        OnBackPressedCallback callback = new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);


        binding.validBtn.setOnClickListener(v -> {
            if(nullCheck(binding.mailFieldTxt) && nullCheck(binding.passwordFieldTxt)) {
                return;
            }
            try {
                evm.getEmployeeByEmail(binding.mailFieldTxt.getText().toString(), binding.passwordFieldTxt.getText().toString());
                evm.getDeveloperByEmail(binding.mailFieldTxt.getText().toString(), binding.passwordFieldTxt.getText().toString());
                evm.getTesterByEmail(binding.mailFieldTxt.getText().toString(), binding.passwordFieldTxt.getText().toString());
            }catch (Exception e){}
            emlpoyeeSearch(binding,evm.employeeByEmail,0);
            emlpoyeeSearch(binding,evm.developerByEmail, 1);
            emlpoyeeSearch(binding,evm.testerByEmail,2);
        });
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.reg_btn).setOnClickListener(view1 -> {
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.go_to_reg,null,navBuilder.build());});
    }

    public boolean isEquals(String pass, String passFromDB){
        return pass.equals(passFromDB);
    }
    private boolean nullCheck(EditText et){
        if(et.getText().toString().length()==0){
            et.setError("Пустое поле");
            return true;
        }
        return false;
    }

    private void emlpoyeeSearch(FragmentAuthBinding binding,LiveData<? extends Employee> empByEmail, int type) {
        empByEmail.observe(getViewLifecycleOwner(), employee -> {
            if (employee != null) {
                employeeId = employee.get_id();
                password = employee.getPassword();
                email = employee.getEmail();
                if (isEquals(password, binding.passwordFieldTxt.getText().toString())) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putLong(APP_PREFERENCES_EMPLOYEE_ID, employeeId);
                    editor.putInt(APP_PREFERENCES_EMPLOYEE_TYPE,type);
                    editor.putString(APP_PREFERENCES_EMPLOYEE_EMAIL,email);
                    editor.putString(APP_PREFERENCES_EMPLOYEE_PASSWORD,password);
                    editor.apply();
                }
            }else{
                binding.mailFieldTxt.setError("Такой пользователь не зарегестрирован");
                return;
            }
            if(employeeId!=0 && observeCount == 0){
                observeCount +=1;
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_auth_to_navFragment, null, navBuilder.build());
            }
        });
    }
    
    private void empPreSearch( LiveData<? extends Employee> empByEmail) {
        empByEmail.observe(getViewLifecycleOwner(),tester -> {
            if(tester!=null){
                employeeId = tester.get_id();
            }
            if(employeeId!=0 && observeCount == 0){
                observeCount +=1;
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_auth_to_navFragment, null, navBuilder.build());
            }
        });
    }


}