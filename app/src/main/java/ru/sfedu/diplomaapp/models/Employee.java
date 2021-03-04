package ru.sfedu.diplomaapp.models;


import java.io.Serializable;

import lombok.Data;
import ru.sfedu.diplomaapp.models.enums.TypeOfEmployee;

@Data
public class Employee implements Serializable {

  private long id;

  private String firstName;

  private String lastName;

  private String login;

  private String password;

  private String email;

  private String token;

  private String department;

  private TypeOfEmployee typeOfEmployee;

  public Employee () { }

  public Employee iniEmployee(String firstName,String lastName,String login,String password,String email,TypeOfEmployee typeOfEmployee){
    Employee employee = new Employee();
    employee.setFirstName(firstName);
    employee.setLastName(lastName);
    employee.setLogin(login);
    employee.setPassword(password);
    employee.setEmail(email);
    employee.setTypeOfEmployee(typeOfEmployee);
    return employee;
  }
}
