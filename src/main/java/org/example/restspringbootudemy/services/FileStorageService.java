package org.example.restspringbootudemy.services;

import org.example.restspringbootudemy.config.FileStorageConfig;
import org.example.restspringbootudemy.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path root;

    @Autowired
    public FileStorageService(FileStorageConfig config) {
        this.root = Paths.get(config.getUpload_dir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.root);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                throw new FileStorageException("The file name contains invalid path sequence: " + filename);
            }
            Path target = this.root.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new FileStorageException("Could not store the file: " + filename, e);
        }
    }

}
