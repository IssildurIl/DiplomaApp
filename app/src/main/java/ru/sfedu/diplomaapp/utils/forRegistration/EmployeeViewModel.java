package ru.sfedu.diplomaapp.utils.forRegistration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.EmployeeDao;
import ru.sfedu.diplomaapp.models.Employee;

public class EmployeeViewModel extends AndroidViewModel {

    private AppDatabase appdb;
    private EmployeeDao employeeDao;

    public LiveData<Employee> employee;
    public LiveData<Employee> employeeByEmail;
    private MutableLiveData<Boolean> _eventAddEmployee = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventGetEmployeeByEmail = new MutableLiveData<>();
    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        appdb = AppDatabase.getDatabase(application);
        employeeDao = appdb.employeeDao();
        _eventGetEmployeeByEmail.setValue(false);
        _eventAddEmployee.setValue(false);
    }

    public void insertEmployee(Employee employee) {
        appdb.databaseWriteExecutor.execute(() -> employeeDao.insertEmployee(employee));
        _eventAddEmployee.setValue(true);
    }

    public LiveData<Boolean> getEventEmployeeAdd(){
        return _eventAddEmployee;
    }

    public void eventEmployeeAddFinished() {
        this._eventAddEmployee.setValue(false);
    }


    public void getEmployee(long id) {
        employee = employeeDao.getById(id);
    }


    public void getEmployeeByEmail(String email){
        employeeByEmail = employeeDao.getEmployeeAuthorisation(email);
        _eventGetEmployeeByEmail.setValue(true);
    }
    public LiveData<Boolean> getEventGetEmployeeByEmail(){
        return _eventGetEmployeeByEmail;
    }

    public void eventEmployeeGetEmployeeByEmailFinished() {
        this._eventGetEmployeeByEmail.setValue(false);
    }
}
