package ru.sfedu.diplomaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.sfedu.diplomaapp.models.Developer;
import ru.sfedu.diplomaapp.models.DevelopersTask;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.models.TestersTask;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM Task")
    List<Task> getAllTasksSync();


    @Query("SELECT * FROM Task WHERE _id = :id")
    LiveData<Task> getTaskById(long id);
    @Query("SELECT * FROM DevelopersTask WHERE _id = :id")
    LiveData<DevelopersTask> getDevelopersTaskById(long id);
    @Query("SELECT * FROM TestersTask WHERE _id = :id")
    LiveData<TestersTask> getTestersTaskById(long id);

    @Query("SELECT * FROM Task WHERE status = 0 and projectId=:projectId")
    LiveData<List<Task>> getStartedTask(long projectId);
    @Query("SELECT * FROM DevelopersTask WHERE status = 0 and projectId=:projectId")
    LiveData<List<DevelopersTask>> getStartedDevelopersTask(long projectId);
    @Query("SELECT * FROM TestersTask WHERE status = 0 and projectId=:projectId")
    LiveData<List<TestersTask>> getStartedTestersTask(long projectId);

    @Query("SELECT * FROM Task WHERE status = 1 and projectId=:projectId")
    LiveData<List<Task>> getProcessingTask(long projectId);
    @Query("SELECT * FROM DevelopersTask WHERE status = 1 and projectId=:projectId")
    LiveData<List<DevelopersTask>> getProcessingDevelopersTask(long projectId);
    @Query("SELECT * FROM TestersTask WHERE status = 1 and projectId=:projectId")
    LiveData<List<TestersTask>> getProcessingTestersTask(long projectId);

    @Query("SELECT * FROM Task WHERE status = 2 and projectId=:projectId ")
    LiveData<List<Task>> getEndedTask(long projectId);
    @Query("SELECT * FROM DevelopersTask WHERE status = 2 and projectId=:projectId")
    LiveData<List<DevelopersTask>> getEndedDevelopersTask(long projectId);
    @Query("SELECT * FROM TestersTask WHERE status = 2 and projectId=:projectId")
    LiveData<List<TestersTask>> getEndedTestersTask(long projectId);




    @Query("SELECT * FROM Task Where employeeId = :employeeId")
    LiveData<List<Task>> getTasksByEmployee(long employeeId);
    @Query("SELECT * FROM DevelopersTask Where employeeId = :developerId")
    LiveData<List<DevelopersTask>> getTasksByDeveloper(long developerId);
    @Query("SELECT * FROM TestersTask Where employeeId = :testerId")
    LiveData<List<TestersTask>> getTasksByTester(long testerId);


    @Query("SELECT Count(*) FROM Task Where projectId = :ProjectId")
    Integer getNumberOfTasksByProject(long ProjectId);
    @Query("Select * from Task where ((deadline-:deadline)< 604800000) and employeeId=:employeeId")
    LiveData<List<Task>> getTaskWeekDeadline(long deadline,long employeeId);
    @Query("Select * from DevelopersTask where ((deadline-:deadline)< 604800000) and employeeId=:employeeId")
    LiveData<List<DevelopersTask>> getDevelopersTaskWeekDeadline(long deadline,long employeeId);

    @Query("Select * from TestersTask where ((deadline-:deadline)< 604800000) and employeeId=:employeeId")
    LiveData<List<TestersTask>> getTestersTaskWeekDeadline(long deadline,long employeeId);


    @Insert
    void insertTask(Task task);

    @Insert
    void insertDevelopersTask(DevelopersTask developersTask);

    @Insert
    void insertTestersTask(TestersTask testersTask);

    @Update
    void updateTask(Task task);

    @Update
    void updateDevelopersTask(DevelopersTask developersTask);

    @Update
    void updateTestersTask(TestersTask testersTask);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM Task")
    void deleteAll();

}
