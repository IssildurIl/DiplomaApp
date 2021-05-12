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

  public TestersTask(String taskName, String taskDescription, Long employeeId, Long projectId,int status,  long createdDate, long deadline) {
    super.taskName = taskName;
    super.taskDescription = taskDescription;
    super.employeeId = employeeId;
    super.projectId = projectId;
    super.status = status;
    super.createdDate = createdDate;
    super.deadline = deadline;
  }
}
