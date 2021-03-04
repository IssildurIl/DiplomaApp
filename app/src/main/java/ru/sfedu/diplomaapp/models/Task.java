package ru.sfedu.diplomaapp.models;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import ru.sfedu.diplomaapp.models.enums.TaskTypes;
import ru.sfedu.diplomaapp.models.enums.TypeOfCompletion;

@Data
public class Task implements Serializable  {

  private long id;

  private String taskDescription;

  private double money;

  private Employee scrumMaster;

  private TypeOfCompletion status;

  private List<Employee> team;

  private Date createdDate;

  private Date deadline;

  private Date lastUpdate;

  private TaskTypes taskType;

  public Task () { }



}
