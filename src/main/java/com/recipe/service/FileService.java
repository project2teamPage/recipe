package com.recipe.service;

import com.recipe.constant.UploadType;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.recipe-path}")
    private String recipeUploadPath;

    @Value("${file.post-path}")
    private String postUploadPath;

    public String uploadFile(String originalFileName, byte[] fileData, UploadType type) throws IOException {
        String folderPath = (type == UploadType.RECIPE) ? recipeUploadPath : postUploadPath;

        UUID uuid = UUID.randomUUID();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String saveName = uuid.toString() + ext;
        String fullPath = folderPath + "/" + saveName;

        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            fos.write(fileData);
        }

        return saveName;
    }

}


