package com.example.democontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DemoConfig.class)
public class DemocontrollerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemocontrollerApplication.class, args);
    }

}
