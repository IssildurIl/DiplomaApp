package ru.sfedu.diplomaapp.models;


import androidx.annotation.CallSuper;
import androidx.room.Entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.sfedu.diplomaapp.models.enums.TypeOfTester;

@ToString
@Data
@Entity(tableName = "Tester")
@EqualsAndHashCode(callSuper = true)
public class Tester extends Developer {

  private int typeOfTester;


  public Tester () { }

}
