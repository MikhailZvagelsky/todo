package com.example.todo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

@Slf4j
@Service
public class DailyImageLoader implements DailyImageProvider {

    private final HttpClient httpClient;
    private final HttpRequest request;

    private final HttpResponse.BodyHandler<Path> responseHandler;

    public DailyImageLoader(
            @Value("${image.url}") String imageUrl,
            @Value("${image.file}") String imageFile
    ) throws URISyntaxException {

        URI imageUri = new URI(imageUrl);
        request = HttpRequest.newBuilder(imageUri).GET().build();

        responseHandler = HttpResponse.BodyHandlers.ofFile(Path.of(imageFile));

        log.info("Image url: " + imageUrl);
        log.info("Image file: " + imageFile);

        httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    @Override
    public void loadImage(File imageFile) {
        try {
            log.info("Try to load image through network.");
            httpClient.send(request, responseHandler);
        } catch (IOException | InterruptedException e) {
            log.error("Failed to download image with client.");
            log.error(httpClient.toString());
            throw new RuntimeException(e);
        }
    }
}
