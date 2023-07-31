package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import model.MonthType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Utils {

public static String loginUser;
    public static String getMonthName(int monthNumber) {
        String monthName = switch (monthNumber) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "";
        };

        return monthName;
    }

    public static ObservableList<String> getTime(int startTime, int endTime)
    {
        ObservableList<String> listTime = FXCollections.observableArrayList();
        for (int hour = startTime; hour <= endTime; hour++) {
            listTime.add(String.format("%02d", hour) + ":" + "00");
            listTime.add(String.format("%02d", hour) + ":" + "30");
        }
        return listTime;
    }
    public static Date convertLocalDateTimeToDateUsingInstant(LocalDateTime dateToConvert) {
    return java.util.Date
                   .from(dateToConvert.atZone(ZoneId.systemDefault())
                                 .toInstant());
}

}

