package ru.sfedu.diplomaapp.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.R;

public class Reg extends Fragment {
    NavController navController;

    public Reg() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getActivity().findViewById(R.id.bottom_navigation_view_constraint).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.reg_btn).setOnClickListener(view1 -> navController.navigate(R.id.go_two_hellofragment));
        view.findViewById(R.id.return_to_auth).setOnClickListener(view1 -> navController.navigate(R.id.go_to_auth));
    }
}