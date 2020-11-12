package com.example.threehealthymeals.api;

import com.example.threehealthymeals.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/files")
@RestController
public class FileApi {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile file) throws IOException {
        String fileName = fileService.store(file);
        return ResponseEntity.ok(fileName);
    }
}
