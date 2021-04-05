package ru.sfedu.diplomaapp.mainlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.backgroundActivity.CreateTask;
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getActivity().findViewById(R.id.bottom_navigation_view_constraint).setVisibility(View.VISIBLE);
        view.findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent=new Intent(getContext(), CreateTask.class);
            startActivity(intent);
        });
    }

}