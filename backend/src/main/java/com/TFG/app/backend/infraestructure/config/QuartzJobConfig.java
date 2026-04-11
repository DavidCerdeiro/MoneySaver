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
    public JobDetail periodicSpendingJobDetail() {
        return JobBuilder.newJob(PeriodicSpendingJob.class)
                .withIdentity("periodicSpendingJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger periodicSpendingTrigger(JobDetail periodicSpendingJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(periodicSpendingJobDetail)
                .withIdentity("periodicSpendingTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ?")) 
                .build();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            SpringBeanJobFactory jobFactory,
            Trigger resetCategoryTrigger,
            JobDetail resetCategoryJobDetail, 
            Trigger periodicSpendingTrigger,     
            JobDetail periodicSpendingJobDetail  
    ) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        
        factory.setJobDetails(resetCategoryJobDetail, periodicSpendingJobDetail);
        factory.setTriggers(resetCategoryTrigger, periodicSpendingTrigger);
        
        return factory;
    }
}
