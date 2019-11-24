package com.insuk.ecologytour.controller;

import com.insuk.ecologytour.domain.entity.Tour;
import com.insuk.ecologytour.service.TourService;
import com.insuk.ecologytour.web.request.ModifyTourDataReq;
import com.insuk.ecologytour.web.request.RegionCodeReq;
import com.insuk.ecologytour.web.request.TourDataReq;
import com.insuk.ecologytour.web.response.KeywordFrequencyRes;
import com.insuk.ecologytour.web.response.CountRegionRes;
import com.insuk.ecologytour.web.response.ProgramRes;
import com.insuk.ecologytour.web.response.RecommendationPrgRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "생태 정보 서비스 API")
@RestController
@RequestMapping("/ecologyTour/v1")
public class TourRestController {

    @Autowired
    private TourService tourService;

    @ApiOperation(value = "1. 생태 관광정보 데이터 조회")
    @GetMapping(value = "/search/v1", produces = {"application/json"})
    public List<Tour> getTours(@ModelAttribute RegionCodeReq regionCodeReq){
        return tourService.getTourByRegionCode(regionCodeReq);
    }

    @ApiOperation(value = "2. 생태 관광정보 데이터 추가")
    @PostMapping(value = "/add/v1", produces = {"application/json; charset=UTF-8"})
    public Tour addTour(@RequestBody TourDataReq tourDataReq){
        return tourService.saveTour(tourDataReq);
    }

    @ApiOperation(value = "3. 생태 관광정보 데이터 수정")
    @PutMapping(value = "/modify/v1", produces = {"application/json; charset=UTF-8"})
    public Tour modifyTour(@RequestBody ModifyTourDataReq modifyTourDataReq){
        return tourService.modifyTour(modifyTourDataReq);
    }

    @ApiOperation(value = "4. 특정 지역에서 진행되는 프로그램명과 테마 출력")
    @GetMapping(value = "/regionProgram/v1", produces = {"application/json"})
    public ProgramRes getProgramByRegionName(@RequestParam(value = "region") String regionName){
        return tourService.getProgramByRegionName(regionName);
    }

    @ApiOperation(value = "5. '프로그램 소개' 레코드에서 특정 문자열이 포함된 서비스 지역 개수 API")
    @GetMapping(value = "/countRegion/v1")
    public CountRegionRes getCountRegionByKeyword(@RequestParam String keyword){
        return tourService.getCountRegionByKeyword(keyword);
    }

    @ApiOperation(value = "6. 모든 레코드의 프로그램 상세 정보에서 입력 단어의 출현빈도수 API")
    @GetMapping(value = "/keywordFrequency/v1")
    public KeywordFrequencyRes getKeywordFrequencyInAllDetailExplain(@RequestParam String keyword){
        return tourService.getKeywordFrequencyInAllDetailExplain(keyword);
    }

    @ApiOperation(value = "7. 생태관광 프로그램 추천 API")
    @GetMapping(value = "/recommendation/program/v1")
    public RecommendationPrgRes getRecommendationPrg(@RequestParam(value = "region") String regionName,
                                                     @RequestParam String keyword){
        return tourService.getRecommendationPrg(regionName, keyword);
    }


}
