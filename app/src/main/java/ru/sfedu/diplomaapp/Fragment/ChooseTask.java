package ru.sfedu.diplomaapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.utils.RecycleView.ExampleItem;
import ru.sfedu.diplomaapp.utils.RecycleView.RecycleViewAdaptor;

import java.util.ArrayList;

public class ChooseTask extends Fragment {

    RecyclerView mRecyclerView;
    private ArrayList<ExampleItem> mExampleList;
    private RecycleViewAdaptor mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ChooseTask() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return Recycle(inflater,container);
    }

    public View Recycle(LayoutInflater inflater,ViewGroup container){
        View view = inflater.inflate(R.layout.fragment_choose_task, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.chars_rec);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdaptor rva = new RecycleViewAdaptor(getContext(),mExampleList);
        mRecyclerView.setAdapter(rva);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createExampleList();
        buildRecyclerView();
        NavController navController = Navigation.findNavController(view);
        view.findViewById(R.id.fab).setOnClickListener(view1 -> navController.navigate(R.id.go_to_createTask));
        mAdapter.setOnItemClickListener(position -> navController.navigate(R.id.go_to_taskList));
    }

    public void buildRecyclerView() { //НЕ ТРОГАТЬ
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecycleViewAdaptor(getContext(),mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void createExampleList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem("Ivan","DevelopersTask","1 week"));
        mExampleList.add(new ExampleItem("Vanya","Task","2 week"));

    }
}