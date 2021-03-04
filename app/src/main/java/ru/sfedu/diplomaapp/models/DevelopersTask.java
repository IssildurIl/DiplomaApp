package ru.sfedu.diplomaapp.models;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.sfedu.diplomaapp.models.enums.DeveloperTaskType;

/**
 * Class DevelopersTask
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DevelopersTask extends Task implements Serializable {

  private String developerComments;
  private DeveloperTaskType developerTaskType;

  public DevelopersTask () { }
}
