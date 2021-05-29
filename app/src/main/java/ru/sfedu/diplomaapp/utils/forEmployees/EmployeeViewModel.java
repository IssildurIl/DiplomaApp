package ru.sfedu.diplomaapp.utils.forEmployees;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.EmployeeDao;
import ru.sfedu.diplomaapp.models.Developer;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Tester;

import static ru.sfedu.diplomaapp.dao.AppDatabase.databaseWriteExecutor;

public class EmployeeViewModel extends AndroidViewModel {

    private AppDatabase appdb;
    private EmployeeDao employeeDao;

    public LiveData<Employee> employee;
    public LiveData<Developer> developer;
    public LiveData<Tester> tester;

    public LiveData<Employee> employeeByEmail;
    public LiveData<Developer> developerByEmail;
    public LiveData<Tester> testerByEmail;
    private MutableLiveData<Boolean> _eventAddEmployee = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventGetEmployeeByEmail = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventUpdEmployee = new MutableLiveData<>();
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

    public void insertDeveloper(Developer developer) {
        appdb.databaseWriteExecutor.execute(() -> employeeDao.insertDeveloper(developer));
        _eventAddEmployee.setValue(true);
    }

    public void insertTester(Tester tester) {
        appdb.databaseWriteExecutor.execute(() -> employeeDao.insertTester(tester));
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

    public void getDeveloper(long id) {
        developer = employeeDao.getDeveloperById(id);
    }

    public void getTester(long id) {
        tester = employeeDao.getTesterById(id);
    }

    public void getEmployeeByEmail(String email,String password){
        employeeByEmail = employeeDao.getEmployeeAuthorisation(email,password);
        _eventGetEmployeeByEmail.setValue(true);
    }

    public void getDeveloperByEmail(String email,String password){
        developerByEmail = employeeDao.getDeveloperAuthorisation(email,password);
        _eventGetEmployeeByEmail.setValue(true);
    }

    public void getTesterByEmail(String email,String password){
        testerByEmail = employeeDao.getTesterAuthorisation(email,password);
        _eventGetEmployeeByEmail.setValue(true);
    }

    public LiveData<Boolean> getEventGetEmployeeByEmail(){
        return _eventGetEmployeeByEmail;
    }

    public void eventEmployeeGetEmployeeByEmailFinished() {
        this._eventGetEmployeeByEmail.setValue(false);
    }
    public void updateEmployee() {
        employee.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> employeeDao.update(employee.getValue()));
        _eventUpdEmployee.setValue(true);
    }

    public void updateDeveloper() {
        developer.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> employeeDao.update(developer.getValue()));
        _eventUpdEmployee.setValue(true);
    }

    public void updateTester() {
        tester.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> employeeDao.update(tester.getValue()));
        _eventUpdEmployee.setValue(true);
    }

    public void eventEmployeeUpdateFinished() {
        this._eventUpdEmployee.setValue(false);
    }

    public LiveData<Boolean> getEventEmployeeUpd(){
        return _eventUpdEmployee;
    }
}
