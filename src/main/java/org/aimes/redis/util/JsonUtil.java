package org.aimes.redis.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.minidev.json.JSONObject;
import org.aimes.redis.dto.UserDataDTO;


public class JsonUtil {
    public static JSONObject convertToJsonObj(String jsonStr) {
        JSONObject result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.readValue(jsonStr, JSONObject.class);
        }catch(Exception ex){
            System.out.println("ex = " + ex);
        }
//        String result = "";
//        ObjectMapper objectMapper = new ObjectMapper();
//        UserDataDTO dto = new UserDataDTO();
//        dto.setId(135);
//        dto.setName("Mike");
//        dto.setBalance(100d);
//        try {
//            result = objectMapper.writeValueAsString(obj);
//        }catch(Exception ex){
//            System.out.println("ex = " + ex);
//        }
        return result;
    }
}
