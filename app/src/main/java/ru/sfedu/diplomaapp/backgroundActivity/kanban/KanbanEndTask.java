package ru.sfedu.diplomaapp.backgroundActivity.kanban;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.databinding.FragmentKanbanEndTaskBinding;
import ru.sfedu.diplomaapp.utils.forTasks.TaskDiffCallback;
import ru.sfedu.diplomaapp.utils.forTasks.TaskItemAdapter;
import ru.sfedu.diplomaapp.utils.forTasks.TasksViewModel;


public class KanbanEndTask extends Fragment {
    TasksViewModel tvm;
    Bundle bundle = new Bundle();
    long projectId;
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_EMPLOYEE_TYPE= "SP_EMPLOYEE_TYPE";
    int employeeTypeFromSp;
    SharedPreferences mSettings;
    public KanbanEndTask() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentKanbanEndTaskBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_kanban_end_task,container,false);
        binding.setLifecycleOwner(this);
        tvm = new ViewModelProvider(this).get(TasksViewModel.class);
        binding.setTasksListViewModel(tvm);
        TaskItemAdapter tia = new TaskItemAdapter(new TaskDiffCallback(), task -> {
            tvm.onTaskItemClicked(task.get_id());
        });
        binding.recview.setAdapter(tia);
        bundle = getParentFragment().getArguments();

        tvm.getNavigateToTaskEdit().observe(getViewLifecycleOwner(), taskId -> {
            if(taskId!=null){
                bundle.putLong("E_TASK_ID", taskId);
                bundle.putLong("E_PROJECT_ID",projectId);
                bundle.putInt("E_TASK_TYPE", employeeTypeFromSp);
                NavHostFragment.findNavController(this).navigate(R.id.action_kanbanStartTask_to_editTask,bundle);
                tvm.onTaskItemNavigated();
            }
        });
        projectId = bundle.getLong("projectId");
        shared(tia);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                tvm.deleteTask(tvm.getTaskEndTaskByPosition(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(binding.recview);
        return binding.getRoot();
    }

    private void shared(TaskItemAdapter tia) {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(APP_PREFERENCES_EMPLOYEE_TYPE)) {
            employeeTypeFromSp=mSettings.getInt(APP_PREFERENCES_EMPLOYEE_TYPE,0);
        }
        methodTaskEmployee(employeeTypeFromSp,tia);

    }

    private void methodTaskEmployee(int status,TaskItemAdapter tia) {
        switch (status) {
            case 0:
                tvm.getTaskListFinished(projectId);
                tvm.taskListFinished.observe(getViewLifecycleOwner(), tasks -> {
                    if (tasks != null) {
                        tia.submitList(tasks);
                    }
                });
                break;
            case 1:
                tvm.getDevelopersTaskListFinished(projectId);
                tvm.developersTaskListFinished.observe(getViewLifecycleOwner(), developersTask -> {
                    if (developersTask != null) {
                        tia.submitList(developersTask);
                    }
                });
                break;
            case 2:
                tvm.getTestersTaskListFinished(projectId);
                tvm.testersTaskListFinished.observe(getViewLifecycleOwner(), testersTask -> {
                    if (testersTask != null) {
                        tia.submitList(testersTask);
                    }
                });
                break;
            default:
                break;
        }
    }

}