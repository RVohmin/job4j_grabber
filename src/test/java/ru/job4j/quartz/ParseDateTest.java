package ru.job4j.quartz;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class ParseDateTest {

    @Test
    public void parse() throws ParseException {
        ParseDate parseDate = new ParseDate();
        String str = "5 май 19, 23:40";
        String exp = parseDate.parse(str);
        System.out.println(exp);
        String rsl = "05 мая 2019, 23:40";
        assertEquals(rsl, exp);
    }
}