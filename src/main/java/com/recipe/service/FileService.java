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

        // 파일 확장자 체크: 확장자가 없으면 예외 발생
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 비어 있습니다.");
        }

        // 확장자 추출
        String ext = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < originalFileName.length() - 1) {
            ext = originalFileName.substring(dotIndex); // 확장자 추출
        } else {
            throw new IllegalArgumentException("유효한 확장자가 없습니다.");
        }

        // 랜덤한 UUID로 파일 이름 생성
        UUID uuid = UUID.randomUUID();
        String saveName = uuid.toString() + ext;  // 새로운 파일 이름
        String fullPath = folderPath + "/" + saveName;
        System.out.println("Saving file to: " + fullPath);  // 파일 경로 확인


        // 파일 저장
        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            fos.write(fileData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return saveName;
    }


}


