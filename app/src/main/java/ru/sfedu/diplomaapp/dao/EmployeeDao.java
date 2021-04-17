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
    LiveData<Employee> getById(long id);

    @Insert
    void insertEmployee(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);

    @Query("Select * FROM employee WHERE email = :email and password = :password")
    LiveData<Employee> getEmployeeAuthorisation(String email, String password);
}
