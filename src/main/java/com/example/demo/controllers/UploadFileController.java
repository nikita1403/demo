package com.example.demo.controllers;

import com.example.demo.service.mib.MIBFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
@RequestMapping("/api/upload")
@RestController()
public class UploadFileController {
    @Autowired
    private MIBFileService mibFileService;
    @PostMapping("/mibFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Нет файла!";
        }
        String originalFilename = file.getOriginalFilename();
        File mibFile = null;
        try {
            mibFile = File.createTempFile("temp_",  "_" + file.getOriginalFilename());
            try(InputStream inputStream = file.getInputStream())
            {
                Files.copy(inputStream, mibFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mibFileService.parseAndSaveMIBFile(originalFilename, mibFile);
        mibFile.delete();
        return "MIB-файл успешно загружен!";
    }
}
