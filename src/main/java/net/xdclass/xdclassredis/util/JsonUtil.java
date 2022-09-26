package net.xdclass.xdclassredis.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 把对象转字符串
     * @param data
     * @return
     */
    public static String objectToJson(Object data){

        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把字符串转对象
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String jsonData,Class<T> beanType){
        try {
            return MAPPER.readValue(jsonData,beanType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
