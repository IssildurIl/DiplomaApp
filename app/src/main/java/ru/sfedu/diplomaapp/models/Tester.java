package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.Ignore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Data
@Entity(tableName = "Tester")
@EqualsAndHashCode(callSuper = true)
public class Tester extends Developer {

  protected int typeOfTester;


  public Tester () { }
  @Ignore
  public Tester(String firstName, String password,String email,int typeOfEmployee) {
    super.firstName = firstName;
    super.password = password;
    super.email = email;
    super.TypeOfEmployee = typeOfEmployee;
  }
}
