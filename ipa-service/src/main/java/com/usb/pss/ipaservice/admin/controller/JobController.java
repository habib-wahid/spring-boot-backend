package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.common.job.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.JOB_ENDPOINT;

/**
 * @author Junaid Khan Pathan
 * @date Aug 07, 2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(JOB_ENDPOINT)
@Tag(name = "Job Controller", description = "API Endpoints for batch job operations.")
public class JobController {
    private final JobService jobService;

    @PostMapping("/runOtpLogDeleteJob")
    @Operation(summary = "Endpoint to trigger OTP log delete job")
    public void runOtpLogDeleteJob() throws Exception {
        jobService.runOtpLogDeleteJob();
    }
}
