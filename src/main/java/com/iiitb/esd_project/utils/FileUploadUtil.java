package com.iiitb.esd_project.utils;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static void copyFile(String source, String dest) throws IOException{
        Path sourcePath = Paths.get(source);
        Path destPath = Paths.get(dest);
        System.out.println(sourcePath);
        System.out.println(destPath);
        if (!Files.exists(destPath)) {
            Files.createDirectories(destPath);
        }
        try{
            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IOException("Could not copy image file: " + source, ioe);
        }
    }
}