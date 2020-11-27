package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements elements = doc.select(".postslisttopic");
        Elements elementsDate = doc.select(".altCol");
        for (Element header : elements) {
            Element href = header.child(0);
            System.out.println(href.text() + " - " + href.parent().parent().child(5).text());
        }
    }
}