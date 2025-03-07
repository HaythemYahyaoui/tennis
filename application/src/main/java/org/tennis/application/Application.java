package org.tennis.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
@ImportResource({"classpath*:beans/beans.xml"})
@ComponentScan(basePackages = {"org.tennis"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}