package ru.sfedu.diplomaapp.mainlist;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.services.EmployeesService;
import ru.sfedu.diplomaapp.services.ProjectService;
import ru.sfedu.diplomaapp.services.TaskService;


public class MainActivity extends AppCompatActivity {
    EmployeesService es= new EmployeesService();
    ProjectService ps = new ProjectService();
    TaskService ts = new TaskService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary));

        Transition trs = new Slide();
        trs.setStartDelay(2000);
        trs.setDuration(5000);
        getWindow().setEnterTransition(trs);
        getWindow().setExitTransition(trs);


        es.updateEmployees();
        ps.updateProjects();
        ts.updateTasks();
        startService(new Intent(this, EmployeesService.class));
        startService(new Intent(this, TaskService.class));
        startService(new Intent(this, ProjectService.class));
    }

}
