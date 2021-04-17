package ru.sfedu.diplomaapp.utils.forRegistration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.EmployeeDao;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Project;

import static ru.sfedu.diplomaapp.dao.AppDatabase.databaseWriteExecutor;


public class EmployeesViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private EmployeeDao employeeDao;
    public LiveData<List<Employee>> employeeList;
    public LiveData<Employee> employee;

    public EmployeesViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
        employeeDao = appDatabase.employeeDao();
        employeeList = employeeDao.getAll();
    }
    public void insertEmployee(Employee employee) { databaseWriteExecutor.execute(() -> employeeDao.insertEmployee(employee)); }

    public void updateEmployee(Employee employee) { databaseWriteExecutor.execute(() -> employeeDao.update(employee)); }

    public void deleteEmployee(Employee employee) { databaseWriteExecutor.execute(() -> employeeDao.delete(employee)); }
}
