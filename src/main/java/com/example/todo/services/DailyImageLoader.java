package com.example.todo.services;

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

        httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    @Override
    public void loadImage(File imageFile) {
        try {
            httpClient.send(request, responseHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
