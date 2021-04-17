package ru.sfedu.diplomaapp.utils.forprojects;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.sfedu.diplomaapp.dao.AppDatabase;
import ru.sfedu.diplomaapp.dao.ProjectDao;
import ru.sfedu.diplomaapp.models.Project;

import static ru.sfedu.diplomaapp.dao.AppDatabase.databaseWriteExecutor;

public class ProjectsViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private ProjectDao projectDao;

    public LiveData<List<Project>> projectList;
    private MutableLiveData<Long> navigateToProject = new MutableLiveData<>();

    public ProjectsViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(application);
        projectDao = appDatabase.projectDao();
        navigateToProject.setValue(null);
        projectList = projectDao.getAllProjects();
    }

    public void insertProject(Project project) { databaseWriteExecutor.execute(() -> projectDao.insertProject(project)); }

    public void updateProject(Project project) { databaseWriteExecutor.execute(() -> projectDao.updateProject(project)); }

    public void deleteProject(Project project) { databaseWriteExecutor.execute(() -> projectDao.deleteProject(project)); }

    public void deleteAllProject() { AppDatabase.databaseWriteExecutor.execute(() -> projectDao.deleteAll()); }

    public void onProjectItemClicked(Long id){
        navigateToProject.setValue(id);
    }
    public void onProjectItemNavigated(){
        navigateToProject.setValue(null);
    }

    public LiveData<Long> getNavigateToProjectEdit(){
        return navigateToProject;
    }


}
