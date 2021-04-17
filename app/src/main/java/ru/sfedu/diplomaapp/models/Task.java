package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
import ru.sfedu.diplomaapp.utils.forOthers.DateConverter;

@Entity(tableName  = "Task")
@ToString
@Data
@TypeConverters({DateConverter.class})
public class Task implements Serializable {
  @PrimaryKey(autoGenerate = true)
  private long id;

  private String taskDescription;

  private double money;

  private int scrumMaster, status ,taskType ;

  private Long createdDate,deadline,lastUpdate;

  public Task () { }
}
