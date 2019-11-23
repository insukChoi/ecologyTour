package com.insuk.ecologytour.web.response;

import lombok.Data;

@Data
public class FileUploadRes {
    final static public FileUploadRes EMPTY = new FileUploadRes();

    private String fileName;
    private long fileCount;
    private long uploadedCount;
}
