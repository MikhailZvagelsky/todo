package com.example.todo.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Order(1)
@Component
@RequiredArgsConstructor
public class Notifier implements ApplicationRunner {

    private final Environment environment;
    @Value("${server.port}")
    private int serverPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Port application property: " + serverPort);
        System.out.println("Read postgres credentials from folder: " + environment.getProperty("POSTGRES_CREDENTIALS_FOLDER"));
    }
}
