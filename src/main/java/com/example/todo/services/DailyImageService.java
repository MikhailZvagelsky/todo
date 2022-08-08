package com.example.todo.services;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface DailyImageService {
    InputStream getDailyImage() throws FileNotFoundException;
}
