package ru.sfedu.diplomaapp.controllers;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import ru.sfedu.diplomaapp.models.DevelopersTask;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.models.TestersTask;

public interface TaskController {

    @GET("api/project_controller/all_tasks")
    Call<List<Task>> getAllTasks();

    @GET("api/task_controller/s_tasks")
    Call<List<Task>> getStartedTask(long projectId);

    @GET("api/task_controller/s_developers_tasks")
    Call<List<DevelopersTask>> getStartedDevelopersTask(long projectId);

    @GET("api/task_controller/s_testers_tasks")
    Call<List<TestersTask>> getStartedTestersTask(long projectId);

    @GET("api/task_controller/p_tasks")
    Call<List<Task>> getProcessingTask(long projectId);

    @GET("api/task_controller/p_developers_tasks")
    Call<List<DevelopersTask>> getProcessingDevelopersTask(long projectId);

    @GET("api/task_controller/p_testers_tasks")
    Call<List<TestersTask>> getProcessingTestersTask(long projectId);

    @GET("api/task_controller/e_tasks")
    Call<List<Task>> getEndedTask(long projectId);

    @GET("api/task_controller/e_developers_tasks")
    Call<List<DevelopersTask>> getEndedDevelopersTask(long projectId);

    @GET("api/task_controller/e_testers_tasks")
    Call<List<TestersTask>> getEndedTestersTask(long projectId);

    @GET("api/task_controller/task")
    Call<Task> getTaskById(long id);

    @GET("api/task_controller/developerstask")
    Call<DevelopersTask> getDevelopersTaskById(long id);

    @GET("api/task_controller/testerstask")
    Call<TestersTask> getTestersTaskById(long id);

    @GET("api/task_controller/task_by_employee")
    Call<Task> getTasksByEmployee(long employeeId);

    @GET("api/task_controller/developerstask_by_developer")
    Call<DevelopersTask> getTasksByDeveloper(long developerId);

    @GET("api/task_controller/testerstask_by_tester")
    Call<TestersTask> getTasksByTester(long testerId);

    @GET("api/task_controller/number_of_task")
    Call<Integer> getNumberOfTasksByProject(long projectId);

    @GET("api/task_controller/number_of_developerstask")
    Call<Integer> getNumberOfDevelopersTasksByProject(long projectId);

    @GET("api/task_controller/number_of_testerstask")
    Call<Integer> getNumberOfTestersTasksByProject(long projectId);

    @GET("api/task_controller/deadline_task")
    Call<List<Task>> getTaskWeekDeadline(long deadline,long employeeId);

    @GET("api/task_controller/deadline_developerstask")
    Call<List<DevelopersTask>> getDevelopersTaskWeekDeadline( long deadline, long employeeId);

    @GET("api/task_controller/deadline_testerstask")
    Call<List<TestersTask>> getTestersTaskWeekDeadline(long deadline,long employeeId);

    @POST("api/task_controller/s_tasks")
    Call<Boolean> saveTask(@Body Task task);


    @PUT("api/task_controller/u_tasks")
    Call<Boolean> updateTask(@Body Task task);

    @HTTP(method = "DELETE", path ="api/task_controller/d_task", hasBody = true)
    Call<Boolean> deleteTask(@Body Task task);


}
