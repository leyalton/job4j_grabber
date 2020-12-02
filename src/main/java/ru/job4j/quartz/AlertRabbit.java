package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    private Properties properties;
    Connection connection = null;

    // Чтение из конфигурационного файла
    // Properties properties = new Properties();
    public void start() {
        try (InputStream inputStream = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            properties.load(inputStream);
            // подключение к бд
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password")); {
                JobDataMap data = new JobDataMap();
                data.put("connection", connection);

                // 1. Конфигурирование.
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();

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
            }
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AlertRabbit().start();
    }
}
