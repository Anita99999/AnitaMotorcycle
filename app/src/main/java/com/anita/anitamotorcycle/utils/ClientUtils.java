package com.anita.anitamotorcycle.utils;

import android.util.Log;

import com.anita.anitamotorcycle.beans.UserItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Anita
 * @description:请求服务器数据
 * @date : 2020/2/4 12:35
 */
public class ClientUtils {
    public static void getUserData(String baseUrl, Map<String, String> params) {
        try {
            //拼接URL
            StringBuilder sb = new StringBuilder(baseUrl);
            if (params.size() > 0) {
                sb.append("?");
                Set<Map.Entry<String, String>> entries = params.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(entry.getValue());
                }
            }
            String resultUrl = sb.toString();
            URL url = new URL(resultUrl);
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //设置请求超时时间
            urlConnection.setConnectTimeout(1000);
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "keep-alive");
            urlConnection.setRequestProperty("Accept-Language", "zh-CN,zh");

            //开始连接
            urlConnection.connect();
            //获取返回内容
            System.out.println("获取返回内容");
            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
            Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
            for (Map.Entry<String, List<String>> entry : entries) {
                System.out.println(entry.getKey() + " === " + entry.getValue());
            }
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("line---" + line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用GSON解析
     *
     * @param jsonStr
     */
    private static void parseJsonData(String jsonStr) {
        Gson gson = new Gson();
        List<UserItem> list = gson.fromJson(jsonStr,
                new TypeToken<List<UserItem>>() {
                }.getType());
        for (UserItem user : list) {
            Log.e("User", user.toString());
        }
    }
}
