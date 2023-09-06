package com.qiuguan.schedule.controller;

import com.qiuguan.schedule.config.ScheduleConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fu yuan hui
 * @date 2023-07-03 14:13:25 Monday
 */
@Slf4j
@AllArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleConfig scheduleConfig;

    @GetMapping("/updateCron")
    public String dynamicSchedule(String cron){
        log.info("new cron: {}", cron);

        this.scheduleConfig.setNewCron(cron);

        return "ok";
    }
}
