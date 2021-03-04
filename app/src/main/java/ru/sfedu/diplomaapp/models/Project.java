package ru.sfedu.diplomaapp.models;


import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
public class Project implements Serializable {

  private long id;

  private String title;

  private String takeIntoDevelopment;

  private List<Task> task;

  public Project () { }
}
