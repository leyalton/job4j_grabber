package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream rabbitProperties = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            properties.load(rabbitProperties);

            //конфигурирование
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            //создание задачи
            JobDetail job = newJob(Rabbit.class).build();

            //создание расписания с чтением из properties
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(properties.getProperty("rabbit.interval")))
                    .repeatForever();

            //задача выполняется через триггер
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();

            //загрузка задачи и триггера в планировщик
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException | IOException se) {
            se.printStackTrace();
        }
    }

    //quartz каждый раз создает объект с типом org.quartz.Job. Вам нужно создать класс реализующий этот интерфейс.
    //Внутри этого класса нужно описать требуемые действия. В нашем случае - это вывод на консоль текста.
    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}