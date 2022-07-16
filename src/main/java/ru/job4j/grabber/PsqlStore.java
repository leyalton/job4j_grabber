package ru.job4j.grabber;

import ru.job4j.grabber.utils.HabrCareerDateTimeParser;
import ru.job4j.grabber.utils.Post;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private final Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            String url = cfg.getProperty("jdbc.url");
            String login = cfg.getProperty("jdbc.username");
            String password = cfg.getProperty("jdbc.password");
            cnn = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) throws Exception {
        String sql = "insert into post(name, text, link, created) values (?, ?, ?, ?) on conflict (link) do nothing;";
        try (PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.execute();
        }
    }

    @Override
    public List<Post> getAll() throws Exception {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement prepState = cnn.prepareStatement("select * from post;")) {
            ResultSet resultSet = prepState.executeQuery();
            while (resultSet.next()) {
                Post post = new Post(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
                posts.add(post);
            }
            return posts;
        }
    }

    @Override
    public Post findById(int id) throws SQLException {
        Post post = new Post();
        String sql = String.format("select id, name, text, link, created from post where id = %s;", id);
        try (PreparedStatement prepState = cnn.prepareStatement(sql)) {
            ResultSet resultSet = prepState.executeQuery();
            while (resultSet.next()) {
                post = new Post(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
            }
            return post;
        }
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream("app.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            try (PsqlStore psqlStore = new PsqlStore(properties)) {
                HabrCareerDateTimeParser dateParser = new HabrCareerDateTimeParser();
                HabrCareerParse habrCareerParse = new HabrCareerParse(dateParser);
                String link = String.format("%s/vacancies/java_developer", "https://career.habr.com");

                for (Post vacancy : habrCareerParse.list(link)) {
                    System.out.println("проверка строки");
                    System.out.println(vacancy);
                    psqlStore.save(vacancy);
                }
                System.out.println("выводим все");
                System.out.println(psqlStore.getAll());
                System.out.println(psqlStore.findById(340));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


