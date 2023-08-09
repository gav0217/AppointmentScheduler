package helper;

import javafx.util.Pair;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This is a Calendar helper class.  It gets the current date and is used to create a filter for the appointment views.
 *
 * @author Gavril Fofiu
 */

public class CalendarHelper {
/**
 * This method gets the current date. This method gets the current date and sets a range for weekly views or monthly
 * views.
 *
 * @param period is passed through to get a range.
 * @return the date pairs.
 */
public static Pair<Date, Date> getDateRange(int period) {
    Date begining, end;

    {
        Calendar calendar = getCalendarForNow();
        calendar.set(period, calendar.getActualMinimum(period));
        setTimeToBeginningOfDay(calendar);
        begining = calendar.getTime();
    }

    {
        Calendar calendar = getCalendarForNow();
        calendar.set(period, calendar.getActualMaximum(period));
        setTimeToEndofDay(calendar);
        end = calendar.getTime();
    }

    return new Pair(begining, end);
}

/**
 * This method gets date.  The method looks for the current date.
 *
 * @return the current date.
 */

private static Calendar getCalendarForNow() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(new Date());
    return calendar;
}

/**
 * This method returns a date.  Returns the start date of the range.
 *
 * @param calendar the start date in the calendar.
 */
private static void setTimeToBeginningOfDay(Calendar calendar) {
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
}

/**
 * This method returns a date.  Returns the end date of the range.
 *
 * @param calendar the end date in the calendar.
 */

private static void setTimeToEndofDay(Calendar calendar) {
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
}
}
