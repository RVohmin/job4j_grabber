package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SqlRuParse {

    private String stringToDate1(String stringDate) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        if (stringDate.contains("сегодня")) {
            Date date = new Date();
            return date.toString();
        }
        if (stringDate.contains("вчера")) {
            Calendar cal = GregorianCalendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -1);
            Date date = cal.getTime();
            return date.toString();
        }
        String[] arr = stringDate.split(" ");
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
        StringBuilder sb = new StringBuilder();
        String str = String.join(" ", arr);
        Date date2 = new SimpleDateFormat("d MMM yy, H:m").parse(str);
        System.out.println("---------" + date2);
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date2);
    }

        private String stringToDate2(String stringDate) throws ParseException {
        if (stringDate.contains("сегодня")) {
            Date date = new Date();
            return date.toString();
        }
        if (stringDate.contains("вчера")) {
            LocalDate daysAgo = LocalDate.now().minusDays(1);
            return daysAgo.toString();
        }
        Locale locale = new Locale("ru");
        DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
        String[] shortMonths = {
                "янв", "фев", "мар", "апр", "май", "июн",
                "июл", "авг", "сен", "окт", "ноя", "дек"};
        dfs.setShortMonths(shortMonths);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yy, H:mm", locale);
        sdf.setDateFormatSymbols(dfs);
        Date date = sdf.parse(stringDate);

        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date);
    }

    private String stringToDate3(String stringDate) {
        if (stringDate.contains("сегодня")) {
            LocalDate date = LocalDate.now();
            return date.toString();
        }
        if (stringDate.contains("вчера")) {
            LocalDate daysAgo = LocalDate.now().minusDays(1);
            return daysAgo.toString();
        }

        String[] arr = stringDate.split(" ");
        switch (arr[1]) {
            case ("янв"):
                arr[1] = "января";
                break;
            case ("фев"):
                arr[1] = "февраля";
                break;
            case ("мар"):
                arr[1] = "марта";
                break;
            case ("апр"):
                arr[1] = "апреля";
                break;
            case ("май"):
                arr[1] = "мая";
                break;
            case ("июн"):
                arr[1] = "июня";
                break;
            case ("июл"):
                arr[1] = "июля";
                break;
            case ("авг"):
                arr[1] = "августа";
                break;
            case ("сен"):
                arr[1] = "сентября";
                break;
            case ("окт"):
                arr[1] = "октября";
                break;
            case ("ноя"):
                arr[1] = "ноября";
                break;
            case ("дек"):
                arr[1] = "декабря";
                break;
            default:
                break;
        }
        String str = String.join(" ", arr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yy, HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMMM y, HH:mm"));
    }
    public static void main(String[] args) throws Exception {

        SqlRuParse o = new SqlRuParse();
        for (int i = 1, k = 1; i <= 30; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            k++;
            for (Element td : row) {
                Element href = td.child(0);
                Element date = td.lastElementSibling();
                System.out.println(href.attr("href"));
                System.out.println(k + ". " + href.text() + "   || " + o.stringToDate3(date.text()));
                k++;
            }
        }
    }
}
