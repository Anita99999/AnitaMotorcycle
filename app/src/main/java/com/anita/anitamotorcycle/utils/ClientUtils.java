package com.anita.anitamotorcycle.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.anita.anitamotorcycle.beans.MotorItem;
import com.anita.anitamotorcycle.beans.UserItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
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
    private static ObjectMapper objectMapper;
    private static final String TAG = "ClientUtils";
    private static int sLine;
    private static MotorItem sMotorItem;
    private static boolean sResult;

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
     *
     * @param phone
     * @param password
     * @return
     */
    public static boolean validateLoginPost(Context context, String phone, String password) {
        boolean result = false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL(Constants.BASEURL + "/AMServer/userlogin");
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
                    sLine = Integer.parseInt(line);
                    if (sLine < 0) {
                        Looper.prepare();
                        Toast.makeText(context, "该手机号未注册", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    } else if (sLine == 0) {
                        Looper.prepare();
                        Toast.makeText(context, "密码输入错误", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else if (sLine > 0) {
                        result = true;
                    }
                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
            Looper.prepare();
            Toast.makeText(context, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 验证vin码
     * 验证是否存在该vin码，且该车辆未曾添加
     * （若存在，返回摩托车数据，更新ui）
     *
     * @param vin
     * @return
     */
    public static MotorItem validateVIN(final String vin, final Context context) {
        sMotorItem = null;
//        sResult = false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL(Constants.BASEURL + "/AMServer/vinvalidate");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

            MotorItem motorItem = new MotorItem(vin);
            Log.d(TAG, "post数据---" + motorItem.toString());
            Gson gson = new Gson();
            String jsonStr = gson.toJson(motorItem);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            Log.d(TAG, "生成json--- " + jsonStr);
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
                Log.d(TAG, "服务器响应数据 --- " + line);
                if (line != null) {
                    sMotorItem = fromJson(line);

                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
            Looper.prepare();
            Toast.makeText(context, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "validateVIN:Exception-- " + e.getMessage());
        }

        return sMotorItem;
    }

    /**
     * 连接数据库
     * 1. 传回当前用户phone、摩托车基本信息、车牌号、创建更新时间(motoritem)
     * 2. 验证车牌号是否当前数据库
     * （若在，返回true;若不在，返回false）
     *
     * @param motorItem
     * @return
     */
    public static boolean validateNumbers(Context context, MotorItem motorItem) {
        sResult = false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL(Constants.BASEURL + "/AMServer/pnumsvalidate");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

            Log.d(TAG, "post数据---" + motorItem.toString());
            Gson gson = new Gson();
            String jsonStr = gson.toJson(motorItem);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            Log.d(TAG, "生成json--- " + jsonStr);
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
                Log.d(TAG, "服务器响应数据 --- " + line);
                if (line != null) {
                    sResult = Boolean.parseBoolean(line);

                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
            Looper.prepare();
            Toast.makeText(context, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "validateVIN:Exception-- " + e.getMessage());
        }
        return sResult;
    }

    /**
     * 连接数据库
     * 1. 传回当前用户phone、摩托车基本信息、车牌号、创建更新时间(motoritem)
     * 2. update数据库摩托车信息
     * （更新成功返回true，失败false）
     *
     * @param motorItem
     * @return
     */
    public static boolean addMotor(Context context, MotorItem motorItem) {
        sResult = false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL(Constants.BASEURL + "/AMServer/updatemotor");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

            Log.d(TAG, "post数据---" + motorItem.toString());
            Gson gson = new Gson();
            String jsonStr = gson.toJson(motorItem);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            Log.d(TAG, "生成json--- " + jsonStr);
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
                Log.d(TAG, "服务器响应数据 --- " + line);
                if (line != null) {
                    sResult = Boolean.parseBoolean(line);

                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
            Looper.prepare();
            Toast.makeText(context, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "validateVIN:Exception-- " + e.getMessage());
        }
        return sResult;
    }


    /**
     * 解析json数据
     *
     * @param jsonStr
     * @return
     */
    private static MotorItem fromJson(String jsonStr) {
        Gson gson = new Gson();
        MotorItem motor = gson.fromJson(jsonStr, MotorItem.class);
        System.out.println("json解析：motor---" + motor.toString());
        return motor;
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
