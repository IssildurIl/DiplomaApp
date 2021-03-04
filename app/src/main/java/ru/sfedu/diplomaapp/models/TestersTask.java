package ru.sfedu.diplomaapp.models;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.sfedu.diplomaapp.models.enums.BugStatus;

/**
 * Class TestersTask
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class TestersTask extends Task implements Serializable {

  private BugStatus bugStatus;

  private String bugDescription;

  public TestersTask () { }
}
