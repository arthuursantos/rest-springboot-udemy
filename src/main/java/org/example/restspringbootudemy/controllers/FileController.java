package org.example.restspringbootudemy.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.restspringbootudemy.config.FileStorageConfig;
import org.example.restspringbootudemy.dto.UploadFileResponseVO;
import org.example.restspringbootudemy.services.FileStorageService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "File Endpoint")
@RestController
@RequestMapping(value = "/api/file")
public class FileController {

    private final Logger logger = Logger.getLogger(FileController.class.getName());

    @Autowired
    private FileStorageService service;

    @PostMapping(value = "/upload")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Storing file to disk: " + file.getOriginalFilename());
        var filename = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/download/").path(filename).toUriString();
        return UploadFileResponseVO.builder()
                .fileName(filename)
                .fileDownloadUri(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
    }

    @PostMapping(value = "/uploads")
    public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        logger.info("Storing files to disk");
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

}
