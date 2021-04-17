package ru.sfedu.diplomaapp.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.TestOnly;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.EmployeeDao;
import ru.sfedu.diplomaapp.databinding.FragmentAuthBinding;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.utils.forRegistration.EmployeeViewModel;
import ru.sfedu.diplomaapp.utils.forRegistration.EmployeesViewModel;

public class Auth extends Fragment {
    NavController navController;
    EmployeeViewModel evm;
    String pwrd="",email="";
    public Auth() {

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

        binding.validBtn.setOnClickListener(v -> {
            if(!nullCheck(binding.mailFieldTxt) && !nullCheck(binding.passwordFieldTxt)){
                return;
            }
            evm.getEmployeeByEmail(binding.mailFieldTxt.getText().toString(), binding.passwordFieldTxt.getText().toString());
            evm.employeeByEmail.observe(getViewLifecycleOwner(),employee -> {
                if(employee!=null){
                    email = employee.getEmail();
                    pwrd = employee.getPassword();
                }
                if(email.length() == 0){
                    binding.mailFieldTxt.setError("Такой пользователь не зарегестрирован");
                    return;
                }
            if(isEquals(pwrd,binding.passwordFieldTxt.getText().toString())){
                 navController.navigate(R.id.go_to_hellofragment);
            }
            });
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getActivity().findViewById(R.id.navbar).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.reg_btn).setOnClickListener(view1 -> navController.navigate(R.id.go_to_reg));
    }

    public boolean isEquals(String pass, String passFromDB){
        return pass.equals(passFromDB);
    }
    private boolean nullCheck(EditText et){
        if(et.getText().toString().length()==0){
            et.setError("Пустое поле");
            return false;
        }
        return true;
    }
}