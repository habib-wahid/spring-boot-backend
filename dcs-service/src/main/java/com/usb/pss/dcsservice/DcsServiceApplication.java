package com.usb.pss.dcsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DcsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcsServiceApplication.class, args);

        System.out.println("Hello from DCS Service");
    }

}
