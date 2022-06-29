package com.tobiasbrandy.challenge.meli1;

import java.time.Clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {
    "com.tobiasbrandy.challenge.meli1.config",
    "com.tobiasbrandy.challenge.meli1.resources",
    "com.tobiasbrandy.challenge.meli1.services",
    "com.tobiasbrandy.challenge.meli1.repositories",
})
public class MainWebapp {

    public static void main(String[] args) {
        SpringApplication.run(MainWebapp.class, args);
    }

    @Bean
    public Clock applicationClock() {
        return Clock.systemUTC();
    }

}
