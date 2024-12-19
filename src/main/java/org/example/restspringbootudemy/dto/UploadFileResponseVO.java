package org.example.restspringbootudemy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UploadFileResponseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
