package ru.sfedu.diplomaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.sfedu.diplomaapp.models.Developer;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Tester;

@Dao
public interface EmployeeDao {


    @Query("SELECT * FROM employee")
    LiveData<List<Employee>> getAll();

    @Query("SELECT * FROM developer")
    LiveData<List<Developer>> getAllDevelopers();

    @Query("SELECT * FROM tester")
    LiveData<List<Tester>> getAllTesters();

//    @Query("SELECT _id,firstName,TypeOfEmployee FROM employee union Select _id,firstName,TypeOfEmployee from developer d union Select _id,firstName,TypeOfEmployee from tester")
//    LiveData<List<Employee>> getAllUsers();

    @Query("SELECT * FROM employee WHERE _id = :id")
    LiveData<Employee> getById(long id);

    @Query("SELECT * FROM developer WHERE _id = :id")
    LiveData<Developer> getDeveloperById(long id);

    @Query("SELECT * FROM Tester WHERE _id = :id")
    LiveData<Tester> getTesterById(long id);

    @Insert
    void insertEmployee(Employee employee);

    @Insert
    void insertDeveloper(Developer developer);

    @Insert
    void insertTester(Tester tester);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);

    @Query("Select * FROM employee WHERE email = :email and password = :password")
    LiveData<Employee> getEmployeeAuthorisation(String email, String password);

    @Query("Select * FROM developer WHERE email = :email and password = :password")
    LiveData<Developer> getDeveloperAuthorisation(String email, String password);

    @Query("Select * FROM tester WHERE email = :email and password = :password")
    LiveData<Tester> getTesterAuthorisation(String email, String password);
}
