package com.insuk.ecologytour.service;

import com.insuk.ecologytour.common.ConstUtil;
import com.insuk.ecologytour.common.StringUtil;
import com.insuk.ecologytour.domain.entity.Region;
import com.insuk.ecologytour.web.request.ModifyTourDataReq;
import com.insuk.ecologytour.web.request.RegionCodeReq;
import com.insuk.ecologytour.web.request.TourDataReq;
import com.insuk.ecologytour.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RegionService {

    private final Pattern regionRegex = Pattern.compile("(^[가-힣]{2,}[,]*)$");

    @Autowired
    private RegionRepository regionRepository;

    public Region save(Region region){
        final Optional<Region> savedRegion = regionRepository.findByRegionName(region.getRegionName());
        return savedRegion.orElseGet(() -> regionRepository.save(region));
    }

    public List<Region> saveByUpload(TourDataReq tourData){
        List<Region> regionList = this.regionParser(tourData.getRegions());
        return regionList.stream().map(this::save).collect(Collectors.toList());
    }

    public Region getRegion(RegionCodeReq regionCodeReq){
        final Optional<Region> savedRegion = regionRepository.findByRegionCode(regionCodeReq.getRegionCode());
        return savedRegion.orElse(Region.EMPTY);
    }

    public List<Region> modifyRegion(ModifyTourDataReq modifyTourData) {
        List<Region> regionList = this.regionParser(modifyTourData.getRegions());
        return regionList.stream().map(this::save).collect(Collectors.toList());
    }

    public Region getRegionByName(String regionName) {
        final Optional<Region> savedRegion = regionRepository.findByRegionName(regionName);
        return savedRegion.orElse(Region.EMPTY);
    }

    /**
     * Region (서비스 지역) String 을 스페이스로 구분하여 파싱
     * @param region
     * @return List<Region>
     */
    private List<Region> regionParser(String region){
        List<Region> regionList = new ArrayList<>();
        String[] arr = region.split(" ");
        Matcher matcher;
        for (String s : arr){
            matcher = regionRegex.matcher(s);
            if (matcher.find()){
                s = s.replaceAll(",", "");
                Region r = new Region();
                r.setRegionCode(ConstUtil.REGION_CODE_PREFIX + StringUtil.getUUIDNumber());
                r.setRegionName(s);
                regionList.add(r);
            }
        }
        return regionList;
    }

}
