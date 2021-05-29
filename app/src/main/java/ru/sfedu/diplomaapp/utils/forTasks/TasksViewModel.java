package ru.sfedu.diplomaapp.utils.forTasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.TaskDao;
import ru.sfedu.diplomaapp.models.DevelopersTask;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.models.TestersTask;
import ru.sfedu.diplomaapp.services.ProjectService;
import ru.sfedu.diplomaapp.services.TaskService;

import static ru.sfedu.diplomaapp.dao.AppDatabase.databaseWriteExecutor;

public class TasksViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private TaskDao taskDao;
    TaskService ts = new TaskService();
    public int numberOfTasks=0;
    public LiveData<List<Task>> taskList;


    public LiveData<List<Task>> taskListOpen;
    public LiveData<List<DevelopersTask>> developersTaskListOpen;
    public LiveData<List<TestersTask>> testersTaskListOpen;

    public LiveData<List<Task>> taskListResume;
    public LiveData<List<DevelopersTask>> developersTaskListResume;
    public LiveData<List<TestersTask>> testersTaskListResume;

    public LiveData<List<Task>> taskListFinished;
    public LiveData<List<DevelopersTask>> developersTaskListFinished;
    public LiveData<List<TestersTask>> testersTaskListFinished;


    public LiveData<List<Task>> taskListByEmployee;
    public LiveData<List<DevelopersTask>> taskListByDeveloper;
    public LiveData<List<TestersTask>> taskListByTester;


    public LiveData<List<Task>> outdatedTask;
    public LiveData<List<DevelopersTask>> outdatedDevelopersTask;
    public LiveData<List<TestersTask>> outdatedTestersTask;

    private MutableLiveData<Long> navigateToTask = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventCount = new MutableLiveData<>();

    public TasksViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
        taskDao = appDatabase.taskDao();
        taskList = taskDao.getAllTasks();
        navigateToTask.setValue(null);
        _eventCount.setValue(true);
    }

    public void deleteTask(Task task) { ts.deleteTask(task);databaseWriteExecutor.execute(() -> taskDao.deleteTask(task)); }

    public Task getTaskEndTaskByPosition(int position){
        List<Task> listTasks = taskListFinished.getValue();
        return listTasks.get(position);
    }


    public void getTaskListOpen(long projectId){
        taskListOpen = taskDao.getStartedTask(projectId);
    }
    public void getDevelopersTaskListOpen(long projectId){
        developersTaskListOpen = taskDao.getStartedDevelopersTask(projectId);
    }
    public void getTestersTaskListOpen(long projectId){
        testersTaskListOpen = taskDao.getStartedTestersTask(projectId);
    }



    public void getTaskListResume(long projectId){
        taskListResume = taskDao.getProcessingTask(projectId);
    }
    public void getDevelopersTaskListResume(long projectId){
        developersTaskListResume = taskDao.getProcessingDevelopersTask(projectId);
    }
    public void getTestersTaskListResume(long projectId){
        testersTaskListResume = taskDao.getProcessingTestersTask(projectId);
    }



    public void getTaskListFinished(long projectId){
        taskListFinished = taskDao.getEndedTask(projectId);
    }
    public void getDevelopersTaskListFinished(long projectId){
        developersTaskListFinished = taskDao.getEndedDevelopersTask(projectId);
    }
    public void getTestersTaskListFinished(long projectId){
        testersTaskListFinished = taskDao.getEndedTestersTask(projectId);
    }



    public void getTaskByEmployee(long employeeId){
        taskListByEmployee = taskDao.getTasksByEmployee(employeeId);
    }

    public void getTaskByDeveloper(long developerId){
        taskListByDeveloper = taskDao.getTasksByDeveloper(developerId);
    }

    public void getTaskByTester(long testerId){
        taskListByTester = taskDao.getTasksByTester(testerId);
    }




    public void getTaskByEmployeeAndDate(long date,long employeeId){
        outdatedTask = taskDao.getTaskWeekDeadline(date,employeeId);
    }
    public void getDevelopersTaskByDeveloperAndDate(long date,long employeeId){
        outdatedDevelopersTask = taskDao.getDevelopersTaskWeekDeadline(date,employeeId);
    }

    public void getTestersTaskByTesterAndDate(long date,long employeeId){
        outdatedTestersTask = taskDao.getTestersTaskWeekDeadline(date,employeeId);
    }


    public void getNumTaskByProject(long id){
        numberOfTasks = taskDao.getNumberOfTasksByProject(id);
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
