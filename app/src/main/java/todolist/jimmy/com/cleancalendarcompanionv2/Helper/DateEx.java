package todolist.jimmy.com.cleancalendarcompanionv2.Helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// helper class to standardize the date and time format across the app
public class DateEx extends Date{
    static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static DateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    static DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    // default ctor
    public DateEx(){
        super();
    }

    // getters
    public static String getDateString(Date date){
        return dateFormat.format(date);
    }

    public static String getTimeString(Date date){
        return timeFormat.format(date);
    }

    public static String getDateTimeString(Date date){
        return dateTimeFormat.format(date);
    }

    // parses and returns a date given a string
    // throws a parse exception should it fail
    public static Date getDateOfDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    // parses and returns a time given a string
    // throws a parse exception should it fail
    public static Date getDateOfTime(String date) throws ParseException {
        return timeFormat.parse(date);
    }

    // parses and returns a date nad time given a string
    // throws a parse exception should it fail
    public static Date getDateOfDateTime(String date) throws ParseException {
        return dateTimeFormat.parse(date);
    }

    // returns the year of a given date
    public static int getYearOf(Date date){
        Calendar calendar = Calendar.getInstance();
        if(date == null)
            return calendar.get(Calendar.YEAR);
        else{
            calendar.clear();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        }
    }

    // returns the month of a given date
    public static int getMonthOf(Date date){
        Calendar calendar = Calendar.getInstance();
        if(date == null)
            return calendar.get(Calendar.MONTH)+1;
        else{
            calendar.clear();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH)+1;
        }
    }

    // returns the day of a given date
    public static int getDayOf(Date date){
        Calendar calendar = Calendar.getInstance();
        if(date == null)
            return calendar.get(Calendar.DAY_OF_MONTH);
        else{
            calendar.clear();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    // adds a specific number of minutes to a current date and returns it
    public static Date addMinutesTo(Date date, int noOfMinutes){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, noOfMinutes);
        return calendar.getTime();
    }

    // returns a Date that has the time 12am
    public static Date getTodayMorning(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    // returns a Date that has the time 11:59pm
    public static Date getTodayMidNight(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    // formats the
    public static String getFormattedDateString(String date) throws ParseException {
        return dateFormat.format(dateFormat.parse(date));
    }

    public static String getFormattedTimeString(String time) throws ParseException {
        return timeFormat.format(timeFormat.parse(time));
    }
}
