package com.qiuguan.schedule.config;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author fu yuan hui
 * @date 2023-07-03 14:16:37 Monday
 */
@Setter
@EnableScheduling
@Slf4j
@PropertySource("classpath:/task-config.ini")
@Component
public class ScheduleConfig implements SchedulingConfigurer {

    @Value("${schedule.corn}")
    private String cron;

    private String newCron;

    /**
     * 注意：修改使，如果url传入是一个错误的cron, 比如 null, 那么定时任务就终止了！！！！
     *
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                log.info("当前时间：{}", LocalDateTime.now());
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {

                //如果表达式正确，就按照新的表达式执行任务，否则就按照原来的cron执行任务
                if (CronExpression.isValidExpression(newCron)) {
                    CronTrigger cronTrigger = new CronTrigger(newCron);
                    return cronTrigger.nextExecutionTime(triggerContext);
                }

                CronTrigger cronTrigger  = new CronTrigger(cron);
                return cronTrigger.nextExecutionTime(triggerContext);
            }
        });
    }
}
