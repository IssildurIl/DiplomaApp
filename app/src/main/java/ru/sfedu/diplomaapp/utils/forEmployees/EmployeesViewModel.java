package ru.sfedu.diplomaapp.utils.forEmployees;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.EmployeeDao;
import ru.sfedu.diplomaapp.models.Employee;

import static ru.sfedu.diplomaapp.dao.AppDatabase.databaseWriteExecutor;


public class EmployeesViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private EmployeeDao employeeDao;
    public LiveData<List<Employee>> employeeList;
    public LiveData<Employee> employee;
    private MutableLiveData<Long> navigateToCreateTask = new MutableLiveData<>();

    public EmployeesViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
        employeeDao = appDatabase.employeeDao();
        employeeList = employeeDao.getAll();
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
