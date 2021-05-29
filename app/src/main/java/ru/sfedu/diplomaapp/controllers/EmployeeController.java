package ru.sfedu.diplomaapp.controllers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import ru.sfedu.diplomaapp.models.Developer;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Tester;


public interface EmployeeController {

    @POST("api/employee_controller/s_user")
    Call<Boolean> addEmployee(@Body Employee employee);

    @GET("api/employee_controller/employees")
    Call<List<Employee>> getAll();

    @GET("api/employee_controller/developers")
    Call<List<Developer>> getAllDevelopers();

    @GET("api/employee_controller/testers")
    Call<List<Tester>> getAllTesters();

    @GET("api/employee_controller/id_employee")
    Call<Employee> getById(long id);

    @GET("api/employee_controller/id_developer")
    Call<Developer> getDeveloperById(long id);

    @GET("api/employee_controller/id_tester")
    Call<Tester> getTesterById(long id);

    @PUT("api/employee_controller/u_user")
    Call<Boolean> update(@Body Employee employee);

    @HTTP(method = "DELETE", path ="api/employee_controller/d_user", hasBody = true)
    Call<Boolean> deleteEmployee(@Body Employee employee);

    @GET("api/employee_controller/aut_employee")
    Call<Employee> getEmployeeAuthorisation(String email, String password);

    @GET("api/employee_controller/aut_developer")
    Call<Developer> getDeveloperAuthorisation(String email,String password);

    @GET("api/employee_controller/aut_tester")
    Call<Tester> getTesterAuthorisation(String email,String password);


}
