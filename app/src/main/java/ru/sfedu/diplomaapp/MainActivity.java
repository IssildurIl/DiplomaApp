package ru.sfedu.diplomaapp;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import me.ibrahimsn.lib.SmoothBottomBar;
import ru.sfedu.diplomaapp.mainlist.HelloAct;
import ru.sfedu.diplomaapp.mainlist.MyProject;
import ru.sfedu.diplomaapp.mainlist.MyTask;
import ru.sfedu.diplomaapp.mainlist.ToDo;


public class MainActivity extends AppCompatActivity {
    SmoothBottomBar sbb;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.grey));
        Fragment fragmentHello = new HelloAct();
        Fragment fragmentProject = new MyProject();
        Fragment fragmentTask = new MyTask();
        Fragment fragmentTodo = new ToDo();
        sbb = findViewById(R.id.bottom_navigation_view_constraint);
        sbb.setOnItemSelectedListener(i -> {
            switch (i){
                case 0:
                    replace(fragmentHello);
                    break;
                case 1:
                    replace(fragmentProject);
                    break;
                case 2:
                    replace(fragmentTask);
                    break;
                case 3:
                    replace(fragmentTodo);
                    break;
            }
            return true;
        });

    }

    private void replace(Fragment fragment) {
      ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.fragmentContainerView,fragment).commit();
    }
}