package com.startnow.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class StartNowApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartNowApplication.class, args);
    }
}
