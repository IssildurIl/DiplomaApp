package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.sfedu.diplomaapp.models.enums.DeveloperTaskType;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity(tableName = "DevelopersTask")
public class DevelopersTask extends Task{

  private String developerComments;

  private int developerTaskType;

  public DevelopersTask () { }
}
