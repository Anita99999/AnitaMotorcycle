package com.anita.anitamotorcycle.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.anita.anitamotorcycle.activities.LoginActivity;
import com.anita.anitamotorcycle.activities.MainActivity;
import com.anita.anitamotorcycle.beans.UserItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @author Anita
 * @description:请求服务器数据
 * @date : 2020/2/4 12:35
 */
public class ClientUtils {
    private static final String TAG = "ClientUtils";
    private static int sResult;

    //                String url = "http://192.168.0.107:8080/AMServer/userlogin";
//                Map<String, String> params = new HashMap<>();
//                ClientUtils.getUserData(url, params);
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
     * 登录验证
     * 1. 连接服务端数据库，传递数据
     * 2. 验证用户手机号已注册
     * 3. 验证密码正确
     * @param phone
     * @param password
     * @return
     */
    public static boolean validateLoginPost(Context context, String phone, String password){
        boolean result=false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL("http://192.168.0.107:8080/AMServer/userlogin");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

            UserItem userItem = new UserItem(phone, password);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(userItem);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            Log.d(TAG, "jsonStr --- " + jsonStr);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            //连接
            httpURLConnection.connect();
            //把数据给到服务
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            //拿结果
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String line = in.readLine();
                Log.d(TAG, "server response data --- " + line);
                if (line != null) {
                    sResult = Integer.parseInt(line);
                    if (sResult < 0) {
                        Looper.prepare();
                        Toast.makeText(context, "当前手机号未注册", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    } else if (sResult == 0) {
                        Looper.prepare();
                        Toast.makeText(context, "密码输入错误", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else if (sResult > 0) {
                        result =true;
                    }
                    Log.d(TAG, "sResult -- > " + sResult);
                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
