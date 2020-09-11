package android.example.searchmovies.database;

import androidx.room.TypeConverter;

import java.sql.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
