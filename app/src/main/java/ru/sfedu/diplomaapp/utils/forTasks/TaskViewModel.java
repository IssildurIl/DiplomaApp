package ru.sfedu.diplomaapp.utils.forTasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.TaskDao;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;

public class TaskViewModel extends AndroidViewModel {

    private AppDatabase appdb;
    private TaskDao taskDao;

    public LiveData<Task> task;
    private MutableLiveData<Boolean> _eventAddTask = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventUpdTask = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventLoadDataTask = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventUpdTaskToEmployee = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventUpdTaskToProject = new MutableLiveData<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);

        appdb = AppDatabase.getDatabase(application);
        taskDao = appdb.taskDao();
        _eventLoadDataTask.setValue(true);
        _eventAddTask.setValue(false);
        _eventUpdTaskToEmployee.setValue(true);
    }

    public void insertTask(Task task){
        appdb.databaseWriteExecutor.execute(() -> taskDao.insertTask(task));
        _eventAddTask.setValue(true);
    }

    public void updateTask() {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTask(task.getValue()));
        _eventUpdTask.setValue(true);
    }

    public void updateTaskToEmployee() {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTask(task.getValue()));
        _eventUpdTaskToEmployee.setValue(true);
    }

    public void updateTaskToProject() {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTask(task.getValue()));
        _eventUpdTaskToProject.setValue(true);
    }


    public void getTask(long id) {
        task = taskDao.getTaskById(id);
    }

    public void eventTaskUpdateFinished() {
        this._eventUpdTask.setValue(false);
    }

    public LiveData<Boolean> getEventTaskUpd(){
        return _eventUpdTask;
    }


    public void eventTaskToEmployeeUpdateFinished() {
        this._eventUpdTaskToEmployee.setValue(false);
    }

    public LiveData<Boolean> getEventTaskToEmployeeUpd(){
        return _eventUpdTaskToEmployee;
    }


    public void eventTaskToProjectUpdateFinished() {
        this._eventUpdTaskToProject.setValue(false);
    }

    public LiveData<Boolean> getEventTaskToProjectUpd(){
        return _eventUpdTaskToProject;
    }


    public void eventTaskAddFinished() {
        this._eventAddTask.setValue(false);
    }

    public LiveData<Boolean> getEventTaskAdd(){
        return _eventAddTask;
    }


    public LiveData<Boolean> loadTaskData(){
        return _eventLoadDataTask;
    }
    public void eventTaskDataLoaded() {
        this._eventLoadDataTask.setValue(false);
    }


}
