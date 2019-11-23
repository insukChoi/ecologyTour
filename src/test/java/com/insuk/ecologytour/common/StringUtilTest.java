package com.insuk.ecologytour.common;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class StringUtilTest {

    @Test
    public void UUID는_오직_숫자_테스트(){
        final String onlyNumberRegex = "\\d+";
        String uuid = StringUtil.getUUIDNumber();
        assertThat(uuid.matches(onlyNumberRegex), is(true));
    }

    @Test
    public void UUID는_40글자_테스트(){
        String uuid = StringUtil.getUUIDNumber();
        assertThat(uuid.length(), is(40));
    }

}