package com.insuk.ecologytour.service;

import com.insuk.ecologytour.common.ConstUtil;
import com.insuk.ecologytour.common.StringUtil;
import com.insuk.ecologytour.domain.entity.Region;
import com.insuk.ecologytour.repository.RegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @MockBean
    private RegionRepository regionRepository;

    private Region region;

    @Before
    public void init(){
        region = new Region();
        region.setRegionCode(ConstUtil.REGION_CODE_PREFIX + StringUtil.getUUIDNumber());
        region.setRegionName("강원도");

        Mockito.when(regionRepository.save(region))
                .thenReturn(region);

        Mockito.when(regionRepository.findByRegionName(region.getRegionName()))
                .thenReturn(java.util.Optional.ofNullable(region));
    }

    @Test
    public void 서비스지역_파싱_및_저장(){
        Region savedRegion = regionService.save(region);
        assertThat(savedRegion.getRegionName(), is(region.getRegionName()));
    }

    @Test
    public void 서비스지역_검색_by_지역명(){
        Region savedRegion = regionService.getRegionByName(region.getRegionName());
        assertThat(savedRegion.getRegionName(), is(region.getRegionName()));
    }
}