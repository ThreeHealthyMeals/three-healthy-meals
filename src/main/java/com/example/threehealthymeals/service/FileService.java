package com.example.threehealthymeals.service;

import com.example.threehealthymeals.config.StorageProperties;
import com.example.threehealthymeals.domain.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Transactional
@Service
public class FileService {

    private final Path directory;

    @Autowired
    public FileService(StorageProperties properties) {
        this.directory = Paths.get(properties.getLocation());
    }

    private String generateFileName(String fileName){
        final String[] source = {
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "abcdefghijklmnopqrstuvwxyz",
                "0123456789"
        };
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 15; i++){
            int idx = (int)(Math.random()*100%3);
            int charIdx = (int)(Math.random()*100%source[idx].length());
            sb.append(source[idx].charAt(charIdx));
        }
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return sb.toString()+extension;
    }

    public String store(MultipartFile file) throws IOException {
        Files.createDirectories(directory);
        if(file == null){
            return "";
        }
        String fileName = generateFileName(file.getOriginalFilename());
        Path targetPath = directory.resolve(fileName).normalize();
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}

