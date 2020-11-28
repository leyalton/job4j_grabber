package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SqlRuParse {
    String parse(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy, HH:mm", Locale.forLanguageTag("ru"));

        if (date.contains("сегодня")) {
            return dateFormat.format(cal.getTime());
        }
        if (date.contains("вчера")) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            return dateFormat.format(cal.getTime());
        }
        String[] arr = date.split(" ");
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy г., в HH:mm", Locale.forLanguageTag("ru"));
        cal.setTime(dateFormat.parse(str));
        return sdf.format(cal.getTime());
    }

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements elements = doc.select(".postslisttopic");
        for (Element header : elements) {
            Element href = header.child(0);
            Element date = href.parent().parent().child(5);
            SqlRuParse sqlRuParse = new SqlRuParse();
            System.out.println(href.text() + " - " + sqlRuParse.parse(date.text()));
        }
    }
}