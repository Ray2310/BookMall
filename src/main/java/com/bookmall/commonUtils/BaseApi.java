package com.bookmall.commonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装baseApi， 将前端需要的map类型的响应信息回现
 * 定义map属性， 然后赋值 3个key, value 默认为null
 */
public class BaseApi {
    private static Map<String, Object> map(String code, Object data, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("data", data);
        map.put("msg", msg);
        return map;
    }

    public static Map<String, Object> success() {
        return map("200", null, null);
    }

    public static Map<String, Object> success(Object data) {
        return map("200", data, null);
    }

    public static Map<String, Object> error(String msg) {
        return map("400", null, msg);
    }

}
