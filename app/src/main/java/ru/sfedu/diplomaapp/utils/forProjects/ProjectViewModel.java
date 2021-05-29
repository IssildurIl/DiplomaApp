package ru.sfedu.diplomaapp.utils.forProjects;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.ProjectDao;
import ru.sfedu.diplomaapp.models.Project;

public class ProjectViewModel extends AndroidViewModel {

    private AppDatabase appdb;
    private ProjectDao projectDao;

    public LiveData<Project> project;
    private MutableLiveData<Boolean> _eventAddProject = new MutableLiveData<>();
    private MutableLiveData<Boolean> _eventUpdProject = new MutableLiveData<>();

    public ProjectViewModel(@NonNull Application application) {
        super(application);

        appdb = AppDatabase.getDatabase(application);
        projectDao = appdb.projectDao();

        _eventAddProject.setValue(false);
    }

    public void insertProject(Project project) {
        appdb.databaseWriteExecutor.execute(() -> projectDao.insertProject(project));
        _eventAddProject.setValue(true);
    }
    public void eventProjectAddFinished() {
        this._eventAddProject.setValue(false);
    }

    public void getProject(long id) {
        project = projectDao.getProjectById(id);
    }

    public void updateProject() {
        project.getValue().setTimestamp(new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> projectDao.updateProject(project.getValue()));
        _eventUpdProject.setValue(true);
    }

    public void eventProjectUpdateFinished() {
        this._eventUpdProject.setValue(false);
    }

    public LiveData<Boolean> getEventProjectUpd(){
        return _eventUpdProject;
    }

    public LiveData<Boolean> getEventProjectAdd(){
        return _eventAddProject;
    }


}
