package ru.sfedu.diplomaapp.utils.otherUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.sfedu.diplomaapp.controllers.EmployeeController;
import ru.sfedu.diplomaapp.controllers.ProjectController;
import ru.sfedu.diplomaapp.controllers.TaskController;

public class RetrofitConfig {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://stark-sea-06327.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    EmployeeController employeeController;
    TaskController taskController;
    ProjectController projectController;

    public EmployeeController getEmployeeController() {
        employeeController = retrofit.create(EmployeeController.class);
        return employeeController;
    }

    public TaskController getTaskController() {
        taskController = retrofit.create(TaskController.class);
        return taskController;
    }

    public ProjectController getProjectController() {
        projectController = retrofit.create(ProjectController.class);
        return projectController;
    }


}
