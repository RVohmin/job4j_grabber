package ru.job4j.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ParseDate {
    String parse(String value) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy, HH:mm", Locale.getDefault());

        if (value.contains("сегодня")) {
            return dateFormat.format(cal.getTime());
        }
        if (value.contains("вчера")) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            return dateFormat.format(cal.getTime());
        }
        String[] arr = value.split(" ");
        arr[1] = arr[1] + ".";

        if (arr[1].equals("май.")) {
            arr[1] = "мая";
        }
        if (arr[1].equals("фев.")) {
            arr[1] = "февр.";
        }
        if (arr[1].equals("ноя.")) {
            arr[1] = "нояб.";
        }
        if (arr[1].equals("сен.")) {
            arr[1] = "сент.";
        }
        String str = String.join(" ", arr);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, в HH:mm", Locale.getDefault());
        cal.setTime(dateFormat.parse(str));
        return sdf.format(cal.getTime());
    }
}
