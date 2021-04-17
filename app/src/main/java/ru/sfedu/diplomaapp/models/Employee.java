package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Entity(tableName  = "Employee")
@ToString
@Data
public class Employee implements Serializable {
  @PrimaryKey(autoGenerate = true)
  private long _id;

  private String firstName;

  private String password;

  private String email;

  public Employee () { }

  public Employee(String firstName, String password,String email) {
    this.firstName = firstName;
    this.password = password;
    this.email = email;
  }

}
