package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Entity(tableName  = "Employee")
@ToString
@Data
public class Employee implements Serializable {
  @PrimaryKey(autoGenerate = true)
  protected long _id;

  protected String firstName;
  protected String password;
  protected int typeOfEmployee;
  protected int age;
  protected int sex;
  protected String email;
  protected long timestamp;
  public Employee () { }

  @Ignore
  public Employee(String firstName, String password,String email,int typeOfEmployee) {
    this.firstName = firstName;
    this.password = password;
    this.email = email;
    this.typeOfEmployee = typeOfEmployee;
    this.timestamp = new Date().getTime();
  }
}
