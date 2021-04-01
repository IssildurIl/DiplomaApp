package ru.sfedu.diplomaapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import ru.sfedu.diplomaapp.mainlist.HelloAct;
import ru.sfedu.diplomaapp.mainlist.MyProject;
import ru.sfedu.diplomaapp.mainlist.MyTask;
import ru.sfedu.diplomaapp.mainlist.ToDo;


public class MainActivity extends AppCompatActivity {
    BubbleNavigationConstraintView bncv;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bncv = findViewById(R.id.bottom_navigation_view_constraint);
        bncv.setBadgeValue(0,"30");
        bncv.setBadgeValue(1,"20");
        bncv.setBadgeValue(2,"20");
        bncv.setBadgeValue(3,"5");


        bncv.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position){
                    case 0:
                        ft = getSupportFragmentManager().beginTransaction();
                        //ft.setCustomAnimations(R.anim.go_to_right, R.anim.go_to_left, R.anim.go_to_right, R.anim.go_to_left);
                        ft.replace(R.id.fragmentContainerView,new HelloAct());
                        //ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case 1:
                        ft =getSupportFragmentManager().beginTransaction();
                        //ft.setCustomAnimations(R.anim.go_to_right, R.anim.go_to_left, R.anim.go_to_right, R.anim.go_to_left);
                        ft.replace(R.id.fragmentContainerView,new MyProject());
                        ft.commit();
                        break;
                    case 2:
                        ft =getSupportFragmentManager().beginTransaction();
                        //ft.setCustomAnimations(R.anim.go_to_right, R.anim.go_to_left, R.anim.go_to_right, R.anim.go_to_left);
                        ft.replace(R.id.fragmentContainerView,new MyTask());
                        ft.commit();
                        break;
                    case 3:
                        ft =getSupportFragmentManager().beginTransaction();
                        //ft.setCustomAnimations(R.anim.go_to_right, R.anim.go_to_left, R.anim.go_to_right, R.anim.go_to_left);
                        ft.replace(R.id.fragmentContainerView,new ToDo());
                        ft.commit();
                        break;
                }
            }
        });
    }
}