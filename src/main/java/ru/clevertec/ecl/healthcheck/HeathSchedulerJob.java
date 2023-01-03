package ru.clevertec.ecl.healthcheck;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.service.HealthCheckService;

@Component
public class HeathSchedulerJob implements Job {

    @Autowired
    private HealthCheckService checkService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        checkService.checkNodeHealth();
    }
}
