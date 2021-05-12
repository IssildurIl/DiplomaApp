package ru.sfedu.diplomaapp.mainlist.mainfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectDiffCallback;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectsViewModel;
import ru.sfedu.diplomaapp.databinding.FragmentMyProjectBinding;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectItemAdapter;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TasksViewModel;
import ru.sfedu.diplomaapp.utils.otherUtils.RecyclerDecoration;

public class MyProject extends Fragment {
    NavController navController;
    ProjectsViewModel pvm;
    TasksViewModel tvm;
    Bundle bundle = new Bundle();

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
        tvm = new ViewModelProvider(this).get(TasksViewModel.class);
        binding.setProjectsListViewModel(pvm);
        ProjectItemAdapter pia = new ProjectItemAdapter(new ProjectDiffCallback(), project -> {
            pvm.onProjectItemClicked(project.get_id());
        },tvm);
        binding.recview.setAdapter(pia);
        pvm.getNavigateToProjectEdit().observe(getViewLifecycleOwner(), projectId -> {
            if(projectId!=null){
                bundle.putLong("projectId", projectId);
                NavHostFragment.findNavController(this).navigate(R.id.go_to_editProject,bundle);
                pvm.onProjectItemNavigated();
            }
        });
        pvm.projectList.observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                pia.submitList(projects);
            }
        });

        binding.goToPersonal.setOnClickListener(v -> {
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_navFragment_to_personalCabinet,null,navBuilder.build());
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
                alertDialog.dismiss();
            });
            v1.findViewById(R.id.back).setOnClickListener(v22 -> alertDialog.dismiss());
            alertDialog.show();
        });

    }




}
