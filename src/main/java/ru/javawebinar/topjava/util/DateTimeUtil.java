package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static <T>  boolean isBetween(T lt, T start, T end) {
        boolean result = false;
        if(lt instanceof LocalDate){
            result = ((LocalDate) lt).compareTo((LocalDate)start) >= 0 &&
                     ((LocalDate) lt).compareTo((LocalDate)end) <= 0;
        }else if(lt instanceof LocalTime){
            result = ((LocalTime) lt).compareTo((LocalTime)start) >= 0 &&
                     ((LocalTime) lt).compareTo((LocalTime)end) <= 0;
        }
        return result;
    }

    public static String toString(Object ldt) {
        String result = "";
        if (ldt instanceof LocalDateTime) {
            result = ((LocalDateTime) ldt).format(DATE_TIME_FORMATTER);
        } else if (ldt instanceof LocalDate) {
            result = ((LocalDate) ldt).format(DATE_FORMATTER);
        } else if (ldt instanceof LocalTime) {
            result = ((LocalTime) ldt).format(TIME_FORMATTER);
        }
        return result;
    }

    public static LocalDate getDate(String startDateStr) {
        try {
            return LocalDate.parse(startDateStr, DATE_FORMATTER);
        }catch (Exception e){
            return null;
        }
    }

    public static LocalTime getTime(String startTimeStr) {
        try {
            return LocalTime.parse(startTimeStr, TIME_FORMATTER);
        }catch (Exception e){
            return null;
        }
    }


}
