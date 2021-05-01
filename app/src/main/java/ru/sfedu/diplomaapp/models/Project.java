package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.sfedu.diplomaapp.utils.forOthers.DateConverter;

@Data
@EqualsAndHashCode
@ToString
@Entity(tableName = "Project")
public class Project implements Serializable {
  @PrimaryKey(autoGenerate = true)
  private long _id;

  private String title,description;
  private int taskNumber;
  @TypeConverters({DateConverter.class})
  private Long takeIntoDevelopment;

  public Project () { }


  public Project(String title,String description) {
    this.title = title;
    this.description = description;
  }
}
