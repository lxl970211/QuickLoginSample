package com.energysh.quicklogin.util;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


public class GsonUtil {

    public GsonUtil() {
        // TODO Auto-generated constructor stub
    }

    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static <T> List<Map<String, T>> changeGsonToListMaps(
            String gsonString) {
        List<Map<String, T>> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        Gson gson = new Gson();
        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }
    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> parseJsonToList(String json, Type type) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(json, type);
        return list;
    }

    public static int parseJsonInt(JSONObject jsonObject, String key, int defaultValue) {
        int val = defaultValue;
        try {
            val = jsonObject.has(key) ? Integer.valueOf(jsonObject.getString(key)) : defaultValue;
        } catch (Exception e) {
                e.printStackTrace();
        }
        return val;
    }

    public static String parseJsonString(JSONObject jsonObject, String key, String defaultVal) {
        String val = defaultVal;
        try {
            val = jsonObject.optString(key);
            if ("null".equalsIgnoreCase(val)) {
                val = defaultVal;
            }
        } catch (Exception e) {

                e.printStackTrace();
        }
        return val;
    }


    public static Boolean parseJsonBoolean(JSONObject jsonObject, String key, Boolean defaultVal) {
        Boolean val = defaultVal;
        try {
            val = jsonObject.has(key) ? jsonObject.getBoolean(key) : defaultVal;
        } catch (Exception e) {

                e.printStackTrace();
        }
        return val;
    }



    /**
     * 获取消息bean 中的某一个值
     * @param params
     * @param flagName bean名称
     * @param key 要获取的值key
     * @return
     */
    public static String getMessageParamsBeanValue(String params, String flagName, String key){
        return getMessageParamsBeanValue(params, flagName, key, "");
    }

    public static String getMessageParamsBeanValue(String params, String flagName, String key, String defaultValue) {
        try {
            JSONObject object = new JSONObject(params);
            if (object != null && object.has(flagName)) {
                JSONObject uploadShareImage = object.getJSONObject(flagName);
                if (uploadShareImage != null && uploadShareImage.has(key)) {
                    return GsonUtil.parseJsonString(uploadShareImage, key, defaultValue);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
