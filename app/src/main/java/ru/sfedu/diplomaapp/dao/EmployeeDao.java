package ru.sfedu.diplomaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.sfedu.diplomaapp.models.Employee;

@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM employee")
    LiveData<List<Employee>> getAll();

    @Query("SELECT * FROM employee WHERE _id = :id")
    Employee getById(long id);

    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);
}
