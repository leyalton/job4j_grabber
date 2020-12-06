package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;

import java.io.IOException;


public class SqlRuParse {

    public Post getPostData(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element row = doc.select(".msgTable").first();
        String name = row.select(".messageHeader").first().select("td:matchText").text();
        String text = row.select(".msgBody").last().text();
        String created = row.select(".msgFooter").text();
        return new Post(name, text, url, created);
    }

    public static void main(String[] args) throws Exception {

            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/").get();
            Elements elements = doc.select(".postslisttopic");
            for (Element header : elements) {
                Element href = header.child(0);
                Element date = href.parent().parent().child(5);
                SqlRuParse sqlRuParse = new SqlRuParse();
                // парсинг сссылки
                // System.out.println(href.attr("href"));
                // парсинг названия ссылки и дата
                System.out.println(href.text() + " - " + Date.parse(date.text()));
                // System.out.println(sqlRuParse.getPostData(href.attr("href")));
        }
    }
}