package com.zdk.blog.response;

import java.util.Map;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 13:30
 */
public class MapResponse extends ApiResponse{
    private static final long serialVersionUID = 7692793617692118128L;

    public transient Map<String, Object> mapData;

    public MapResponse(){
    }

    public MapResponse(Map<String, Object> data){
        this.mapData = data;
    }

    public void put(String key, Object value) {
        this.mapData.put(key, value);
    }

    public Map<String, Object> getMapData() {
        return this.mapData;
    }

    public void setMapData(Map<String, Object> data) {
        this.mapData = data;
    }

    public static MapResponse mapResponse(){
        return new MapResponse();
    }

    public static MapResponse mapResponse(Map<String, Object> data){
        return new MapResponse(data);
    }

}
