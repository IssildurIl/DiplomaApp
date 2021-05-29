package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.Ignore;

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
  @Ignore
  public DevelopersTask(String taskName, String taskDescription, Long employeeId, Long projectId,int status,  long createdDate, long deadline) {
    super.taskName = taskName;
    super.taskDescription = taskDescription;
    super.employeeId = employeeId;
    super.projectId = projectId;
    super.status = status;
    super.createdDate = createdDate;
    super.deadline = deadline;
  }
}
