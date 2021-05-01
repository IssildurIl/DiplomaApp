package ru.sfedu.diplomaapp.auth;

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
import android.widget.EditText;
import android.widget.Toast;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentRegBinding;

import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeeViewModel;

public class Reg extends Fragment {
    NavController navController;
    private EmployeeViewModel evm;

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
        binding.regBtn.setOnClickListener(v -> {
            if(!check(binding)){
                return;
            }
            evm.insertEmployee(new Employee(binding.nameFieldTxt.getText().toString(), binding.passwordHintTxt.getText().toString(),
                    binding.mailFieldTxt.getText().toString()));
            evm.getEmployeeByEmail(binding.mailFieldTxt.getText().toString(),binding.passwordHintTxt.getText().toString());
            evm.employeeByEmail.observe(getViewLifecycleOwner(),employee -> {
                if(employee!=null){
                long employeeId = employee.get_id();
                Bundle bundle = new Bundle();
                bundle.putLong("Auth_Employee_Id",employeeId);
                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_reg_to_navFragment,bundle,navBuilder.build());
                }
            });
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
        return nullCheck(binding.nameFieldTxt) && nullCheck(binding.mailFieldTxt) && isEmailCheck(binding.mailFieldTxt)
                && nullCheck(binding.mailRepeatFieldTxt) && isEquals(binding.mailFieldTxt, binding.mailRepeatFieldTxt)
                && nullCheck(binding.passwordHintTxt) && nullCheck(binding.passwordRepeatHintTxt)
                && isEquals(binding.passwordHintTxt, binding.passwordRepeatHintTxt);
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
    private boolean isEquals(EditText et,EditText et1){
        if(!et.getText().toString().equals(et1.getText().toString())){
            Toast.makeText(getContext(),"Поля не совпадают",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}