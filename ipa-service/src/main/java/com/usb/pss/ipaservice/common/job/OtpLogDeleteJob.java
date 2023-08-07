package com.usb.pss.ipaservice.common.job;

import com.usb.pss.ipaservice.admin.model.entity.OtpLog;
import com.usb.pss.ipaservice.admin.repository.OtpLogRepository;
import com.usb.pss.ipaservice.common.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

/**
 * @author Junaid Khan Pathan
 * @date Aug 07, 2023
 */

@Configuration
@RequiredArgsConstructor
public class OtpLogDeleteJob {
    private final OtpLogRepository repository;
    private final PlatformTransactionManager transactionManager;
    private final Integer pageSize = 100;
    private final Integer chunkSize = 1000;

    @Bean
    public ItemReader<OtpLog> otpLogItemReader() {
        return new RepositoryItemReaderBuilder<OtpLog>()
            .repository(repository)
            .methodName("findAll")
            .pageSize(pageSize)
            .sorts(Map.of("id", Sort.Direction.ASC))
            .saveState(false)
            .build();
    }

    @Bean
    public ItemWriter<OtpLog> otpLogItemWriter() {
        return new RepositoryItemWriterBuilder<OtpLog>()
            .repository(repository)
            .methodName("delete")
            .build();
    }

    @Bean
    public Step otpLogDeleteJobStep(JobRepository jobRepository) {
        return new StepBuilder("otpLogDeleteJobStep", jobRepository)
            .<OtpLog, OtpLog>chunk(chunkSize, transactionManager)
            .reader(otpLogItemReader())
            .writer(otpLogItemWriter())
            .build();
    }

    @Bean
    public Job createOtpLogDeleteJob(JobRepository jobRepository) {
        return new JobBuilder(ApplicationConstants.OTP_LOG_DELETE_JOB_NAME, jobRepository)
            .flow(otpLogDeleteJobStep(jobRepository))
            .end()
            .build();
    }
}
