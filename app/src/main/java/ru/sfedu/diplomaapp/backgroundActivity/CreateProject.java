package ru.sfedu.diplomaapp.backgroundActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentCreateProjectBinding;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectViewModel;


public class CreateProject extends Fragment {
    ProjectViewModel pvm;
    public CreateProject() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCreateProjectBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_project,container,false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectViewModel.class);
        pvm.getEventProjectAdd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                pvm.eventProjectAddFinished();
            }
        });

        binding.createProjectButton.setOnClickListener(v -> {
            pvm.insertProject(new Project(binding.projectName.getText().toString()));
            requireActivity().onBackPressed();
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}