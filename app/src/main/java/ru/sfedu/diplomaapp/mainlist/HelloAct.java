package ru.sfedu.diplomaapp.mainlist;

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

public class HelloAct extends Fragment {

    NavController navController;

    public HelloAct() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hellofragment, container, false);
        getActivity().findViewById(R.id.navbar).setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getActivity().findViewById(R.id.navbar).setVisibility(View.VISIBLE);
        view.findViewById(R.id.fab).setOnClickListener(v -> {
            navController.navigate(R.id.action_hellofragment_to_createTask);
            getActivity().findViewById(R.id.navbar).setVisibility(View.INVISIBLE);
        });
    }

}