package ru.sfedu.diplomaapp.models;



import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.sfedu.diplomaapp.models.enums.ProgrammingLanguage;
import ru.sfedu.diplomaapp.models.enums.TypeOfDevelopers;
/**
 * Class Developer
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Developer extends Employee implements Serializable {

  private TypeOfDevelopers status;

  private ProgrammingLanguage programmingLanguage;

  public Developer () { }
}
