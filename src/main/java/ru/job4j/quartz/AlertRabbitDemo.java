package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbitDemo {
    public static void main(String[] args) throws IOException {
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
        }
    }

    public void read() throws IOException {
        Properties prop = new Properties();
        try (InputStream in = AlertRabbitDemo.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            prop.load(in);
            System.out.println(prop);
        }

    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            Properties prop = new Properties();
            System.out.println("Rabbit runs here ...");
            AlertRabbitDemo alertRabbitDemo = new AlertRabbitDemo();
            try {
                alertRabbitDemo.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
