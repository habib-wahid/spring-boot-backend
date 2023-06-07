package com.usb.pss.ipaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IpaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpaServiceApplication.class, args);

		System.out.println("Hello from IPA Service");
	}

}
