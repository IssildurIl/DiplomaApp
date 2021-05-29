package ru.sfedu.diplomaapp.utils.forTasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.TaskDao;
import ru.sfedu.diplomaapp.models.DevelopersTask;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.models.TestersTask;

import static ru.sfedu.diplomaapp.dao.AppDatabase.databaseWriteExecutor;

public class TaskViewModel extends AndroidViewModel {

    private AppDatabase appdb;
    private TaskDao taskDao;

    public LiveData<Task> task;
    public LiveData<DevelopersTask> developersTask;
    public LiveData<TestersTask> testersTask;

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

    public void insertDevelopersTask(DevelopersTask developersTask){
        appdb.databaseWriteExecutor.execute(() -> taskDao.insertDevelopersTask(developersTask));
        _eventAddTask.setValue(true);
    }

    public void insertTestersTask(TestersTask testersTask){
        appdb.databaseWriteExecutor.execute(() -> taskDao.insertTestersTask(testersTask));
        _eventAddTask.setValue(true);
    }


    public void updateTask() {
        task.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTask(task.getValue()));
        _eventUpdTask.setValue(true);
    }

    public void updateDevelopersTask() {
        developersTask.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateDevelopersTask(developersTask.getValue()));
        _eventUpdTask.setValue(true);
    }

    public void updateTestersTask() {
        testersTask.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTestersTask(testersTask.getValue()));
        _eventUpdTask.setValue(true);
    }

    public void updateTaskToEmployee() {
        task.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTask(task.getValue()));
        _eventUpdTaskToEmployee.setValue(true);
    }

    public void updateDevelopersTaskToEmployee() {
        developersTask.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateDevelopersTask(developersTask.getValue()));
        _eventUpdTaskToEmployee.setValue(true);
    }

    public void updateTestersTaskToEmployee() {
        testersTask.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTestersTask(testersTask.getValue()));
        _eventUpdTaskToEmployee.setValue(true);
    }

    public void updateTaskToProject() {
        task.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTask(task.getValue()));
        _eventUpdTaskToProject.setValue(true);
    }
    public void updateDevelopersTaskToProject() {
        developersTask.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateDevelopersTask(developersTask.getValue()));
        _eventUpdTaskToProject.setValue(true);
    }
    public void updateTestersTaskToProject() {
        testersTask.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.updateTestersTask(testersTask.getValue()));
        _eventUpdTaskToProject.setValue(true);
    }


    public void getTask(long id) {
        task = taskDao.getTaskById(id);
    }
    public void getDevelopersTask(long id) {
        developersTask = taskDao.getDevelopersTaskById(id);
    }
    public void getTestersTask(long id) {
        testersTask = taskDao.getTestersTaskById(id);
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

    public void updateTask(Task task) {
        task.setTimestamp(new Date().getTime());
        databaseWriteExecutor.execute(() -> taskDao.updateTask(task));
    }

}
