package com.example.todo.controllers;

import com.example.todo.services.DailyImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class DailyImageController {

    private final DailyImageService imageService;

    @GetMapping(
            value = "/daily_image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getDailyImage() throws IOException {
//        InputStream in = getClass()
//                .getResourceAsStream("/images/ezh-s-muhomorom.jpg");
//        return IOUtils.toByteArray(in);
        InputStream imageStream = imageService.getDailyImage();
        return IOUtils.toByteArray(imageStream);
    }

}
