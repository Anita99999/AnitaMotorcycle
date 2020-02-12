package com.anita.anitamotorcycle.utils;

import com.anita.anitamotorcycle.beans.MotorBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anita
 * @description:
 * @date : 2020/2/12 13:18
 */
public class JsonUtils {

    public static ObjectMapper objectMapper;

    /**
     * 解析json数据
     *
     * @param jsonStr
     * @return
     */
    public static MotorBean motorFromJson(String jsonStr) {
        Gson gson = new Gson();
        MotorBean motor = gson.fromJson(jsonStr, MotorBean.class);
        System.out.println("json解析：motor---" + motor.toString());
        return motor;
    }

    public static MotorBean motorsFromJson(String jsonStr) {
        Gson gson = new Gson();
        MotorBean motor = gson.fromJson(jsonStr, MotorBean.class);
        System.out.println("json解析：motors---" + motor.toString());
        return motor;
    }


    public static List<MotorBean> motorListFromJson(String jsonStr) {

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MotorBean>>() {
        }.getType();
        List<MotorBean> list = gson.fromJson(jsonStr, type);
        return list;
    }


    /**
     * 解析json
     *
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T listFromJson(String content, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
