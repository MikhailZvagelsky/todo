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
    private final String PORT_ENV_VARIABLE_NAME = "PORT";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String port = environment.getProperty(PORT_ENV_VARIABLE_NAME);
        System.out.println("Server started in port (get from environment object): " + port);
        System.out.println("Server started in port (get from application properties): " + serverPort);
    }
}
