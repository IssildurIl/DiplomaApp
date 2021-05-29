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
import ru.sfedu.diplomaapp.dao.EmployeeDao;
import ru.sfedu.diplomaapp.dao.ProjectDao;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.utils.forEmployees.EmployeesViewModel;
import ru.sfedu.diplomaapp.utils.forProjects.ProjectsViewModel;
import ru.sfedu.diplomaapp.utils.otherUtils.RetrofitConfig;

public class ProjectService extends Service {

    public RetrofitConfig retrofitConfig = new RetrofitConfig();
    ProjectsViewModel pvm;
    ArrayList<Long> serverIds = new ArrayList<>();
    ArrayList<Long> localIds = new ArrayList<>();
    ProjectDao projectDao;
    long delay = 300000;
    public ProjectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppDatabase database =  Room.databaseBuilder(this, AppDatabase.class, "data.db").allowMainThreadQueries().build();
        projectDao = database.projectDao();
        new Handler().postDelayed(() -> {
            updateProjects();
        },delay);
        return super.onStartCommand(intent, flags, startId);
    }

    public boolean updateProjects(){
        Call<List<Project>> call = retrofitConfig.getProjectController().getAllProjects();
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                try{
                    List<Project> projectsFromServer =  response.body();
                    if(projectsFromServer==null){
                        return;
                    }
                    List<Project> projectsFromLocal=projectDao.getAllProjectsSync();
                    if(projectsFromLocal==null){
                        return;
                    }
                    for (Project project: projectsFromServer){
                        serverIds.add(project.get_id());
                    }

                    for (Project project: projectsFromLocal){
                        localIds.add(project.get_id());
                    }

                    for(Long id: serverIds){
                        if(!localIds.contains(id)){
                            for(Project project: projectsFromServer) {
                                if (project.get_id() == id) {
                                    pvm.insertProject(project);
                                }
                            }
                        }
                    }

                    for(Long id: localIds){
                        if(!serverIds.contains(id)){
                            for(Project project: projectsFromLocal) {
                                if (project.get_id() == id) {
                                    Call<Boolean> createCall = retrofitConfig.getProjectController().insertProject(project);
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

                    for(Project projectServer: projectsFromServer){
                        for(Project projectLocal: projectsFromLocal){
                            if(projectServer.getTimestamp()<projectLocal.getTimestamp()){
                                projectServer.setTimestamp(projectLocal.getTimestamp());
                                Call<Boolean> updCall = retrofitConfig.getProjectController().updateProject(projectServer);
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
                            if(projectServer.getTimestamp()>projectLocal.getTimestamp()){
                                projectLocal.setTimestamp(projectServer.getTimestamp());
                                pvm.updateProject(projectLocal);
                            }
                        }
                    }
                }catch (Exception e){
                    Log.d("Service", e+"");
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });

        return true;
    }

    public void deleteProject(Project project){
        Call<Boolean> deleteCall = retrofitConfig.getProjectController().deleteProject(project);
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