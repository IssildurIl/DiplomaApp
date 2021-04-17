package ru.sfedu.diplomaapp.backgroundActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentEditProjectBinding;
import ru.sfedu.diplomaapp.utils.forprojects.ProjectViewModel;


public class EditProject extends Fragment {

    private ProjectViewModel pvm;
    private FragmentEditProjectBinding binding;

    public EditProject() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_project, container, false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectViewModel.class);
        pvm.getProject(EditProjectArgs.fromBundle(getArguments()).getProjectId());
        binding.setProjectViewModel(pvm);


        pvm.getEventProjectUpd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                pvm.eventProjectUpdateFinished();
                NavHostFragment.findNavController(this).navigate(R.id.action_editProject_to_myProject);
            }
        });

        binding.editProjectButton.setOnClickListener(v -> {
            pvm.project.getValue().setTitle(binding.projectName.getText().toString());
            pvm.updateProject();
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}