package com.insuk.ecologytour.service;

import com.insuk.ecologytour.config.RecommendWeightProperties;
import com.insuk.ecologytour.domain.entity.Program;
import com.insuk.ecologytour.domain.entity.Region;
import com.insuk.ecologytour.domain.entity.Tour;
import com.insuk.ecologytour.domain.exception.NotFoundTourIdException;
import com.insuk.ecologytour.web.dto.CountRegionDto;
import com.insuk.ecologytour.web.dto.RecommendationPrgDto;
import com.insuk.ecologytour.web.request.ModifyTourDataReq;
import com.insuk.ecologytour.web.request.RegionCodeReq;
import com.insuk.ecologytour.web.request.TourDataReq;
import com.insuk.ecologytour.repository.TourRepository;
import com.insuk.ecologytour.web.dto.ProgramDto;
import com.insuk.ecologytour.web.response.CountRegionRes;
import com.insuk.ecologytour.web.response.KeywordFrequencyRes;
import com.insuk.ecologytour.web.response.ProgramRes;
import com.insuk.ecologytour.web.response.RecommendationPrgRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class TourService {

    @Autowired
    private ProgramService programService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private RecommendWeightProperties recommendWeightProperties;

    @Transactional
    public Tour saveTour(TourDataReq tourData){
        // Program 등록
        Program programPersist = programService.saveByUpload(tourData);

        // Region 등록
        List<Region> regionPersist = regionService.saveByUpload(tourData);

        // Tour 등록
        Tour tour = new Tour();
        tour.setProgram(programPersist);
        tour.setTheme(tourData.getTheme());
        tour.setRegions(tourData.getRegions());
        tour.setExplain(tourData.getExplain());
        tour.setDetailExplain(tourData.getDetailExplain());

        // Tour - Region 관계 설정
        regionPersist.forEach(region -> region.addTour(tour));

        return tourRepository.save(tour);
    }

    public List<Tour> getTourByRegionCode(RegionCodeReq regionCodeReq){
        return regionService.getRegion(regionCodeReq).getTourList();
    }

    public Tour modifyTour(ModifyTourDataReq modifyTourData){
        // Program 수정
        Program programPersist = programService.modifyProgram(modifyTourData);

        // Region 수정
        List<Region> regionPersist = regionService.modifyRegion(modifyTourData);

        // Tour 수정
        Optional<Tour> savedTour = tourRepository.findById(modifyTourData.getId());
        savedTour.orElseThrow(() -> new NotFoundTourIdException("Not Found Tour ID : "+modifyTourData.getId()));

        Tour tour = savedTour.get();
        tour.setProgram(programPersist);
        tour.setTheme(modifyTourData.getTheme());
        tour.setRegions(modifyTourData.getRegions());
        tour.setExplain(modifyTourData.getExplain());
        tour.setDetailExplain(modifyTourData.getDetailExplain());

        // Tour - Region 관계 수정
        tour.getRegionsList().clear();
        regionPersist.forEach(region -> region.addTour(tour));

        return tourRepository.save(tour);
    }

    /**
     * 특정 지역에서 진행되는 프로그램명과 테마 출력
     * @param regionName
     * @return ProgramRes
     */
    public ProgramRes getProgramByRegionName(String regionName) {
        Region savedRegion = regionService.getRegionByName(regionName);
        if (savedRegion == Region.EMPTY) return ProgramRes.EMPTY;

        List<Tour> tourList = savedRegion.getTourList();
        ProgramRes programRes = new ProgramRes();
        programRes.setRegionCode(savedRegion.getRegionCode());
        programRes.setProgramList(tourList.stream().map(
                tour -> new ProgramDto(
                        tour.getProgram().getProgramName(),
                        tour.getTheme())
                ).collect(toList())
        );

        return programRes;
    }

    /**
     * '프로그램 소개' 레코드에서 특정 문자열이 포함된 서비스 지역 개수
     * @param keyword
     * @return CountRegionRes
     */
    public CountRegionRes getCountRegionByKeyword(String keyword) {
        Optional<List<Tour>> tourList = tourRepository.findByExplainContaining(keyword);
        if (!tourList.isPresent()) return CountRegionRes.EMPTY;

        // key : Regions(서비스 지역), value : count(서비스 지역 카운트)
        Map<String, Long> regionsMap = tourList.get().stream()
                .map(Tour::getRegions)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        CountRegionRes countRegionRes = new CountRegionRes();
        countRegionRes.setKeyword(keyword);

        List<CountRegionDto> countRegionDtoList = new ArrayList<>();
        regionsMap.forEach((k, v) -> countRegionDtoList.add(new CountRegionDto(k , v)));
        countRegionRes.setPrograms(countRegionDtoList);

        return countRegionRes;
    }


    /**
     * 모든 레코드의 프로그램 상세 정보에서 입력 단어의 출현빈도수
     * @param keyword
     * @return KeywordFrequencyRes
     */
    public KeywordFrequencyRes getKeywordFrequencyInAllDetailExplain(String keyword) {
        Optional<List<Tour>> tourList = tourRepository.findByDetailExplainContaining(keyword);
        if (!tourList.isPresent()) return KeywordFrequencyRes.EMPTY;

        KeywordFrequencyRes keywordFrequencyRes = new KeywordFrequencyRes();
        keywordFrequencyRes.setKeyword(keyword);
        keywordFrequencyRes.setCount(
                tourList.get().stream().map(
                        tour -> StringUtils.countOccurrencesOf(tour.getDetailExplain(), keyword)
                ).reduce(0, Integer::sum).longValue()
        );

        return keywordFrequencyRes;
    }

    /**
     * 생태관광 프로그램 추천 by 지역명, 키워드
     * @param regionName
     * @param keyword
     * @return RecommendationPrgRes
     */
    public RecommendationPrgRes getRecommendationPrg(String regionName, String keyword) {
        Optional<List<Tour>> tourList = tourRepository.findByRegionsContaining(regionName);
        if (!tourList.isPresent()) return RecommendationPrgRes.EMPTY;

        List<RecommendationPrgDto> prgDtoList = tourList.get().stream()
            .map(tour -> {
                RecommendationPrgDto prgDto = new RecommendationPrgDto(
                        tour.getProgram().getProgramCode(),
                        tour.getProgram().getProgramName(),
                        RecommendationPrgDto.WEIGHT_ZERO
                );
                prgDto.plusWeight(new int[]{
                        tour.getTheme().contains(keyword) ? recommendWeightProperties.getTheme() : RecommendationPrgDto.WEIGHT_ZERO,
                        tour.getExplain().contains(keyword) ? recommendWeightProperties.getExplain() : RecommendationPrgDto.WEIGHT_ZERO,
                        tour.getDetailExplain().contains(keyword) ? recommendWeightProperties.getDetailExplain() : RecommendationPrgDto.WEIGHT_ZERO
                });
                return prgDto;
            }).sorted(Comparator.reverseOrder()).limit(1).collect(Collectors.toList());

       return prgDtoList.size() == 0 ?
               RecommendationPrgRes.EMPTY : new RecommendationPrgRes(prgDtoList.get(0).getProgramCode());
    }
}
