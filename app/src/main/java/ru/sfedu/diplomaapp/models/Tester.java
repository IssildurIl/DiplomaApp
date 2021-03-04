package ru.sfedu.diplomaapp.models;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.sfedu.diplomaapp.models.enums.TypeOfTester;

/**
 * Class Tester
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Tester extends Developer implements Serializable {

  private TypeOfTester typeOfTester;

  public Tester () { }
}
