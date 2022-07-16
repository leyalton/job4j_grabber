package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;
import ru.job4j.grabber.utils.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {
    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);
    private static final int FIRST_PAGES = 1;
    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
            Document document;
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= FIRST_PAGES; i++) {
            Connection connection = Jsoup.connect(String.format("%s?page=%s", PAGE_LINK, i));
            try {
                document = connection.get();
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row ->
                    posts.add(parsing(row)));
        }
        return posts;
    }

    public Post parsing(Element element) {
        Element titleElement = element.select(".vacancy-card__title").first();
        Element dateElement = element.select(".vacancy-card__date").first().child(0);
        Element linkElement = titleElement.child(0);
        String vacancyName = titleElement.text();
        String linkOffer = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
        String date = dateElement.attr("datetime");
        String description = this.retrieveDescription(linkOffer);
        System.out.printf("%s %s %s %s%n ", vacancyName, linkOffer, description, date);
        return new Post(vacancyName, linkOffer, description, dateTimeParser.parse(date));
    }

    private String retrieveDescription(String link) {
        String description;
        try {
            Connection connection = Jsoup.connect(link);
            Document document = connection.get();
            description = document.selectFirst(".collapsible-description").text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return description;
    }

    public static void main(String[] args) {
        HabrCareerDateTimeParser dateParser = new HabrCareerDateTimeParser();
        HabrCareerParse habrCareerParse = new HabrCareerParse(dateParser);
        habrCareerParse.list(PAGE_LINK).forEach(System.out::println);
    }
}