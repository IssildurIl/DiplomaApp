package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.Ignore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = "Developer")

public class Developer extends Employee {

  protected int status;

  protected int programmingLanguage;

  public Developer () {
  }

  @Ignore
  public Developer(String firstName, String password,String email,int typeOfEmployee) {
    super.firstName = firstName;
    super.password = password;
    super.email = email;
    super.TypeOfEmployee = typeOfEmployee;
  }

}
