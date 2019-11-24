package com.insuk.ecologytour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insuk.ecologytour.domain.entity.Program;
import com.insuk.ecologytour.domain.entity.Tour;
import com.insuk.ecologytour.service.TourService;
import com.insuk.ecologytour.web.request.ModifyTourDataReq;
import com.insuk.ecologytour.web.request.RegionCodeReq;
import com.insuk.ecologytour.web.response.CountRegionRes;
import com.insuk.ecologytour.web.response.KeywordFrequencyRes;
import com.insuk.ecologytour.web.response.ProgramRes;
import com.insuk.ecologytour.web.response.RecommendationPrgRes;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TourRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TourService tourService;

    private ObjectMapper mapper = new ObjectMapper();

    @WithMockUser
    @Test
    public void 생태_관광정보_데이터_조회_API_컨트롤러_테스트() throws Exception{
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

    @WithMockUser
    @Test
    public void 생태_관광정보_데이터_수정_API_컨트롤러_테스트() throws Exception{
        Tour tour = new Tour();
        tour.setId((long) 1);
        tour.setProgram(new Program("prg2", "2 함께갑시다~!"));
        tour.setTheme("2 나들이 체험");
        tour.setRegions("2 서울시 구로구");
        tour.setExplain("2 구로구 나들이 같이 걸어요");
        tour.setDetailExplain("2 구로구에서 우리 함께 손잡고 같이 걸어요");

        when(tourService.modifyTour(any(ModifyTourDataReq.class)))
                .thenReturn(tour);

        mvc.perform(put("/ecologyTour/v1/modify/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tour)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.program.programCode").value("prg2"))
                .andExpect(jsonPath("$.theme").value("2 나들이 체험"))
                .andExpect(jsonPath("$.regions").value("2 서울시 구로구"))
                .andExpect(jsonPath("$.explain").value("2 구로구 나들이 같이 걸어요"))
                .andExpect(jsonPath("$.detailExplain").value("2 구로구에서 우리 함께 손잡고 같이 걸어요"));
    }

    @WithMockUser
    @Test
    public void 특정_지역에서_진행되는_프로그램명과_테마_API_컨트롤러_테스트() throws Exception {
        when(tourService.getProgramByRegionName(any(String.class)))
                .thenReturn(ProgramRes.EMPTY);

        mvc.perform(get("/ecologyTour/v1/regionProgram/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("region", "anything"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @WithMockUser
    @Test
    public void 특정_문자열이_포함된_서비스_지역_개수_API_컨트롤러_테스트() throws Exception {
        when(tourService.getCountRegionByKeyword(any(String.class)))
                .thenReturn(CountRegionRes.EMPTY);

        mvc.perform(get("/ecologyTour/v1/countRegion/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "anything"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @WithMockUser
    @Test
    public void 입력_단어의_출현빈도수_API_컨트롤러_테스트() throws Exception {
        KeywordFrequencyRes keywordFrequencyRes = new KeywordFrequencyRes();
        keywordFrequencyRes.setKeyword("문화");
        keywordFrequencyRes.setCount(20L);

        when(tourService.getKeywordFrequencyInAllDetailExplain(any(String.class)))
                .thenReturn(keywordFrequencyRes);

        mvc.perform(get("/ecologyTour/v1/keywordFrequency/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "문화"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.keyword").value("문화"))
                .andExpect(jsonPath("$.count").value(20));
    }

    @WithMockUser
    @Test
    public void 프로그램_추천_API_컨트롤러_테스트() throws Exception {
        RecommendationPrgRes recommendationPrgRes = new RecommendationPrgRes();
        recommendationPrgRes.setProgram("prg111");

        when(tourService.getRecommendationPrg(any(String.class), any(String.class)))
                .thenReturn(recommendationPrgRes);

        mvc.perform(get("/ecologyTour/v1/recommendation/program/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("region", "any").param("keyword", "any"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.program").value("prg111"));
    }

}