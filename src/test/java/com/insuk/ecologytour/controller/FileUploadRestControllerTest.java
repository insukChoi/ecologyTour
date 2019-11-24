package com.insuk.ecologytour.controller;

import com.insuk.ecologytour.service.FileUploadService;
import com.insuk.ecologytour.web.response.FileUploadRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileUploadService fileUploadService;

    @Test
    @WithMockUser
    public void 파일_업로드_테스트() throws Exception {
        MockMultipartFile file = new MockMultipartFile("sample2.csv", new FileInputStream(new File("download/sample2.csv")));
        FileUploadRes fileUploadRes = new FileUploadRes();
        when(fileUploadService.upload(any(MultipartFile.class)))
                .thenReturn(fileUploadRes);

        mvc.perform(MockMvcRequestBuilders.multipart("/ecologyTour/v1/fileUpload/v1")
                .file(file))
                .andExpect(status().isOk());
    }

}