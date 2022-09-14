package com.example.todo.controllers;

import com.example.todo.services.DailyImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DailyImageController {

    private final DailyImageService imageService;

    @GetMapping(
            value = "/daily_image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getDailyImage() throws IOException {
        log.info("Get daily image request.");
        InputStream imageStream = imageService.getDailyImage();
        byte[] imageBytes = IOUtils.toByteArray(imageStream);
        log.info("Image byte size: " + imageBytes.length);
        return imageBytes;
    }

}
