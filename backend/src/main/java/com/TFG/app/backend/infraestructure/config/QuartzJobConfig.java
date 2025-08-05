package com.TFG.app.backend.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.quartz.*;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.TFG.app.backend.infraestructure.jobs.CategoryMonthlyJob;

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
}
