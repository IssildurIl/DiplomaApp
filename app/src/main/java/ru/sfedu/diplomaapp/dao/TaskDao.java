package ru.sfedu.diplomaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM Task WHERE status = 0 and projectId=:projectId and employeeId=:employeeId")
    LiveData<List<Task>> getStartedTask(long projectId,long employeeId);

    @Query("SELECT * FROM Task WHERE status = 1 and projectId=:projectId and employeeId=:employeeId")
    LiveData<List<Task>> getProcessingTask(long projectId,long employeeId);

    @Query("SELECT * FROM Task WHERE status = 2 and projectId=:projectId and employeeId=:employeeId")
    LiveData<List<Task>> getEndedTask(long projectId,long employeeId);

    @Query("SELECT * FROM Task WHERE _id = :id")
    LiveData<Task> getTaskById(long id);


    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM Task")
    void deleteAll();

}
