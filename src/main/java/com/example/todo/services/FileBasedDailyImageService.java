package com.example.todo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class FileBasedDailyImageService implements DailyImageService {

    private final File imageFile;
    private final DailyImageProvider imageProvider;

    public FileBasedDailyImageService(
            @Value("${image.file}") String imageFilePath,
            DailyImageProvider imageProvider) {
        this.imageFile = new File(imageFilePath);
        this.imageProvider = imageProvider;
    }

    @Override
    public InputStream getDailyImage() throws FileNotFoundException {
        if (!imageFile.exists() || !isCurrentDayImage(imageFile) || isEmptyOrFailedToFind(imageFile)) {
            imageProvider.loadImage(imageFile);
        } else {
            log.info("Load image from file.");
        }
        return new FileInputStream(imageFile);
    }

    private boolean isCurrentDayImage(File imageFile) {
        if (!imageFile.exists()) return false;
        long lastModifiedMillis = imageFile.lastModified();
        ZonedDateTime imageDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(lastModifiedMillis), ZoneId.systemDefault());
        LocalDate imageDate = imageDateTime.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return currentDate.equals(imageDate);
    }

    private boolean isEmptyOrFailedToFind(File imageFile) {
        try {
            return Files.size(imageFile.toPath()) == 0;
        } catch (IOException ex) {
            log.error("Failed to determine image file size.");
            log.error(ex.getMessage());
            return true;
        }
    }
}
