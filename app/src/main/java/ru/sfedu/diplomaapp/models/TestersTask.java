package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.sfedu.diplomaapp.models.enums.BugStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity(tableName = "TestersTask")
public class TestersTask extends Task{

  private int bugStatus;

  private String bugDescription;

  public TestersTask () { }
}
