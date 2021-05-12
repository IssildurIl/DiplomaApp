package ru.sfedu.diplomaapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import ru.sfedu.diplomaapp.models.Employee;

public interface AuthorisationController {
    @POST("/employee")
    public Call<Boolean> addEmployee(@Body Employee employee);
    
    @HTTP(method = "DELETE", path ="/employee", hasBody = true)
    public Call<Boolean> deleteEmployee(@Body Employee employee);
}
