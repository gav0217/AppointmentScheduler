package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import model.MonthType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * This is the Utility class. This class has a few helper methods, one which converts the number portion corresponding to the month
 * into the string, which says the month.  It also holds the list for possible appointment types.  Also has a date time converter from
 * localdatetime to instant time format.
 *
 * @author Gavril Fofiu.
 */

public class Utils {

public static String loginUser;

/**
 * This method converts a number to a string.  The method changes the number of the month to the
 * name of the month as a string.
 *
 * @param monthNumber passed through as a month number.
 * @return the string depending on the number.
 */
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

/**
 * This method creates a string of times.  Creates a list of times to select appointments from.
 *
 * @param startTime when called as start time.
 * @param endTime when called as end time
 * @return an observable list of time times.
 */

public static ObservableList<String> getTime(int startTime, int endTime)
    {
        ObservableList<String> listTime = FXCollections.observableArrayList();
        for (int hour = startTime; hour <= endTime; hour++) {
            listTime.add(String.format("%02d", hour) + ":" + "00");
            listTime.add(String.format("%02d", hour) + ":" + "30");
        }
        return listTime;
    }

/**
 * This method converts the date. Changes the date from localdatetime to an instant date type.
 *
 * @param dateToConvert when called.
 * @return current date as Instant date.
 */
public static Date convertLocalDateTimeToDateUsingInstant(LocalDateTime dateToConvert) {
    return java.util.Date
                   .from(dateToConvert.atZone(ZoneId.systemDefault())
                                 .toInstant());
}

}

