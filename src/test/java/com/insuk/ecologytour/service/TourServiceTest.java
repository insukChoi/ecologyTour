package com.insuk.ecologytour.service;

import com.insuk.ecologytour.domain.entity.Tour;
import com.insuk.ecologytour.web.request.ModifyTourDataReq;
import com.insuk.ecologytour.web.request.RegionCodeReq;
import com.insuk.ecologytour.web.request.TourDataReq;
import com.insuk.ecologytour.web.response.CountRegionRes;
import com.insuk.ecologytour.web.response.KeywordFrequencyRes;
import com.insuk.ecologytour.web.response.ProgramRes;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class TourServiceTest {

    @Autowired
    private TourService tourService;

    private TourDataReq tourDataReq;

    @Before
    public void init(){
        tourDataReq = new TourDataReq();
        tourDataReq.setProgramName("한려해상국립공원으로 떠나는 자연여행");
        tourDataReq.setTheme("자연생태체험,");
        tourDataReq.setRegions("경상남도 거제 일원");
        tourDataReq.setExplain("지심도, 조선해양문화관 등");
        tourDataReq.setDetailExplain("한려해상국립공원 거제해금강지구를 탐방하는 일정으로 동백섬으로 알려진 아름다운 섬 지심도를 비롯하여 조선해양문화관 탐방까지! 즐거운 일정으로 채워져 있습니다.");
    }

    @Test
    @Transactional
    @Order(1)
    public void 투어_등록_테스트(){
        Tour saveTourTour = tourService.saveTour(tourDataReq);
        log.info("{}", saveTourTour);
        assertThat(saveTourTour.getProgram().getProgramName(), is(tourDataReq.getProgramName()));
        assertThat(saveTourTour.getTheme(), is(tourDataReq.getTheme()));
        assertThat(saveTourTour.getRegions(), is(tourDataReq.getRegions()));
        assertThat(saveTourTour.getExplain(), is(tourDataReq.getExplain()));
        assertThat(saveTourTour.getExplain(), is(tourDataReq.getExplain()));
        assertThat(saveTourTour.getDetailExplain(), is(tourDataReq.getDetailExplain()));
    }

    @Test
    @Transactional
    @Order(2)
    public void 투어_수정_테스트(){
        Tour tour = tourService.saveTour(tourDataReq);

        ModifyTourDataReq modifyTourDataReq = new ModifyTourDataReq();
        modifyTourDataReq.setId(tour.getId());
        modifyTourDataReq.setTheme("수정합니다.");
        modifyTourDataReq.setProgramName("수정");
        modifyTourDataReq.setRegions(tour.getRegions());
        modifyTourDataReq.setExplain(tour.getExplain());
        modifyTourDataReq.setDetailExplain(tour.getDetailExplain());
        Tour modifiedTour = tourService.modifyTour(modifyTourDataReq);

        assertThat(modifiedTour.getTheme(), is("수정합니다."));
        assertThat(modifiedTour.getProgram().getProgramName(), is("수정"));
    }

    @Test
    @Transactional
    @Order(3)
    public void 투어_검색_by_지역코드(){
        Tour tour = tourService.saveTour(tourDataReq);

        RegionCodeReq regionCodeReq = new RegionCodeReq();
        regionCodeReq.setRegionCode(tour.getRegionsList().get(0).getRegionCode());

        List<Tour> tourList = tourService.getTourByRegionCode(regionCodeReq);

        assertThat(tourList.get(0).getExplain(), is(tour.getExplain()));
    }

    @Test
    @Transactional
    @Order(4)
    public void 프로그램_검색_by_지역명(){
        Tour tour = tourService.saveTour(tourDataReq);
        ProgramRes programRes = tourService.getProgramByRegionName(tour.getRegionsList().get(0).getRegionName());

        assertThat(programRes.getProgramList().size(), is(1));
        assertThat(programRes.getProgramList().get(0).getProgramName(), is(tour.getProgram().getProgramName()));
        assertThat(programRes.getProgramList().get(0).getTheme(), is(tour.getTheme()));
    }

    @Test
    @Transactional
    @Order(5)
    public void 지역_카운트_by_키워드(){
        tourService.saveTour(tourDataReq);
        String keyword = "경상남도";

        CountRegionRes countRegionRes = tourService.getCountRegionByKeyword(keyword);
        assertThat(countRegionRes.getPrograms().get(0).getRegion(), containsString(keyword));
        assertThat(countRegionRes.getPrograms().get(0).getCount(), is(1));
    }

    @Test
    @Transactional
    @Order(6)
    public void 프로그램_상세정보_키워드_빈도수_테스트(){
        tourService.saveTour(tourDataReq);
        String keyword = "일정";

        KeywordFrequencyRes keywordFrequencyRes = tourService.getKeywordFrequencyInAllDetailExplain(keyword);
        assertThat(keywordFrequencyRes.getKeyword(), is(keyword));
        assertThat(keywordFrequencyRes.getCount(), is(4L));
    }


}