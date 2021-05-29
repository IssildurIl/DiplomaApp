package ru.sfedu.diplomaapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.ProjectDao;
import ru.sfedu.diplomaapp.dao.TaskDao;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectsViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TaskViewModel;
import ru.sfedu.diplomaapp.utils.forTasks.TasksViewModel;
import ru.sfedu.diplomaapp.utils.otherUtils.RetrofitConfig;

public class TaskService  extends Service {

    public RetrofitConfig retrofitConfig = new RetrofitConfig();
    TaskViewModel tvm;
    ArrayList<Long> serverIds = new ArrayList<>();
    ArrayList<Long> localIds = new ArrayList<>();
    TaskDao taskDao;
    long delay = 300000;
    public TaskService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppDatabase database =  Room.databaseBuilder(this, AppDatabase.class, "data.db").allowMainThreadQueries().build();
        taskDao = database.taskDao();
        new Handler().postDelayed(() -> {
            updateTasks();
        },delay);
        return super.onStartCommand(intent, flags, startId);
    }

    public boolean updateTasks(){
        Call<List<Task>> call = retrofitConfig.getTaskController().getAllTasks();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                try{
                    List<Task> tasksFromServer =  response.body();
                    if(tasksFromServer==null){
                        return;
                    }
                    List<Task> tasksFromLocal=taskDao.getAllTasksSync();
                    if(tasksFromLocal==null){
                        return;
                    }
                    for (Task employee: tasksFromServer){
                        serverIds.add(employee.get_id());
                    }

                    for (Task employee: tasksFromLocal){
                        localIds.add(employee.get_id());
                    }

                    for(Long id: serverIds){
                        if(!localIds.contains(id)){
                            for(Task task: tasksFromServer) {
                                if (task.get_id() == id) {
                                    tvm.insertTask(task);
                                }
                            }
                        }
                    }

                    for(Long id: localIds){
                        if(!serverIds.contains(id)){
                            for(Task task: tasksFromLocal) {
                                if (task.get_id() == id) {
                                    Call<Boolean> createCall = retrofitConfig.getTaskController().saveTask(task);
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

                    for(Task taskServer: tasksFromServer){
                        for(Task taskLocal: tasksFromLocal){
                            if(taskServer.getTimestamp()<taskLocal.getTimestamp()){
                                taskServer.setTimestamp(taskLocal.getTimestamp());
                                Call<Boolean> updCall = retrofitConfig.getTaskController().updateTask(taskServer);
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
                            if(taskServer.getTimestamp()>taskLocal.getTimestamp()){
                                taskLocal.setTimestamp(taskServer.getTimestamp());
                                tvm.updateTask(taskLocal);
                            }
                        }
                    }
                }catch (Exception e){
                    Log.d("Service", e+"");
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });

        return true;
    }
    public void deleteTask(Task task){
        Call<Boolean> deleteCall = retrofitConfig.getTaskController().deleteTask(task);
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