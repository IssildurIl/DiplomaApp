package ru.sfedu.diplomaapp.auth;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.sfedu.diplomaapp.AuthorisationController;
import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentRegBinding;

import ru.sfedu.diplomaapp.models.Developer;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Tester;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;

public class Reg extends Fragment {
    NavController navController;
    private EmployeeViewModel evm;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://young-ocean-61535.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create()).build();
    AuthorisationController controller = retrofit.create(AuthorisationController.class);

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_ID= "SP_EMPLOYEE_ID";
    public static final String APP_PREFERENCES_EMPLOYEE_TYPE= "SP_EMPLOYEE_TYPE";
    public static final String APP_PREFERENCES_EMPLOYEE_EMAIL= "SP_EMPLOYEE_EMAIL";
    public static final String APP_PREFERENCES_EMPLOYEE_PASSWORD= "SP_EMPLOYEE_PASSWORD";
    SharedPreferences mSettings;


    public Reg() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRegBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_reg,container,false);
        binding.setLifecycleOwner(this);
        evm = new ViewModelProvider(this).get(EmployeeViewModel.class);
        binding.setEmployeeListViewModel(evm);
        evm.getEventEmployeeAdd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                evm.eventEmployeeAddFinished();
            }
        });
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        binding.regBtn.setOnClickListener(v -> {
            if(!check(binding)){
                return;
            }
            switch (binding.spinner.getSelectedIndex()){
                case 0:
                    employeeEnter(binding);
                    break;
                case 1:
                    developerEnter(binding);
                    break;
                case 2:
                    testerEnter(binding);
                    break;
                default: break;
            }

//            Call<Boolean> call = controller.addEmployee(new Employee(binding.nameFieldTxt.getText().toString(), binding.passwordHintTxt.getText().toString(),
//                    binding.mailFieldTxt.getText().toString()));
//            call.enqueue(new Callback<Boolean>() {
//                @Override
//                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                    Log.d("Success", String.valueOf(response.body()));
//                }
//
//                @Override
//                public void onFailure(Call<Boolean> call, Throwable t) {
//                    Log.d("Err", t.getMessage());
//                }
//            });


        });
        return binding.getRoot();
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.return_to_auth).setOnClickListener(view1 -> {
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.go_to_auth,null,navBuilder.build());});
    }


    private boolean check(ru.sfedu.diplomaapp.databinding.FragmentRegBinding binding) {
        return nullCheck(binding.nameFieldTxt) && isEmailCheck(binding.mailFieldTxt)
                && nullCheck(binding.passwordHintTxt);

    }

    public boolean isEmail(String s) {
        return s.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
    }
    private boolean nullCheck(EditText et){
        if(et.getText().toString().length()==0){
            et.setError("Пустое поле");
            return false;
        }
        return true;
    }
    private boolean isEmailCheck(EditText et){
        if(!isEmail(et.getText().toString())){
            et.setError("Ошибка ввода");
            return false;
        }
        return true;
    }
    private void testerEnter(FragmentRegBinding binding) {
        evm.insertTester(new Tester(binding.nameFieldTxt.getText().toString(),
                binding.passwordHintTxt.getText().toString(),
                binding.mailFieldTxt.getText().toString(),2));
        evm.getTesterByEmail(binding.mailFieldTxt.getText().toString(), binding.passwordHintTxt.getText().toString());
        evm.testerByEmail.observe(getViewLifecycleOwner(),tester-> {
            if(tester!=null){
                long employeeId = tester.get_id();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putLong(APP_PREFERENCES_EMPLOYEE_ID, employeeId);
                editor.putInt(APP_PREFERENCES_EMPLOYEE_TYPE,2);
                editor.apply();

                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_reg_to_navFragment,null,navBuilder.build());
            }
        });
    }

    private void developerEnter(FragmentRegBinding binding) {
        evm.insertDeveloper(new Developer(binding.nameFieldTxt.getText().toString(),
                binding.passwordHintTxt.getText().toString(),
                binding.mailFieldTxt.getText().toString(),1));
        evm.getDeveloperByEmail(binding.mailFieldTxt.getText().toString(), binding.passwordHintTxt.getText().toString());
        evm.developerByEmail.observe(getViewLifecycleOwner(),developer -> {
            if(developer!=null){
                long employeeId = developer.get_id();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putLong(APP_PREFERENCES_EMPLOYEE_ID, employeeId);
                editor.putInt(APP_PREFERENCES_EMPLOYEE_TYPE,1);
                editor.apply();
                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_reg_to_navFragment,null,navBuilder.build());
            }
        });
    }

    private void employeeEnter(FragmentRegBinding binding) {
        evm.insertEmployee(new Employee(binding.nameFieldTxt.getText().toString(),
                binding.passwordHintTxt.getText().toString(),
                binding.mailFieldTxt.getText().toString(),0));
        evm.getEmployeeByEmail(binding.mailFieldTxt.getText().toString(), binding.passwordHintTxt.getText().toString());
        evm.employeeByEmail.observe(getViewLifecycleOwner(),employee -> {
            if(employee!=null){
                long employeeId = employee.get_id();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putLong(APP_PREFERENCES_EMPLOYEE_ID, employeeId);
                editor.putInt(APP_PREFERENCES_EMPLOYEE_TYPE,0);
                editor.apply();
                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_reg_to_navFragment,null,navBuilder.build());
            }
        });
    }


}