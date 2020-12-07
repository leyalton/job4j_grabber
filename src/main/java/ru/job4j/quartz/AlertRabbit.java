package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    private Properties properties;

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
                    .withIntervalInSeconds(Integer.parseInt(properties.getProperty("rabbit.interval")))
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
        try (InputStream inputStream =
                     AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            this.properties = new Properties();
            this.properties.load(inputStream);
            Class.forName(this.properties.getProperty("driver"));
            connection = DriverManager.getConnection(
                    this.properties.getProperty("url"),
                    this.properties.getProperty("username"),
                    this.properties.getProperty("password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        new AlertRabbit().start();
    }
}