package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    public static void main(String[] args) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream("rabbit.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String time = properties.getProperty("rabbit.interval");
            try {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                JobDetail job = newJob(Rabbit.class).build();
                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(Integer.parseInt(time))
                        .repeatForever();
                Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);
            } catch (SchedulerException se) {
                se.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}