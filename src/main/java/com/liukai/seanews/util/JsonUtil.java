package com.liukai.seanews.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonUtil {

    /**
     * 将Map转为Json字符串
     * @param map
     * @return
     */
    public static String mapToJsonStr(Map<String, String> map){
        Set<String> keys = map.keySet();
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for(Iterator<String> it = keys.iterator(); it.hasNext();){
            key =  (String)it.next();
            value = map.get(key);
            jsonBuffer.append("\"" + key + "\"" +":" + "\"" + value + "\"");
            if(it.hasNext()){
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }
}
