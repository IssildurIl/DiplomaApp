package ru.sfedu.diplomaapp.Fragment;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.enums.TypeOfEmployee;

public class Registration extends Fragment {
    private final static String TAG = "Registration";
    private final static int createAccount_id = R.id.reg_btn;
    private final static int linkLogin_id = R.id.return_to_auth;
    Button createAccount_btn,linkLogin_btn;

    /* Текстовые поля */
    private final static int nameFieldTxt = R.id.nameFieldTxt;
    private final static int lastNameFieldTxt = R.id.lastNameFieldTxt;
    private final static int logHintTxt = R.id.logHintTxt;
    private final static int emailHintTxt = R.id.emailHintTxt;
    private final static int passwordHintTxt = R.id.passwordHintTxt;
    public EditText nameField, lastNameField, logHint, emailHint,passwordHint;
    public TypeOfEmployee typeofEmployee;

    public Registration() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        init(view);
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        List<TypeOfEmployee> enums = Arrays.asList(TypeOfEmployee.values());
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<TypeOfEmployee> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,enums);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeofEmployee=((TypeOfEmployee)parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        view.findViewById(R.id.reg_btn).setOnClickListener(view1 -> {
            register();
            navController.navigate(R.id.go_to_chars);
        });
        view.findViewById(R.id.return_to_auth).setOnClickListener(view12 -> navController.navigate(R.id.action_registration_to_authorization2));
    }

    protected void init(View view){
        createAccount_btn = (Button) view.findViewById(createAccount_id);
        linkLogin_btn = (Button) view.findViewById(linkLogin_id);

        nameField = (EditText) view.findViewById(nameFieldTxt);
        lastNameField = (EditText) view.findViewById(lastNameFieldTxt);
        logHint = (EditText) view.findViewById(logHintTxt);
        emailHint = (EditText) view.findViewById(emailHintTxt);
        passwordHint = (EditText) view.findViewById(passwordHintTxt);
    }

    protected void register() {
        Log.d(TAG, "start register");
        Employee employee = new Employee();
        employee = employee.iniEmployee(
                nameField.getText().toString(),
                lastNameField.getText().toString(),
                logHint.getText().toString(),
                emailHint.getText().toString(),
                passwordHint.getText().toString(),
                typeofEmployee);
        Toast.makeText(getContext(), employee.toString(), Toast.LENGTH_SHORT).show();
        Log.d(TAG,employee.toString());
       // Log.d(TAG, String.format("Entered data: Name: %s, Email: %s, Psw: %s, PswRe: %s", email, email_re, psw, psw_re));


    }
}