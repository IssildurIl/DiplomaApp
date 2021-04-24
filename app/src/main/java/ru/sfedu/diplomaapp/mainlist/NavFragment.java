package ru.sfedu.diplomaapp.mainlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.mainlist.mainfragments.HelloAct;
import ru.sfedu.diplomaapp.mainlist.mainfragments.MyProject;
import ru.sfedu.diplomaapp.mainlist.mainfragments.MyTask;
import ru.sfedu.diplomaapp.mainlist.mainfragments.ToDo;


public class NavFragment extends Fragment {



    public NavFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav, container, false);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add("Обновления", HelloAct.class)
                .add("Мои проекты", MyProject.class)
                .add("Мои задачи", MyTask.class)
                .add("Срочные задачи", ToDo.class)
                .create());
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        try {
            Bundle bundle = getArguments();
            int item = bundle.getInt("item");
            viewPager.setCurrentItem(item);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}