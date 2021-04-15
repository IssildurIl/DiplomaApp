package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
import ru.sfedu.diplomaapp.models.enums.TypeOfEmployee;
@Entity(tableName  = "Employee")
@ToString
@Data
public class Employee implements Serializable {
  @PrimaryKey
  private long _id;

  private String firstName;

  private String lastName;

  private String login;

  private String password;

  private String email;

  private String token;

  private String department;

  private int typeOfEmployee;

  public Employee () { }
}
