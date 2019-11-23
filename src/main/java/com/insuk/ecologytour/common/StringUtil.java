package com.insuk.ecologytour.common;

import java.math.BigInteger;
import java.util.UUID;

public class StringUtil {
    /**
     * UUID 를 랜덤으로 가져온다 (only number)
     * @return String
     */
    public static String getUUIDNumber(){
        return String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
    }
}
