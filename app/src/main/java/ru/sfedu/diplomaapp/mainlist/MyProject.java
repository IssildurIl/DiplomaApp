package ru.sfedu.diplomaapp.mainlist;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.utils.forprojects.ProjectDiffCallback;
import ru.sfedu.diplomaapp.utils.forprojects.ProjectsViewModel;
import ru.sfedu.diplomaapp.databinding.FragmentMyProjectBinding;
import ru.sfedu.diplomaapp.utils.forprojects.ProjectItemAdapter;

public class MyProject extends Fragment {
    NavController navController;
    ProjectsViewModel pvm;
    public MyProject() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMyProjectBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_project, container, false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectsViewModel.class);
        binding.setProjectsListViewModel(pvm);

        ProjectItemAdapter pia = new ProjectItemAdapter(new ProjectDiffCallback(), project -> {
            pvm.onProjectItemClicked(project.get_id());
        });
        binding.recview.setAdapter(pia);

        pvm.getNavigateToProjectEdit().observe(getViewLifecycleOwner(), projectId -> {
            if(projectId!=null){
                NavHostFragment.findNavController(this).navigate(MyProjectDirections.goToEditProject(projectId));
                pvm.onProjectItemNavigated();
            }
        });

        pvm.projectList.observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                pia.submitList(projects);
            }
        });
        return binding.getRoot();


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.fab).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            AlertDialog alertDialog = builder.create();
            View v1 = view.inflate(getContext(),R.layout.alertdialog,null);
            alertDialog.setView(v1);
            v1.findViewById(R.id.addProject).setOnClickListener(v2 -> {
                navController.navigate(R.id.action_myProject_to_createProject);
                getActivity().findViewById(R.id.navbar).setVisibility(View.INVISIBLE);
            });
            v1.findViewById(R.id.back).setOnClickListener(v22 -> alertDialog.dismiss());
            alertDialog.show();
        });
    }

}
