package com.example.todo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
        if (!imageFile.exists() || !isCurrentDayImage(imageFile)) {
            imageProvider.loadImage(imageFile);
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
}
