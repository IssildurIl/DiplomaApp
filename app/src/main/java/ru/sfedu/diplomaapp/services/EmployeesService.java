package ru.sfedu.diplomaapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.EmployeeDao;
import ru.sfedu.diplomaapp.dao.TaskDao;
import ru.sfedu.diplomaapp.models.Developer;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.models.Tester;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeesViewModel;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectsViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;
import ru.sfedu.diplomaapp.utils.otherUtils.RetrofitConfig;

public class EmployeesService extends Service{

    public RetrofitConfig retrofitConfig = new RetrofitConfig();
    EmployeesViewModel evm;
    EmployeeDao employeeDao;
    ArrayList<Long> serverIds = new ArrayList<>();
    ArrayList<Long> localIds = new ArrayList<>();

    long delay = 300000;
    public EmployeesService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppDatabase database =  Room.databaseBuilder(this, AppDatabase.class, "data.db").allowMainThreadQueries().build();
        employeeDao = database.employeeDao();
        new Handler().postDelayed(() -> {
            updateEmployees();
        },delay);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public boolean updateEmployees(){
        Call<List<Employee>> call = retrofitConfig.getEmployeeController().getAll();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                try{
                List<Employee> employeesFromServer =  response.body();
                if(employeesFromServer==null){
                    return;
                }
                List<Employee> employeesFromLocal=employeeDao.getAllEmployeeSync();
                if(employeesFromLocal==null){
                    return;
                }
                for (Employee employee: employeesFromServer){
                    serverIds.add(employee.get_id());
                }

                for (Employee employee: employeesFromLocal){
                    localIds.add(employee.get_id());
                }

                for(Long id: serverIds){
                    if(!localIds.contains(id)){
                        for(Employee employee: employeesFromServer) {
                            if (employee.get_id() == id) {
                                evm.insertEmployee(employee);
                            }
                        }
                    }
                }

                for(Long id: localIds){
                    if(!serverIds.contains(id)){
                        for(Employee employee: employeesFromLocal) {
                            if (employee.get_id() == id) {
                                Call<Boolean> createCall = retrofitConfig.getEmployeeController().addEmployee(employee);
                                createCall.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if(response.isSuccessful()){
                                            Log.d("Success", String.valueOf(response.body()));
                                        }
                                        else {
                                            Log.d("Not success","fail");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Log.d("Err", t.getMessage());
                                    }
                                });
                            }
                        }
                    }
                }

                for(Employee employeeServer: employeesFromServer){
                    for(Employee employeeLocal: employeesFromLocal){
                        if(employeeServer.getTimestamp()<employeeLocal.getTimestamp()){
                            employeeServer.setTimestamp(employeeLocal.getTimestamp());
                            Call<Boolean> updCall = retrofitConfig.getEmployeeController().update(employeeServer);
                            updCall.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if(response.isSuccessful()){
                                        Log.d("Success", String.valueOf(response.body()));
                                    }
                                    else {
                                        Log.d("Not success","fail");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Log.d("Err", t.getMessage());
                                }
                            });
                        }
                        if(employeeServer.getTimestamp()>employeeLocal.getTimestamp()){
                            employeeLocal.setTimestamp(employeeServer.getTimestamp());
                            evm.updateEmployee(employeeLocal);
                        }
                    }
                }
            }catch (Exception e){
                    Log.d("Service", e+"");
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });

        return true;
    }
    public void deleteEmployee(Employee employee){
        Call<Boolean> deleteCall = retrofitConfig.getEmployeeController().deleteEmployee(employee);
        deleteCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Log.d("Success", String.valueOf(response.body()));
                }
                else {
                    Log.d("Not success","fail");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("Err", t.getMessage());
            }
        });
    }


}