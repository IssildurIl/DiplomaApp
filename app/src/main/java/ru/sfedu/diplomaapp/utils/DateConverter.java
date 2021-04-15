package ru.sfedu.diplomaapp.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Long dateToTimestamp(Date createdDate) {
        if (createdDate == null) {
            return null;
        } else {
            return createdDate.getTime();
        }
    }
}
