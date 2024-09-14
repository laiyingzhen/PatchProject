package org.aimes.redis.report;

import net.minidev.json.JSONObject;
import org.aimes.redis.util.JedisUtil;
import org.aimes.redis.util.JsonUtil;
import org.aimes.redis.util.RedisDB;

import java.util.Map;

public class RedisReportCacheExample {
    private static final int redisExpireTime = 8; // 8秒鐘

    public static JSONObject getTeamData(String userKey) {
        JSONObject json = null;
        Object obj = JedisUtil.getRedisObject(RedisDB.PROFIT_REPORT.getValue(), genTeamKey(userKey));
        if(obj != null){
            json = JsonUtil.convertToJsonObj(obj.toString());
        }
        return json;
    }

    public static boolean setTeamData(String userKey, JSONObject jsonData) {
        return JedisUtil.putRedisCache(RedisDB.PROFIT_REPORT.getValue(), genTeamKey(userKey), jsonData.toString(), redisExpireTime);
    }

    private static String genTeamKey(String key) {
        return "team_" + key;
    }

    public static Map<String, JSONObject> getRealtimeTeamData(String key) {
        Map<String, JSONObject> json = null;
        Object obj = JedisUtil.getRedisObject(RedisDB.PROFIT_REPORT.getValue(), genRealtimeTeamKey(key));
        if(obj != null){
            json = (Map<String, JSONObject>) obj;
        }
        return json;
    }

    public static boolean setRealtimeTeamData(String key, Map<String, JSONObject> data) {
        return JedisUtil.putRedisCache(RedisDB.PROFIT_REPORT.getValue(), genRealtimeTeamKey(key), data, redisExpireTime);
    }

    private static String genRealtimeTeamKey(String key) {
        return "realtime_team_" + key;
    }

    public static JSONObject getTeamMemberData(String userKey) {
        JSONObject json = null;
        Object obj = JedisUtil.getRedisObject(RedisDB.PROFIT_REPORT.getValue(), genTeamMemberKey(userKey));
        if(obj != null){
            json = JsonUtil.convertToJsonObj(obj.toString());
        }
        return json;
    }

    public static boolean setTeamMemberData(String userKey, JSONObject jsonData) {
        return JedisUtil.putRedisCache(RedisDB.PROFIT_REPORT.getValue(), genTeamMemberKey(userKey), jsonData.toString(), redisExpireTime);
    }

    private static String genTeamMemberKey(String key) {
        return "team_member_" + key;
    }

    public static Map<String, JSONObject> getPersonalData(String key) {
        Map<String, JSONObject> json = null;
        Object obj = JedisUtil.getRedisObject(RedisDB.PROFIT_REPORT.getValue(), genPersonalKey(key));
        if(obj != null){
            json = (Map<String, JSONObject>) obj;
        }
        return json;
    }

    public static boolean setPersonalData(String key, Map<String, JSONObject> data) {
        return JedisUtil.putRedisCache(RedisDB.PROFIT_REPORT.getValue(), genPersonalKey(key), data, redisExpireTime);
    }

    private static String genPersonalKey(String key) {
        return "person_" + key;
    }

    public static Map<String, Object> getPtTeamProfitData(String key) {
        Map<String, Object> json = null;
        Object obj = JedisUtil.getRedisObject(RedisDB.PROFIT_REPORT.getValue(), genPtTeamProfitKey(key));
        if(obj != null){
            json = (Map<String, Object>) obj;
        }
        return json;
    }

    public static boolean setPtTeamProfitData(String key, Map<String, Object> data) {
        return JedisUtil.putRedisCache(RedisDB.PROFIT_REPORT.getValue(), genPtTeamProfitKey(key), data, redisExpireTime);
    }

    private static String genPtTeamProfitKey(String key) {
        return "pt_team_" + key;
    }
}
