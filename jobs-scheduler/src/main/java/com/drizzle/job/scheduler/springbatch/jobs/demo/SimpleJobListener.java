package com.drizzle.job.scheduler.springbatch.jobs.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

/**
 * 一个简单的JOB listener
 */
public class SimpleJobListener implements JobExecutionListener {
    private static final Logger log = LoggerFactory.getLogger(SimpleJobListener.class);

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private long startTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        log.info("job before " + jobExecution.getJobParameters());
    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("JOB STATUS : {}", jobExecution.getStatus());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("JOB FINISHED");
            threadPoolTaskExecutor.destroy();
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("JOB FAILED");
        }
        log.info("Job Cost Time : {}ms", (System.currentTimeMillis() - startTime));
    }
}
