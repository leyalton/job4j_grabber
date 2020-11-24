package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    public static void main(String[] args) {
        Properties pr = new Properties();
        // считывания данных из файла
        try (FileInputStream in = new FileInputStream("C:\\projects\\job4j_grabber\\src\\main\\resources\\rabbit.properties")) {
            // загрузить данные, содержащие в обьекте класса Properties
            pr.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 1. Конфигурирование.
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            // 2. Создание задачи.
            JobDetail job = newJob(Rabbit.class).build();

            // 3. Создание расписания.
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(pr.getProperty("rabbit.interval")))
                    .repeatForever();

            // 4. Задача выполняется через триггер.
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();

            // 5. Загрузка задачи и триггера в планировщик
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    //quartz каждый раз создает объект с типом org.quartz.Job. нам нужно создать класс реализующий этот интерфейс.
    //Внутри этого класса нужно описать требуемые действия. В нашем случае - это вывод на консоль текста.
    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
        }
    }
}