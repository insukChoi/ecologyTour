package com.insuk.ecologytour.repository;

import com.insuk.ecologytour.common.ConstUtil;
import com.insuk.ecologytour.common.StringUtil;
import com.insuk.ecologytour.domain.entity.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RegionRepositoryTest {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private String regionCode;
    private String regionName;

    @Before
    public void init(){
        regionCode = ConstUtil.REGION_CODE_PREFIX + StringUtil.getUUIDNumber();
        regionName = "구로구";

        // Region 등록
        Region region = new Region();
        region.setRegionCode(regionCode);
        region.setRegionName(regionName);

        testEntityManager.persist(region);
    }

    @Test
    public void 서비스_지역_검색_by_지역코드(){
        Optional<Region> savedRegion = regionRepository.findByRegionCode(regionCode);
        Region region = savedRegion.orElse(Region.EMPTY);

        assertThat(region.getRegionCode(), is(regionCode));
        assertThat(region.getRegionName(), is(regionName));
    }

    @Test
    public void 서비스_지역_검색_by_지역명(){
        Optional<Region> savedRegion = regionRepository.findByRegionName(regionName);
        Region region = savedRegion.orElse(Region.EMPTY);

        assertThat(region.getRegionCode(), is(regionCode));
        assertThat(region.getRegionName(), is(regionName));
    }

}