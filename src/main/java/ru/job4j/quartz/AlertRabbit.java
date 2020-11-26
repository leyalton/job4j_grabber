package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.*;
import java.sql.*;
import java.util.*;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public void start() {
        try {
            // 1. Конфигурирование.
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put("connection", this.getConnection());

            // 2. Создание задачи.
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();

            // 3. Создание расписания.
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();

            // 4. Задача выполняется через триггер.
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();

            // 5. Загрузка задачи и триггера в планировщик
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        // считывания данных из конфигурационного файла
        try (InputStream inputStream = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties properties = new Properties();
            // загрузить данные, содержащие в обьекте класса Properties
            properties.load(inputStream);

            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        new AlertRabbit().start();
    }
}
