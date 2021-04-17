package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.ToString;


@Data
@ToString
@Entity(tableName = "Developer")

public class Developer extends Employee {

  private int status;

  private int programmingLanguage;

  public Developer () {
  }
}
