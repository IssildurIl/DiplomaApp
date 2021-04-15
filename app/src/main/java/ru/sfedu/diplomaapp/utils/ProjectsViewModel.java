package ru.sfedu.diplomaapp.utils;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.ProjectDao;
import ru.sfedu.diplomaapp.models.Project;

import static ru.sfedu.diplomaapp.dao.AppDatabase.databaseWriteExecutor;

public class ProjectsViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private ProjectDao projectDao;

    public LiveData<List<Project>> projectList;

    public ProjectsViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(application);
        projectDao = appDatabase.projectDao();

        projectList = projectDao.getAllProjects();
    }

    public void insertProject(Project project) { databaseWriteExecutor.execute(() -> projectDao.insertProject(project)); }

    public void updateProject(Project project) { databaseWriteExecutor.execute(() -> projectDao.updateProject(project)); }

    public void deleteProject(Project project) { databaseWriteExecutor.execute(() -> projectDao.deleteProject(project)); }

    public void deleteAllProject() { AppDatabase.databaseWriteExecutor.execute(() -> projectDao.deleteAll()); }

}
