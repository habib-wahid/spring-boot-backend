package com.usb.pss.reportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReportingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportingServiceApplication.class, args);

		System.out.println("Hello from Reporting Service");
	}

}
