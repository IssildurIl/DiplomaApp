package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;
import ru.sfedu.diplomaapp.utils.forOthers.DateConverter;

@Entity(tableName  = "Task")
@ToString
@Data
@TypeConverters({DateConverter.class})
public class Task implements Serializable {
  @PrimaryKey(autoGenerate = true)
  protected long _id;

  protected String taskName, taskDescription;

  protected long employeeId, projectId, status;

  protected long createdDate,deadline;

  public Task () { }


  public Task(String taskName, String taskDescription, Long employeeId, Long projectId,int status,  long createdDate, long deadline) {
    this.taskName = taskName;
    this.taskDescription = taskDescription;
    this.employeeId = employeeId;
    this.projectId = projectId;
    this.status = status;
    this.createdDate = createdDate;
    this.deadline = deadline;
  }


}
