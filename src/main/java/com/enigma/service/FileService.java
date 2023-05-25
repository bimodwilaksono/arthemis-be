package com.enigma.service;

import com.enigma.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String uploadFile(MultipartFile multipartFile){
        return fileRepository.store(multipartFile);
    }

    public Resource downloadMaterial(String filename){
        return fileRepository.load(filename);
    }
}
