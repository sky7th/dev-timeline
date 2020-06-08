package com.sky7th.devtimeline.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class Scheduler {

    private final Job job;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0/5 0 * * ?")
    public void crawlingBatch() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder()
                .addDate("requestDate", new Date()).toJobParameters());
    }

}
