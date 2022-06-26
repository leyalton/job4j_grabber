package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {

    public static void main(String[] args) throws ClassNotFoundException {
        Properties properties = new Properties();
        Class.forName("org.postgresql.Driver");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream in = classloader.getResourceAsStream("rabbit.properties")) {
            properties.load(in);
            String url = properties.getProperty("jdbc.url");
            String login = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            try (Connection connection = DriverManager.getConnection(url, login, password)) {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                JobDataMap data = new JobDataMap();
                data.put("connection", connection);
                JobDetail job = newJob(Rabbit.class)
                        .usingJobData(data)
                        .build();
                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever();
                Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);
                Thread.sleep(10000);
                scheduler.shutdown();
                System.out.println(connection);
            } catch (Exception se) {
                se.printStackTrace();
            }
        } catch (IOException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            try (Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection")) {
                try (Statement statement = connection.createStatement()) {
                    String sql = String.format("insert into rabbit (create_date) values (" + System.currentTimeMillis() + ")");
                    statement.execute(sql);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}