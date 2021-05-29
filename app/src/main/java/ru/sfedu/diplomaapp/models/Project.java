package ru.sfedu.diplomaapp.models;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

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
  private String taskNumber;
  @TypeConverters({DateConverter.class})
  private Long takeIntoDevelopment;
  protected long timestamp;
  public Project () { }

  @Ignore
  public Project(String title,String description) {
    this.title = title;
    this.description = description;
    this.timestamp = new Date().getTime();
  }
}
