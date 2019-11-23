package com.insuk.ecologytour.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendWeightPropertiesTest {
    @Autowired
    private RecommendWeightProperties recommendWeightProperties;

    @Test
    public void 프로그램추천_가중치_확인(){
        assertThat(recommendWeightProperties.getTheme(), is(10));
        assertThat(recommendWeightProperties.getExplain(), is(8));
        assertThat(recommendWeightProperties.getDetailExplain(), is(5));
    }

}