package ru.sfedu.diplomaapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import ru.sfedu.diplomaapp.models.Employee;

@Dao
public interface TaskDao {


    @Insert
    void insertProject(Employee employee);

    @Update
    void updateProject(Employee employee);

    @Delete
    void deleteProject(Employee employee);

    @Query("DELETE FROM Employee")
    void deleteAll();

}
