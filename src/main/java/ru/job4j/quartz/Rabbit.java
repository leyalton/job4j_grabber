package ru.job4j.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.sql.*;

public class Rabbit implements Job {
    Connection connection;

    // создание таблицы SQL
    public void creatTable() {
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(
                    "create table if not exists rabbit (" +
                            "id serial primary key not null," +
                            "date varchar(50) not null)");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            this.connection = (Connection) jobExecutionContext.getJobDetail().getJobDataMap().get("connection");
            this.creatTable();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into grabber.public.rabbit (created) values (?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}