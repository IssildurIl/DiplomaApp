package ru.sfedu.diplomaapp.backgroundActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.backgroundActivity.kanban.KanbanDoingTask;
import ru.sfedu.diplomaapp.backgroundActivity.kanban.KanbanEndTask;
import ru.sfedu.diplomaapp.backgroundActivity.kanban.KanbanStartTask;
import ru.sfedu.diplomaapp.databinding.FragmentEditProjectBinding;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectViewModel;


public class EditProject extends Fragment {
    private ProjectViewModel pvm;
    private FragmentEditProjectBinding binding;
    long projectId;
    int count = 1;
    Bundle bundle = new Bundle();
    public EditProject() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle getbundle = getArguments();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_project, container, false);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectViewModel.class);
        projectId = getbundle.getLong("projectId");
        pvm.getProject(projectId);
        binding.setProjectViewModel(pvm);

        pvm.getEventProjectUpd().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                bundle.putInt("item", 1);
                NavHostFragment.findNavController(this).navigate(R.id.action_editProject_to_navFragment,bundle);
                pvm.eventProjectUpdateFinished();
            }
        });
        binding.correctProjectButton.setOnClickListener(v -> {
            count++;
            if(count%2 == 0){
                binding.projectName.setEnabled(true);
            }
            else{
                binding.projectName.setEnabled(false);
            }
        });

        binding.infoProjectButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(pvm.project.getValue().getTitle())
                    .setMessage(pvm.project.getValue().getDescription())
                    .setIcon(R.drawable.ic_project)
                    .setPositiveButton("Оk", (dialog, id) -> {
                        // Закрываем окно
                        dialog.cancel();
                    })
                    .show();
            builder.create();
        });


        binding.editProjectButton.setOnClickListener(v -> {
            pvm.project.getValue().setTitle(binding.projectName.getText().toString());
            pvm.updateProject();
        });

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add("", KanbanStartTask.class)
                .add("", KanbanDoingTask.class)
                .add("", KanbanEndTask.class)
                .create());
        binding.viewpager.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}