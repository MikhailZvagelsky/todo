package com.example.todo.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Configuration
public class PostgresConfig {
    @Bean
    public DataSource postgresDataSource(
            @Value("${postgres.credentials.folder}") String secretsFolder,
            @Value("${postgres.credentials.passwordFile}") String passwordFile,
            @Value("${postgres.credentials.usernameFile}") String usernameFile,
            @Value("${postgres.credentials.urlFile}") String urlFile
    ) throws IOException {
        Path folder = Path.of(secretsFolder);
        String password = read(folder, passwordFile);
        String username = read(folder, usernameFile);
        String url = read(folder, urlFile);
        log.info("Look Postgres at url: " + url);
        return dataSource(url, username, password);
    }

    private String read(Path folder, String fileName) throws IOException {
        Path file = folder.resolve(fileName);
        return Files.readString(file);
    }

    private DataSource dataSource(String url, String username, String password) {
        return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

}
