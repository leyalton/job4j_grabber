package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse extends Date{

    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 5; i++){
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements elements = doc.select(".postslisttopic");
            for (Element header : elements) {
                Element href = header.child(0);
                Element date = href.parent().parent().child(5);
                // парсинг сссылки
                // System.out.println(href.attr("href"));
                // парсинг названия ссылки и дата
                // System.out.println(href.text() + " - " + sqlRuParse.parse(date.text()));
                System.out.println(parse(date.attr("href")));
            }
        }
    }
}