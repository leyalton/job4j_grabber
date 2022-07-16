package ru.job4j.grabber;

import ru.job4j.grabber.utils.Post;

import java.sql.SQLException;
import java.util.List;

public interface Store {
    void save(Post post) throws Exception;
    List<Post> getAll() throws Exception;
    Post findById(int id) throws SQLException;
}