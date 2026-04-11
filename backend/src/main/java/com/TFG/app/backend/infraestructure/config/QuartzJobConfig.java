package com.TFG.app.backend.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.quartz.*;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.TFG.app.backend.infraestructure.jobs.CategoryMonthlyJob;
import com.TFG.app.backend.periodic_spending.job.PeriodicSpendingJob;

@Configuration
public class QuartzJobConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public JobDetail resetCategoryJobDetail() {
        return JobBuilder.newJob(CategoryMonthlyJob.class)
                .withIdentity("resetCategoryJob")
                .storeDurably()
                .build();
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutowiringSpringBeanJobFactory(applicationContext.getAutowireCapableBeanFactory());
    }

    @Bean
    public Trigger resetCategoryTrigger(JobDetail resetCategoryJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(resetCategoryJobDetail)
                .withIdentity("resetCategoryTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 * ?"))
                .build();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            SpringBeanJobFactory jobFactory,
            Trigger resetCategoryTrigger,
            JobDetail resetCategoryJobDetail
    ) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setJobDetails(resetCategoryJobDetail);
        factory.setTriggers(resetCategoryTrigger);
        return factory;
    }

    @Bean
    public JobDetail periodicSpendingJobDetail() {
        return JobBuilder.newJob(PeriodicSpendingJob.class)
                .withIdentity("periodicSpendingJob")
                .storeDurably()
                .build();
    }

    // 2. Definir el Trigger para los Gastos (Ej: Todos los días a las 02:00 AM)
    @Bean
    public Trigger periodicSpendingTrigger(JobDetail periodicSpendingJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(periodicSpendingJobDetail)
                .withIdentity("periodicSpendingTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ?")) 
                .build();
    }

    // 3. ACTUALIZAR la factoría para que acepte una lista de jobs y triggers
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            SpringBeanJobFactory jobFactory,
            Trigger resetCategoryTrigger,     // Trigger 1
            JobDetail resetCategoryJobDetail, // Job 1
            Trigger periodicSpendingTrigger,     // Trigger 2 (NUEVO)
            JobDetail periodicSpendingJobDetail  // Job 2 (NUEVO)
    ) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        
        // Pasamos arrays con todos nuestros jobs y triggers
        factory.setJobDetails(resetCategoryJobDetail, periodicSpendingJobDetail);
        factory.setTriggers(resetCategoryTrigger, periodicSpendingTrigger);
        
        return factory;
    }
}
