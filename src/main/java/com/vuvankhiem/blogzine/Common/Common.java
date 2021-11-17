package com.vuvankhiem.blogzine.Common;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Common {

    public String randomCode() {
        return RandomStringUtils.randomAlphabetic(12);
    }

    public String getCurrentDate(int type) {
        String currentDate = "";
        Date date = new Date();
        DateFormat dateFormat;
        switch (type) {
            case 1: {
                dateFormat = new SimpleDateFormat("MMM dd, yyyy ' at ' hh:mm a", Locale.ENGLISH);
                currentDate = dateFormat.format(date);
                break;
            }
            case 2: {
                dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                currentDate = dateFormat.format(date);
                break;
            }
        }
        return currentDate;
    }

    protected String saveFile(MultipartFile multipartFile, String rootPath) throws IOException {
        if (multipartFile != null || !multipartFile.isEmpty()) {
            byte[] bytes = multipartFile.getBytes();
            File dir = new File(rootPath);
            Path p = Paths.get(rootPath);
            if (!Files.exists(p)) {
                try {
                    Files.createDirectories(p);
                } catch (IOException e) {

                }
            } else {
                String fileName = multipartFile.getOriginalFilename();
                File fileServer = new File(dir.getAbsoluteFile() + File.separator + fileName);
                try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileServer))) {
                    outputStream.write(bytes);
                }
                return fileName;
            }
        }
        return null;
    }
}
