package org.aimes.redis.util;

import lombok.Data;

public enum RedisDB {
    PROFIT_REPORT(1);
    private static int value;

    RedisDB(int value){
        value = value;
    }
    public static int getValue(){
        return value;
    }
}
