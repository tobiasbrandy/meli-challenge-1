package com.tobiasbrandy.challenge.meli1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.tobiasbrandy.challenge.meli1.config",
    "com.tobiasbrandy.challenge.meli1.resources",
    "com.tobiasbrandy.challenge.meli1.services",
})
public class MainWebapp {

    public static void main(String[] args) {
        SpringApplication.run(MainWebapp.class, args);
    }

}
