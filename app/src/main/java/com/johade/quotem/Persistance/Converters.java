package com.johade.quotem.Persistance;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value){
        if(value == null){
            return null;
        }
        return new Date(value);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        if(date == null){
            return null;
        }

        return date.getTime();
    }
}
