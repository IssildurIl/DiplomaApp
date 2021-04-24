package ru.sfedu.diplomaapp.backgroundActivity.kanban;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentKanbanStartTaskBinding;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectDiffCallback;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectItemAdapter;
import ru.sfedu.diplomaapp.utils.forTasks.TaskDiffCallback;
import ru.sfedu.diplomaapp.utils.forTasks.TaskItemAdapter;
import ru.sfedu.diplomaapp.utils.forTasks.TasksViewModel;


public class KanbanStartTask extends Fragment {
    TasksViewModel tvm;
    Bundle bundle = new Bundle();
    public KanbanStartTask() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentKanbanStartTaskBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_kanban_start_task,container,false);
        binding.setLifecycleOwner(this);
        tvm = new ViewModelProvider(this).get(TasksViewModel.class);
        binding.setTasksListViewModel(tvm);

        TaskItemAdapter tia = new TaskItemAdapter(new TaskDiffCallback(), task -> {
            tvm.onTaskItemClicked(task.get_id());
        });
        binding.recview.setAdapter(tia);

        tvm.getNavigateToTaskEdit().observe(getViewLifecycleOwner(), taskId -> {
            if(taskId!=null){
                bundle.putLong("TASK_ID", taskId);
                NavHostFragment.findNavController(this).navigate(R.id.action_kanbanStartTask_to_editTask,bundle);
                tvm.onTaskItemNavigated();
            }
        });


        tvm.taskListOpen.observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                tia.submitList(tasks);
            }
        });

        return binding.getRoot();
    }
}