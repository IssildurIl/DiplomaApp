package ru.sfedu.diplomaapp.backgroundActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.CreateprojectBinding;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.utils.ProjectViewModel;
import ru.sfedu.diplomaapp.utils.ProjectsViewModel;

public class CreateProject extends AppCompatActivity {
    ProjectViewModel pvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateprojectBinding binding = DataBindingUtil.setContentView(this, R.layout.createproject);
        binding.setLifecycleOwner(this);
        pvm = new ViewModelProvider(this).get(ProjectViewModel.class);
        pvm.getEventProjectAdd().observe(this, aBoolean -> {
            if(aBoolean){
                pvm.eventProjectAddFinished();
            }
        });

        binding.createProjectButton.setOnClickListener(v -> {
            pvm.insertProject(new Project(binding.projectName.getText().toString()));
            super.onBackPressed();
        });
    }
}
