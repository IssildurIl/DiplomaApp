package ru.sfedu.diplomaapp.controllers;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import ru.sfedu.diplomaapp.models.Project;

public interface ProjectController {

    @GET("api/project_controller/projects")
    Call<List<Project>> getAllProjects();

    @GET("api/project_controller/project")
    Call<Project> getProjectById(long id);

    @POST("api/project_controller/c_project")
    Call<Boolean> insertProject(@Body Project project);

    @PUT("api/project_controller/u_project")
    Call<Boolean> updateProject(@Body Project project);

    @HTTP(method = "DELETE", path ="api/project_controller/d_project", hasBody = true)
    Call<Boolean> deleteProject(@Body Project project);

}
