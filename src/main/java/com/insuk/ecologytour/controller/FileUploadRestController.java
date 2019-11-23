package com.insuk.ecologytour.controller;

import com.insuk.ecologytour.web.response.FileUploadRes;
import com.insuk.ecologytour.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "CSV 파일 저장 API")
@RestController
@RequestMapping("/ecologyTour/v1")
public class FileUploadRestController {

    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation(value = "파일 Upload 데이터 저장")
    @PostMapping(value = "/fileUpload/v1")
    public FileUploadRes fileUpload(MultipartFile file){
        return fileUploadService.upload(file);
    }
}
