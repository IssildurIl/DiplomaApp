package ru.sfedu.diplomaapp.utils.forTasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.TaskDao;
import ru.sfedu.diplomaapp.models.Task;

public class TasksViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private TaskDao taskDao;

    public LiveData<List<Task>> taskListOpen;
    public LiveData<List<Task>> taskListResume;
    public LiveData<List<Task>> taskListFinished;

    private MutableLiveData<Long> navigateToTask = new MutableLiveData<>();

    public TasksViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
        taskDao = appDatabase.taskDao();
        taskListOpen = taskDao.getStartedTask();
        taskListResume = taskDao.getProcessingTask();
        taskListFinished = taskDao.getEndedTask();

        navigateToTask.setValue(null);
    }


    public void onTaskItemClicked(Long id){
        navigateToTask.setValue(id);
    }
    public void onTaskItemNavigated(){
        navigateToTask.setValue(null);
    }
    public LiveData<Long> getNavigateToTaskEdit(){
        return navigateToTask;
    }
}
