package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        ParseDate parseDate = new ParseDate();
        for (int i = 1, k = 1; i <= 30; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            k++;
            for (Element td : row) {
                Element href = td.child(0);
                Element date = td.lastElementSibling();
                System.out.println(href.attr("href"));
                System.out.println(k + ". " + href.text()
                        + "   || "
                        + parseDate.parse(date.text()));
                k++;
            }
        }
    }
}
