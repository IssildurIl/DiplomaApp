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

    @Query("SELECT * FROM Task WHERE status = 1")
    LiveData<List<Task>> getStartedTask();

    @Query("SELECT * FROM Task WHERE status = 2")
    LiveData<List<Task>> getProcessingTask();

    @Query("SELECT * FROM Task WHERE status = 3")
    LiveData<List<Task>> getEndedTask();

    @Query("SELECT * FROM Task WHERE _id = :id")
    LiveData<Task> getEmployeeById(long id);


    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM Task")
    void deleteAll();

}
