package com.usb.pss.ipaservice.common.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author Junaid Khan Pathan
 * @date Aug 07, 2023
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JobUtil {
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @Async
    public void runJob(Job job, String jobName) {
        if (!isJobRunning(jobName)) {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
            try {
                jobLauncher.run(job, jobParameters);
            } catch (Exception e) {
                log.error("Failed to launch job:" + jobName);
                log.error(e.getMessage());
            }
        }
    }

    public boolean isJobRunning(String jobName) {
        JobInstance jobInstance = jobExplorer.getLastJobInstance(jobName);

        if (Objects.isNull(jobInstance)) {
            return false;
        }

        List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);

        for (JobExecution jobExecution : jobExecutions) {
            if (jobExecution.getStatus().isRunning()) {
                return true;
            }
        }

        return false;
    }
}
