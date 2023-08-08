package com.usb.pss.ipaservice.common.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

/**
 * @author Junaid Khan Pathan
 * @date Aug 07, 2023
 */

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobUtil jobUtil;
    private final JobLauncher jobLauncher;
    private final Job otpLogDeleteJob;

//    @Scheduled(cron = "0 * * * * *")
    public void runOtpLogDeleteJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(otpLogDeleteJob, jobParameters);
    }
}
