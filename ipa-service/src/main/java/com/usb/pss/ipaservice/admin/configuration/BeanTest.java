package com.usb.pss.ipaservice.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanTest {

    @Bean(name = "person")
    public Person getPerson() {
        return new Person("habib");
    }
}
