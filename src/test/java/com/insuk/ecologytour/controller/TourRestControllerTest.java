package com.insuk.ecologytour.controller;

import com.insuk.ecologytour.domain.entity.Program;
import com.insuk.ecologytour.domain.entity.Tour;
import com.insuk.ecologytour.service.TourService;
import com.insuk.ecologytour.web.request.RegionCodeReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TourRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TourService tourService;

    @WithMockUser
    @Test
    public void 생태_관광정보_데이터_조회_API_테스트() throws Exception{
        Tour tour = new Tour();
        tour.setId((long) 1);
        tour.setProgram(new Program("prg1", "함께갑시다~!"));
        tour.setTheme("나들이 체험");
        tour.setRegions("서울시 구로구");
        tour.setExplain("구로구 나들이 같이 걸어요");
        tour.setDetailExplain("구로구에서 우리 함께 손잡고 같이 걸어요");

        List<Tour> tourList = Collections.singletonList(tour);

        when(tourService.getTourByRegionCode(any(RegionCodeReq.class)))
                .thenReturn(tourList);

        mvc.perform(get("/ecologyTour/v1/search/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(1))
                .andExpect(jsonPath("[0].program.programCode").value("prg1"))
                .andExpect(jsonPath("[0].theme").value("나들이 체험"))
                .andExpect(jsonPath("[0].regions").value("서울시 구로구"))
                .andExpect(jsonPath("[0].explain").value("구로구 나들이 같이 걸어요"))
                .andExpect(jsonPath("[0].detailExplain").value("구로구에서 우리 함께 손잡고 같이 걸어요"));

    }



}