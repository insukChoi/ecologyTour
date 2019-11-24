package com.insuk.ecologytour.repository;

import com.insuk.ecologytour.common.ConstUtil;
import com.insuk.ecologytour.common.StringUtil;
import com.insuk.ecologytour.domain.entity.Program;
import com.insuk.ecologytour.domain.entity.Tour;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Tour tour = new Tour();

    @Before
    public void init(){
        Program program = Program.builder()
                .programCode(ConstUtil.PROGRAM_CODE_PREFIX + StringUtil.getUUIDNumber())
                .programName("자연과 문화를 함께 즐기는 설악산 기행").build();
        tour.setProgram(program);
        tour.setTheme("문화생태체험,자연생태체험");
        tour.setRegions("강원도 속초");
        tour.setExplain("설악산 탐방안내소,신흥사,권금성,비룡폭포");
        tour.setDetailExplain("설악산은 왜 설악산이고, 신흥사는 왜 신흥사일까요? 설악산에 대해 정확히 알고, 배우고, 느낄 수 있는 단일형 생태관광입니다.");

        testEntityManager.persist(tour);
    }

    @Test
    public void 투어검색_by_프로그램소개(){
        Optional<List<Tour>> tourList = tourRepository.findByExplainContaining("탐방안내소");
        assertThat(tourList.isPresent(), is(true));
    }

    @Test
    public void 투어검색_by_프로그램상세소개(){
        Optional<List<Tour>> tourList = tourRepository.findByDetailExplainContaining("설악산은 왜 설악산이고");
        assertThat(tourList.isPresent(), is(true));
    }

    @Test
    public void 투어검색_by_서비스지역(){
        Optional<List<Tour>> tourList = tourRepository.findByRegionsContaining("속초");
        assertThat(tourList.isPresent(), is(true));
    }
}