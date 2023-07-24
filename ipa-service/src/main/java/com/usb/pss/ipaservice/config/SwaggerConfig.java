package com.usb.pss.ipaservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_NAME = "JWT Token";
    private static final String SECURITY_SCHEME_NAME_REFRESH_TOKEN = "Authorization"; //TODO This Line Only For development purpose

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(apiInfo());
        openAPI.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
        openAPI.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME_REFRESH_TOKEN)); //TODO This Line Only For development purpose
        openAPI.components(apiComponents());
        return openAPI;
    }

    private Info apiInfo() {
        Info info = new Info();
        info.setTitle("PSS");
        info.setDescription("Passenger Service System for Airlines");
        info.setContact(apiContact());
        info.setVersion("0.0.1");
        info.setLicense(apiLicense());
        info.setTermsOfService("PSS Terms of Services (work in progress)");
        return info;
    }

    private Contact apiContact() {
        Contact contact = new Contact();
        contact.setName("PSS");
        contact.setEmail("pss@dom.com");
        contact.setUrl("http://usbpss.com");
        return contact;
    }

    private License apiLicense() {
        License license = new License();
        license.setName("Pss License (work in progress)");
        license.setUrl("http://usbpsslicense.com");
        return license;
    }

    private Components apiComponents() {
        Components components = new Components();
        components.addSecuritySchemes(
            SECURITY_SCHEME_NAME,
            new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        // TODO  Start : Below Part Only For development purpose
        components.addSecuritySchemes(
            SECURITY_SCHEME_NAME_REFRESH_TOKEN,
            new SecurityScheme()
                .name(SECURITY_SCHEME_NAME_REFRESH_TOKEN)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat(SECURITY_SCHEME_NAME_REFRESH_TOKEN)
        );
           //TODO END
        return components;
    }
}
