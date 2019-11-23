package com.insuk.ecologytour.service;

import com.insuk.ecologytour.web.request.TourDataReq;
import com.insuk.ecologytour.web.response.FileUploadRes;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class FileUploadService {

    @Autowired
    private TourService tourService;

    public FileUploadRes upload(MultipartFile file){
        FileUploadRes fileUploadRes = FileUploadRes.EMPTY;
        if (file.isEmpty()) return fileUploadRes;

        // csv to Obj
        List<TourDataReq> tourDataList;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(file.getInputStream())))){
            CsvToBean<TourDataReq> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(TourDataReq.class)
                    .withSkipLines(1)
                    .build();
            tourDataList = csvToBean.parse();

            //  생태 관광 정보 데이터 등록
            AtomicInteger successCnt = new AtomicInteger(0);
            tourDataList.forEach(data -> {
                tourService.saveTour(data);
                successCnt.incrementAndGet();
            });

            // Response 셋팅
            fileUploadRes.setFileName(file.getOriginalFilename());
            fileUploadRes.setFileCount(tourDataList.size());
            fileUploadRes.setUploadedCount(successCnt.intValue());
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return fileUploadRes;
    }
}
