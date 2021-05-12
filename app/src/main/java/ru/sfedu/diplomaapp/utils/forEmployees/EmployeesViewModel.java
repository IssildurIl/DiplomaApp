package ru.sfedu.diplomaapp.utils.forEmployees;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.EmployeeDao;
import ru.sfedu.diplomaapp.models.Developer;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Tester;

import static ru.sfedu.diplomaapp.dao.AppDatabase.databaseWriteExecutor;


public class EmployeesViewModel extends AndroidViewModel {
    private final AppDatabase appDatabase;
    private final EmployeeDao employeeDao;
    public LiveData<List<Employee>> employeeList;
    public LiveData<List<Developer>> developerList;
    public LiveData<List<Tester>> testerList;
    public MediatorLiveData<List<? extends Employee>> allUsers = new MediatorLiveData<>();

    public LiveData<Employee> employee;
    private MutableLiveData<Long> navigateToCreateTask = new MutableLiveData<>();

    public EmployeesViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
        employeeDao = appDatabase.employeeDao();

        employeeList = employeeDao.getAll();
        developerList = employeeDao.getAllDevelopers();
        testerList = employeeDao.getAllTesters();

        navigateToCreateTask.setValue(null);
    }
    public void insertEmployee(Employee employee) { databaseWriteExecutor.execute(() -> employeeDao.insertEmployee(employee)); }

    public void updateEmployee(Employee employee) { databaseWriteExecutor.execute(() -> employeeDao.update(employee)); }

    public void deleteEmployee(Employee employee) { databaseWriteExecutor.execute(() -> employeeDao.delete(employee)); }

    public void onEmployeeToTaskItemClicked(Long id){
        navigateToCreateTask.setValue(id);
    }
    public void onEmployeeToTaskProjectItemNavigated(){
        navigateToCreateTask.setValue(null);
    }

    public LiveData<Long> getNavigateEmployeeToTask(){
        return navigateToCreateTask;
    }

}
