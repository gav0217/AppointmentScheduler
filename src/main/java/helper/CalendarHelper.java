package helper;

import javafx.util.Pair;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarHelper {
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

private static Calendar getCalendarForNow() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(new Date());
    return calendar;
}

private static void setTimeToBeginningOfDay(Calendar calendar) {
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
}

private static void setTimeToEndofDay(Calendar calendar) {
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
}
}
